package client;

import service.TCProtocol;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuClientMovie {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        // Requests a connection
        try (Socket dataSocket = new Socket(TCProtocol.HOST, TCProtocol.PORT)) {
            try (Scanner input = new Scanner(dataSocket.getInputStream());
                 PrintWriter output = new PrintWriter(dataSocket.getOutputStream())) {

                boolean validSession = true;
                while (validSession) {
                    displayLandingPage();

                    int choice = getUserChoice(userInput);

                    String message = "";
                    switch (choice) {
                        case 1:
                            message = login(userInput);
                            break;
                        case 2:
                            message = register(userInput);
                            break;
                        case 3:
                            message = TCProtocol.EXIT;
                            validSession = false;
                            break;
                        default:
                            System.out.println("Invalid choice. Please select a valid option.");
                            break;
                    }

                    output.println(message);
                    output.flush();

                    String response = input.nextLine();
                    System.out.println("Received from server: " + response);

                    if (response.equals(TCProtocol.ADMIN)) {
                        adminFunctionality(userInput, input, output);
                    } else if (response.equals(TCProtocol.USER)) {
                        userFunctionality(userInput, input, output);
                    }
                }

            }
        } catch (UnknownHostException e) {
            System.out.println("Host cannot be found at this moment. Try again later");
        } catch (IOException e) {
            System.out.println("An IO Exception occurred: " + e.getMessage());
        }
    }

    private static void displayLandingPage() {
        System.out.println("Welcome to Movie Service:");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
    }


    private static void userFunctionality(Scanner userInput, Scanner input, PrintWriter output) {
        boolean validSession = true;
        while (validSession) {
            displayUserMenu();

            int choice = getUserChoice(userInput);

            String message = "";
            switch (choice) {
                case 1:
                    message = rateFilm(userInput);
                    break;
                case 2:
                    message = searchByGenre(userInput);
                    break;
                case 3:
                    message = searchByTitle(userInput);
                    break;
                case 4:
                    message = logout();
                    break;
//                case 5:
//                    message = TCProtocol.EXIT;
//                    validSession = false;
//                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }

            output.println(message);
            output.flush();

            String response = input.nextLine();
            System.out.println("Received from server: " + response);
            if (response.equals(TCProtocol.GOODBYE) || response.equals(TCProtocol.LOGGED_OUT)) {
                validSession = false;
            }
        }
    }


    private static void adminFunctionality(Scanner userInput, Scanner input, PrintWriter output) {
        boolean validSession = true;
        while (validSession) {
            displayAdminMenu();

            int choice = getUserChoice(userInput);

            String message = "";
            switch (choice) {
                case 1:
                    message = addFilm(userInput);
                    break;
                case 2:
                    message = removeFilm(userInput);
                    break;
                case 3:
                    message = shutdownServer();
                    break;
                case 4:
                    message = TCProtocol.EXIT;
                    validSession = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }

            output.println(message);
            output.flush();

            String response = input.nextLine();
            System.out.println("Received from server: " + response);
            if (response.equals(TCProtocol.GOODBYE)) {
                validSession = false;
            }
        }
    }

    private static void displayUserMenu() {
        System.out.println("User Menu:");
        System.out.println("1. Rate a film");
        System.out.println("2. Search by title");
        System.out.println("3. Search by genre");
        System.out.println("4. Logout");
//        System.out.println("5. Exit");
    }

    private static void displayAdminMenu() {
        System.out.println("Admin Menu:");
        System.out.println("1. Add a film");
        System.out.println("2. Remove a film");
        System.out.println("3. Shutdown server");
        System.out.println("4. Exit");
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

    private static String login(Scanner userInput) {
        System.out.print("Enter username: ");
        String username = userInput.nextLine();
        System.out.print("Enter password: ");
        String password = userInput.nextLine();
        return TCProtocol.LOGIN + TCProtocol.DELIMITER + username + TCProtocol.DELIMITER + password;
    }

    private static String register(Scanner userInput) {
        System.out.print("Enter username: ");
        String username = userInput.nextLine();
        System.out.print("Enter password: ");
        String password = userInput.nextLine();
        return TCProtocol.REGISTER + TCProtocol.DELIMITER + username + TCProtocol.DELIMITER + password;
    }

    private static String rateFilm(Scanner userInput) {
        System.out.print("Enter film title: ");
        String title = userInput.nextLine();
        System.out.print("Enter your rating (1-5): ");
        int rating = userInput.nextInt();
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

    private static String shutdownServer() {
        return TCProtocol.SHUTDOWN;
    }
}
