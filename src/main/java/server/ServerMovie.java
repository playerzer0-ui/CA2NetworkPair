package server;

import business.Film;
import business.User;
import service.FilmManager;
import service.TCProtocol;
import service.UserManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ServerMovie {
    private static boolean serverOnline = true;

    public static void main(String[] args) {
        FilmManager filmManager = new FilmManager();
        filmManager.setData();
        UserManager userManager = new UserManager();

        System.out.println("movie server online...");
        try(ServerSocket serverSocket = new ServerSocket(TCProtocol.PORT)){

            while(serverOnline){
                Socket socket = serverSocket.accept();
                boolean validSession = true;
                User user = null;

                try(Scanner input = new Scanner(socket.getInputStream()); PrintWriter output = new PrintWriter(socket.getOutputStream())){

                    while(validSession){
                        String request = input.nextLine();
                        System.out.println(request);
                        String[] components = request.split(TCProtocol.DELIMITER);
                        String response;

                        switch (components[0]){

                            case TCProtocol.REGISTER:
                                if (components.length == 3) {
                                    boolean added = userManager.addUser(new User(components[1], components[2], false));
                                    response = added ? TCProtocol.ADDED : TCProtocol.REJECTED;
                                } else {
                                    response = TCProtocol.INVALID;
                                }
                                break;

                            case TCProtocol.LOGIN:
                                if (components.length == 3) {
                                     User u = userManager.searchByUsername(components[1]);
                                    if (u != null && u.getPassword().equals(components[2])) {
                                        user = u;
                                        response = user.isAdmin() ? TCProtocol.ADMIN : TCProtocol.USER;
                                    } else {
                                        response = TCProtocol.FAILED;
                                    }
                                } else {
                                    response = TCProtocol.INVALID;
                                }
                                break;

                            case TCProtocol.LOGOUT:
                                user = null;
                                response = TCProtocol.LOGGED_OUT;
                                break;


                            case TCProtocol.RATE:
                                if(components.length == 3){
                                    if(user != null){
                                        if(isNumber(components[2])){
                                            Film film = filmManager.searchByTitle(components[1]);
                                            if(film != null){
                                                boolean succeed = filmManager.rateFilm(film.getTitle(), Integer.parseInt(components[2]));
                                                if(succeed){
                                                    response = TCProtocol.SUCCESS;
                                                }
                                                else{
                                                    response = TCProtocol.INVALID_RATING_SUPPLIED;
                                                }
                                            }
                                            else{
                                                response = TCProtocol.NO_MATCH_FOUND;
                                            }
                                        }
                                        else{
                                            response = TCProtocol.INVALID_RATING_SUPPLIED;
                                        }
                                    }
                                    else{
                                        response = TCProtocol.NOT_LOGGED_IN;
                                    }
                                }
                                else{
                                    response = TCProtocol.INVALID;
                                }
                                break;

                            case TCProtocol.SEARCH_NAME:
                                if(components.length == 2){
                                    Film film = filmManager.searchByTitle(components[1]);
                                    if(film != null){
                                        response = film.toString();
                                    }
                                    else{
                                        response = TCProtocol.NO_MATCH_FOUND;
                                    }
                                }
                                else{
                                    response = TCProtocol.INVALID;
                                }
                                break;

                            case TCProtocol.SEARCH_GENRE:
                                if(components.length == 2){
                                    List<Film> films = filmManager.searchByGenre(components[1]);
                                    if(!films.isEmpty()){
                                        StringBuilder result = new StringBuilder();

                                        for (Film film : films) {
                                            result.append(film.toString()).append(TCProtocol.KWARG);
                                        }

                                        // Remove the last "~~" separator if it's present
                                        if (!result.isEmpty()) {
                                            result.setLength(result.length() - 2);
                                        }

                                        response = String.valueOf(result);
                                    }
                                    else{
                                        response = TCProtocol.NO_MATCH_FOUND;
                                    }
                                }
                                else{
                                    response = TCProtocol.INVALID;
                                }
                                break;

                            case TCProtocol.ADD:
                                if(components.length == 3){
                                    if(user != null){
                                        if(user.isAdmin()){
                                            boolean succeed = filmManager.addFilm(new Film(components[1], components[2], 0, 0));
                                            if(succeed){
                                                response = TCProtocol.ADDED;
                                            }
                                            else{
                                                response = TCProtocol.EXISTS;
                                            }
                                        }
                                        else{
                                            response = TCProtocol.INSUFFICIENT;
                                        }
                                    }
                                    else{
                                        response = TCProtocol.INSUFFICIENT;
                                    }
                                }
                                else{
                                    response = TCProtocol.INVALID;
                                }
                                break;

                            case TCProtocol.REMOVE:
                                if(components.length == 2){
                                    if(user != null){
                                        if(user.isAdmin()){
                                            boolean succeed = filmManager.removeFilm(components[1]);
                                            if(succeed){
                                                response = TCProtocol.REMOVE;
                                            }
                                            else{
                                                response = TCProtocol.NOT_FOUND;
                                            }
                                        }
                                        else{
                                            response = TCProtocol.INSUFFICIENT;
                                        }
                                    }
                                    else{
                                        response = TCProtocol.INSUFFICIENT;
                                    }
                                }
                                else{
                                    response = TCProtocol.INVALID;
                                }
                                break;

                            case TCProtocol.EXIT:
                                validSession = false;
                                response = TCProtocol.GOODBYE;
                                break;

                            case TCProtocol.SHUTDOWN:
                                if(user != null){
                                    if(user.isAdmin()){
                                        serverOnline = false;
                                        validSession = false;
                                        response = TCProtocol.SHUTTING_DOWN;
                                    }
                                    else{
                                        response = TCProtocol.INSUFFICIENT;
                                    }
                                }
                                else{
                                    response = TCProtocol.INSUFFICIENT;
                                }
                                break;

                            default:
                                response = TCProtocol.INVALID;
                                break;
                        }

                        output.println(response);
                        output.flush();
                    }
                }
            }
        }
        catch (BindException e) {
            System.out.println("BindException occurred when attempting to bind to port " + TCProtocol.PORT);
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException occurred on server socket");
            System.out.println(e.getMessage());
        }
    }

    public static boolean isNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            // If the string is not a valid number, throw NumberFormatException
            return false;
        }
    }

}
