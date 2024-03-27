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
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ServerMovie {
    public static void main(String[] args) {
        FilmManager filmManager = new FilmManager();
        UserManager userManager = new UserManager();
        User user;

        System.out.println("movie server online...");
        try(ServerSocket serverSocket = new ServerSocket(TCProtocol.PORT)){

            while(true){
                Socket socket = serverSocket.accept();
                boolean validSession = true;
                user = null;

                try(Scanner input = new Scanner(socket.getInputStream()); PrintWriter output = new PrintWriter(socket.getOutputStream())){

                    while(validSession){
                        String request = input.nextLine();
                        System.out.println(request);
                        String[] components = request.split(TCProtocol.DELIMITER);
                        String response = null;

                        switch (components[0]){
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

                                break;

                            case TCProtocol.SEARCH_GENRE:
                                break;
                        }

                        output.println(response);
                        output.flush();
                    }
                }
            }
        }
        catch (BindException e) {
            System.out.println("BindException occurred when attempting to bind to port " + 12321);
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
