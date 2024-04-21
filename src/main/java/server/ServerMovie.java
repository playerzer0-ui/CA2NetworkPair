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
    private static User user;
    private static boolean validSession;

    public static void main(String[] args) {
//        FilmManager filmManager = new FilmManager();
//        filmManager.setData();
//        UserManager userManager = new UserManager();

        System.out.println("movie server online...");
        try(ServerSocket serverSocket = new ServerSocket(TCProtocol.PORT)){

            while(serverOnline){
                Socket socket = serverSocket.accept();
                ServerThreadHandler threadHandler = new ServerThreadHandler(socket);
                Thread worker = new Thread(threadHandler);
                worker.start();

                if(!ServerThreadHandler.serverOnline){
                    serverOnline = false;

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
}


