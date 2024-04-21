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

public class ServerThreadHandler implements Runnable{
    private boolean serverOnline;
    private User user;
    private boolean validSession;
    private final FilmManager filmManager;
    private final UserManager userManager;

    public ServerThreadHandler(){
        serverOnline = true;
        user = null;
        validSession = true;
        filmManager = new FilmManager();
        filmManager.setData();
        userManager = new UserManager();
    }
    @Override
    public void run() {
        try(ServerSocket serverSocket = new ServerSocket(TCProtocol.PORT)){

            while(serverOnline){
                Socket socket = serverSocket.accept();
                validSession = true;
                user = null;

                try(Scanner input = new Scanner(socket.getInputStream()); PrintWriter output = new PrintWriter(socket.getOutputStream())){

                    while(validSession){
                        String request = input.nextLine();
                        System.out.println(request);
                        String[] components = request.split(TCProtocol.DELIMITER);
                        String response;

                        switch (components[0]){

                            case TCProtocol.REGISTER:
                                response = registerCommand(components, userManager);
                                break;

                            case TCProtocol.LOGIN:
                                response = loginCommand(components, userManager);
                                break;

                            case TCProtocol.LOGOUT:
                                user = null;
                                response = TCProtocol.LOGGED_OUT;
                                break;


                            case TCProtocol.RATE:
                                response = rateCommand(components, filmManager);
                                break;

                            case TCProtocol.SEARCH_NAME:
                                response = searchFilmByName(components, filmManager);
                                break;

                            case TCProtocol.SEARCH_GENRE:
                                response = searchFilmByGenre(components, filmManager);
                                break;

                            case TCProtocol.ADD:
                                response = addFilm(components, filmManager);
                                break;

                            case TCProtocol.REMOVE:
                                response = removeFilm(components, filmManager);
                                break;

                            case TCProtocol.EXIT:
                                validSession = false;
                                response = TCProtocol.GOODBYE;
                                break;

                            case TCProtocol.SHUTDOWN:
                                response = shutDownServer();
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

    public boolean isNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            // If the string is not a valid number, throw NumberFormatException
            return false;
        }
    }

    public String registerCommand(String[] components, UserManager userManager){
        if (components.length == 3) {
            boolean added = userManager.addUser(new User(components[1], components[2], false));
            return added ? TCProtocol.ADDED : TCProtocol.REJECTED;
        } else {
            return TCProtocol.INVALID;
        }
    }

    public String loginCommand(String[] components, UserManager userManager){
        if (components.length == 3) {
            User u = userManager.searchByUsername(components[1]);
            if (u != null && u.getPassword().equals(components[2])) {
                user = u;
                return user.isAdmin() ? TCProtocol.ADMIN : TCProtocol.USER;
            } else {
                return TCProtocol.FAILED;
            }
        } else {
            return TCProtocol.INVALID;
        }
    }

    public String rateCommand(String[] components, FilmManager filmManager){
        if(components.length == 3){
            if(user != null){
                if(isNumber(components[2])){
                    Film film = filmManager.searchByTitle(components[1]);
                    if(film != null){
                        boolean succeed = filmManager.rateFilm(film.getTitle(), Integer.parseInt(components[2]));
                        if(succeed){
                            return TCProtocol.SUCCESS;
                        }
                        else{
                            return TCProtocol.INVALID_RATING_SUPPLIED;
                        }
                    }
                    else{
                        return TCProtocol.NO_MATCH_FOUND;
                    }
                }
                else{
                    return TCProtocol.INVALID_RATING_SUPPLIED;
                }
            }
            else{
                return TCProtocol.NOT_LOGGED_IN;
            }
        }
        else{
            return TCProtocol.INVALID;
        }
    }

    public String searchFilmByName(String[] components, FilmManager filmManager){
        if(components.length == 2){
            Film film = filmManager.searchByTitle(components[1]);
            if(film != null){
                return film.toString();
            }
            else{
                return TCProtocol.NO_MATCH_FOUND;
            }
        }
        else{
            return TCProtocol.INVALID;
        }
    }

    public String searchFilmByGenre(String[] components, FilmManager filmManager){
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

                return String.valueOf(result);
            }
            else{
                return TCProtocol.NO_MATCH_FOUND;
            }
        }
        else{
            return TCProtocol.INVALID;
        }
    }

    public String addFilm(String[] components, FilmManager filmManager){
        if(components.length == 3){
            if(user != null){
                if(user.isAdmin()){
                    boolean succeed = filmManager.addFilm(new Film(components[1], components[2], 0, 0));
                    if(succeed){
                        return TCProtocol.ADDED;
                    }
                    else{
                        return TCProtocol.EXISTS;
                    }
                }
                else{
                    return TCProtocol.INSUFFICIENT;
                }
            }
            else{
                return TCProtocol.INSUFFICIENT;
            }
        }
        else{
            return TCProtocol.INVALID;
        }
    }

    public String removeFilm(String[] components, FilmManager filmManager){
        if(components.length == 2){
            if(user != null){
                if(user.isAdmin()){
                    boolean succeed = filmManager.removeFilm(components[1]);
                    if(succeed){
                        return TCProtocol.REMOVED;
                    }
                    else{
                        return TCProtocol.NOT_FOUND;
                    }
                }
                else{
                    return TCProtocol.INSUFFICIENT;
                }
            }
            else{
                return TCProtocol.INSUFFICIENT;
            }
        }
        else{
            return TCProtocol.INVALID;
        }
    }

    public String shutDownServer() {
        if (user != null) {
            if (user.isAdmin()) {
                serverOnline = false;
                validSession = false;
                return TCProtocol.SHUTTING_DOWN;
            } else {
                return TCProtocol.INSUFFICIENT;
            }
        } else {
            return TCProtocol.INSUFFICIENT;
        }
    }
}
