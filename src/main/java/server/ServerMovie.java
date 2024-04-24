package server;

import service.FilmManager;
import service.TCProtocol;
import service.UserManager;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMovie {
    private static boolean serverOnline = true;
    private static FilmManager filmManager;
    private static UserManager userManager;


    public static void main(String[] args) {
        filmManager = new FilmManager();
        filmManager.setData();
        userManager = new UserManager();

        System.out.println("movie server online...");
        try(ServerSocket serverSocket = new ServerSocket(TCProtocol.PORT)){

            while(serverOnline){
                Socket socket = serverSocket.accept();
                ServerThreadHandler threadHandler = new ServerThreadHandler(socket, filmManager, userManager);
                Thread worker = new Thread(threadHandler);
                worker.start();
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

    public static void setServerOnline(boolean online) {
        serverOnline = online;
    }
}


