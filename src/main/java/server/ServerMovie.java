package server;

import business.User;
import commands.*;
import service.FilmManager;
import service.TCProtocol;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerMovie {
    public static boolean serverOnline = true;
    public static User user;
    public static boolean validSession;

    public static void main(String[] args) {
        FilmManager filmManager = new FilmManager();
        filmManager.setData();
        Command command;

        System.out.println("movie server online...");
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

                        switch (components[0]){

                            case TCProtocol.REGISTER:
                                command = new RegisterCommand();
                                break;

                            case TCProtocol.LOGIN:
                                command = new LoginCommand();
                                break;

                            case TCProtocol.LOGOUT:
                                command = new LogoutCommand();
                                break;


                            case TCProtocol.RATE:
                                command = new RateCommand();
                                break;

                            case TCProtocol.SEARCH_NAME:
                                command = new SearchNameCommand();
                                break;

                            case TCProtocol.SEARCH_GENRE:
                                command = new SearchGenreCommand();
                                break;

                            case TCProtocol.ADD:
                                command = new AddFilmCommand();
                                break;

                            case TCProtocol.REMOVE:
                                command = new RemoveFilmCommand();
                                break;

                            case TCProtocol.EXIT:
                                command = new ExitCommand();
                                break;

                            case TCProtocol.SHUTDOWN:
                                command = new ShutDownCommand();
                                break;

                            default:
                                command = new InvalidCommand();
                                break;
                        }

                        output.println(command.execute(components));
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
