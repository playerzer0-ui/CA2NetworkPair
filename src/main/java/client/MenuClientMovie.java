package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.Scanner;
import service.TCProtocol;

public class MenuClientMovie {
    private static boolean validSession;
    private static boolean isLogged;
    private static boolean isAdmin;
    public static void main(String[] args) {
        String message;
        int choice;
        Scanner userInput = new Scanner(System.in);
        // Requests a connection
        try (Socket dataSocket = new Socket(TCProtocol.HOST, TCProtocol.PORT)) {
            try (Scanner input = new Scanner(dataSocket.getInputStream());
                 PrintWriter output = new PrintWriter(dataSocket.getOutputStream())) {
                validSession = true;
                isLogged = false;
                isAdmin = false;

                while(validSession){
                    if(!isLogged){
                        displayLanding();

                        choice = getUserChoice(userInput);
                        message = generateRequestLanding(choice, userInput);
                    }
                    else{
                        if(!isAdmin){
                            displayMenu();

                            choice = getUserChoice(userInput);
                            message = generateRequestSession(choice, userInput);
                        }
                        else{
                            displayAdmin();

                            choice = getUserChoice(userInput);
                            message = generateRequestAdmin(choice, userInput);
                        }
                    }

                    output.println(message);
                    output.flush();

                    String response = input.nextLine();
                    //System.out.println("Received from server: " + response);

                    if(!isLogged){
                        handleResponseLanding(response);
                    }
                    else{
                        if(!isAdmin){
                            handleResponseSession(message, response);
                        }
                        else{
                            handleResponseAdmin(response);
                        }
                    }
                }

            }

        } catch (UnknownHostException e) {
            System.out.println("Host cannot be found at this moment. Try again later");
        } catch (IOException e) {
            System.out.println("An IO Exception occurred: " + e.getMessage());
        }
    }

    private static String generateRequestLanding(int choice, Scanner userInput){
        String request = null;
        switch (choice) {
            case 1:
                request = register(userInput);
                break;
            case 2:
                request = login(userInput);
                break;
            case 3:
                request = exit();
                break;
            default:
                System.out.println("Invalid choice. Please select a valid option.");
                break;
        }
        return request;
    }

    private static String generateRequestSession(int choice, Scanner userInput){
        String request = null;
        switch (choice) {
            case 1:
                request = rateFilm(userInput);
                break;
            case 2:
                request = searchByTitle(userInput);
                break;
            case 3:
                request = searchByGenre(userInput);
                break;
            case 4:
                request = exit();
                break;
            case 5:
                request = logout();
                break;
            default:
                System.out.println("Invalid choice. Please select a valid option.");
                break;
        }
        return request;
    }

    private static String generateRequestAdmin(int choice, Scanner userInput){
        String request = null;
        switch (choice) {
            case 1:
                request = addFilm(userInput);
                break;
            case 2:
                request = removeFilm(userInput);
                break;
            case 3:
                request = shutdownServer();
                break;
            case 4:
                request = exit();
                break;
            case 5:
                request = logout();
                break;
            default:
                System.out.println("Invalid choice. Please select a valid option.");
                break;
        }
        return request;
    }

    private static void handleResponseLanding(String response){
        switch (response){
            case TCProtocol.ADDED:
                System.out.println("added to the program");
                break;

            case TCProtocol.REJECTED:
                System.out.println("register rejected, check credentials and make sure that is not in the program already");
                break;

            case TCProtocol.USER:
                isLogged = true;
                isAdmin = false;
                System.out.println("login success, welcome user");
                break;

            case TCProtocol.FAILED:
                System.out.println("login failed, try again");
                break;

            case TCProtocol.ADMIN:
                isLogged = true;
                isAdmin = true;
                System.out.println("login success, welcome admin");
                break;

            case TCProtocol.GOODBYE:
                validSession = false;
                break;

            default:
                System.out.println("invalid request, try again");
                break;
        }
    }

    private static void handleResponseSession(String request, String response){
        switch (response){
            case TCProtocol.SUCCESS:
                System.out.println("rated a film");
                break;

            case TCProtocol.INVALID_RATING_SUPPLIED:
                System.out.println("rating supplied is invalid");
                break;

            case TCProtocol.NO_MATCH_FOUND:
                System.out.println("can't find the title you are looking for");
                break;

            case TCProtocol.NOT_LOGGED_IN:
                System.out.println("not logged in, request denied");
                break;


            case TCProtocol.LOGGED_OUT:
                isLogged = false;
                isAdmin = false;
                break;

            case TCProtocol.GOODBYE:
                validSession = false;
                break;

            default:
                String[] requestComponents = request.split(TCProtocol.DELIMITER);

                if(requestComponents[0].equals(TCProtocol.SEARCH_NAME)){
                    String[] components = response.split(TCProtocol.DELIMITER);
                    displayFilm(components);
                }
                else if(requestComponents[0].equals(TCProtocol.SEARCH_GENRE)){
                    String[] components = response.split(TCProtocol.KWARG);
                    for(String filmResponse : components){
                        String[] filmString = filmResponse.split(TCProtocol.DELIMITER);
                        displayFilm(filmString);
                    }
                }
                else{
                    System.out.println("invalid request, try again");
                }
                break;
        }
    }

    private static void handleResponseAdmin(String response){
        switch (response){
            case TCProtocol.ADDED:
                System.out.println("added to the program");
                break;

            case TCProtocol.EXISTS:
                System.out.println("that film already exists");
                break;

            case TCProtocol.INSUFFICIENT:
                System.out.println("insufficient permissions, who are you?");
                break;

            case TCProtocol.REMOVED:
                System.out.println("film successfully removed");
                break;

            case TCProtocol.NOT_FOUND:
                System.out.println("film not found");
                break;

            case TCProtocol.SHUTTING_DOWN:
                System.out.println("server shut down, SERVER SHUT DOWN");
                break;

            case TCProtocol.LOGGED_OUT:
                isLogged = false;
                isAdmin = false;
                break;

            case TCProtocol.GOODBYE:
                validSession = false;
                break;

            default:
                System.out.println("invalid request, try again");
                break;
        }
    }

    private static void displayLanding(){
        System.out.println("Welcome to the film program:");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
    }

    private static void displayMenu() {
        System.out.println("Please select an option:");
        System.out.println("1. Rate a film");
        System.out.println("2. Search by title");
        System.out.println("3. Search by genre");
        System.out.println("4. Exit");
        System.out.println("5. Logout");
    }

    private static void displayAdmin(){
        System.out.println("1. Add a film");
        System.out.println("2. Remove a film");
        System.out.println("3. Shutdown server");
        System.out.println("4. Exit");
        System.out.println("5. Logout");
    }

    private static void displayFilm(String[] components){
        System.out.println("--------------------------------");
        System.out.println("movie title: " + components[0]);
        System.out.println("genre: " + components[1]);
        System.out.println("total ratings: " + components[2]);
        System.out.println("number of ratings: " + components[3]);
        System.out.println("--------------------------------");
    }

    private static int getUserChoice(Scanner userInput) {
        int choice;
        while (true) {
            try {
                System.out.print("Your choice: ");
                choice = userInput.nextInt();
                userInput.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                userInput.next();
            }
        }
        return choice;
    }

    private static String register(Scanner userInput) {
        System.out.print("Enter username: ");
        String username = userInput.nextLine();
        System.out.print("Enter password: ");
        String password = userInput.nextLine();
        return TCProtocol.REGISTER + TCProtocol.DELIMITER + username + TCProtocol.DELIMITER + password;
    }

    private static String login(Scanner userInput) {
        System.out.print("Enter username: ");
        String username = userInput.nextLine();
        System.out.print("Enter password: ");
        String password = userInput.nextLine();
        return TCProtocol.LOGIN + TCProtocol.DELIMITER + username + TCProtocol.DELIMITER + password;
    }

    private static String rateFilm(Scanner userInput) {
        System.out.print("Enter film title: ");
        String title = userInput.nextLine();
        System.out.print("Enter your rating (1-10): ");
        int rating = getUserChoice(userInput);
        return TCProtocol.RATE + TCProtocol.DELIMITER + title + TCProtocol.DELIMITER + rating;
    }

    private static String logout() {
        return TCProtocol.LOGOUT;
    }

    private static String searchByTitle(Scanner userInput) {
        System.out.print("Enter film title: ");
        String title = userInput.nextLine();
        return TCProtocol.SEARCH_NAME + TCProtocol.DELIMITER + title;
    }

    private static String searchByGenre(Scanner userInput) {
        System.out.print("Enter genre: ");
        String genre = userInput.nextLine();
        return TCProtocol.SEARCH_GENRE + TCProtocol.DELIMITER + genre;
    }

    private static String addFilm(Scanner userInput) {
        System.out.print("Enter film title: ");
        String title = userInput.nextLine();
        System.out.print("Enter film genre: ");
        String genre = userInput.nextLine();
        return TCProtocol.ADD + TCProtocol.DELIMITER + title + TCProtocol.DELIMITER + genre;
    }

    private static String removeFilm(Scanner userInput) {
        System.out.print("Enter film title to remove: ");
        String title = userInput.nextLine();
        return TCProtocol.REMOVE + TCProtocol.DELIMITER + title;
    }

    private static String shutdownServer(){
        return TCProtocol.SHUTDOWN;
    }

    private static String exit(){
        validSession = false;
        return TCProtocol.EXIT;
    }
}
