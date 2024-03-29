package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import service.TCProtocol;

public class ClearClientMovie {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        // Requests a connection
        try (Socket dataSocket = new Socket(TCProtocol.HOST, TCProtocol.PORT)) {
            try (Scanner input = new Scanner(dataSocket.getInputStream());
                 PrintWriter output = new PrintWriter(dataSocket.getOutputStream())) {
                boolean validSession = true;
                while (validSession) {
                    displayMenu();

                    int choice = getUserChoice(userInput);

                    String message = "";
                    switch (choice) {
                        case 1:
                            message = searchByTitle(userInput);
                            break;
                        case 2:
                            message = searchByGenre(userInput);
                            break;
                        case 3:
                            message = addFilm(userInput);
                            break;
                        case 4:
                            message = removeFilm(userInput);
                            break;
                        case 5:
                            message = TCProtocol.EXIT;
                            validSession = false;
                            break;
                        default:
                            System.out.println("Invalid choice. Please select a valid option.");
                            continue;
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

        } catch (UnknownHostException e) {
            System.out.println("Host cannot be found at this moment. Try again later");
        } catch (IOException e) {
            System.out.println("An IO Exception occurred: " + e.getMessage());
        }
    }

    private static void displayMenu() {
        System.out.println("Please select an option:");
        System.out.println("1. Search by title");
        System.out.println("2. Search by genre");
        System.out.println("3. Add a film");
        System.out.println("4. Remove a film");
        System.out.println("5. Exit");
    }

    private static int getUserChoice(Scanner userInput) {
        System.out.print("Your choice: ");
        return userInput.nextInt();
    }

    private static String searchByTitle(Scanner userInput) {
        userInput.nextLine();
        System.out.print("Enter film title: ");
        String title = userInput.nextLine();
        return TCProtocol.SEARCH_NAME + TCProtocol.DELIMITER + title;
    }

    private static String searchByGenre(Scanner userInput) {
        userInput.nextLine();
        System.out.print("Enter genre: ");
        String genre = userInput.nextLine();
        return TCProtocol.SEARCH_GENRE + TCProtocol.DELIMITER + genre;
    }

    private static String addFilm(Scanner userInput) {
        userInput.nextLine();
        System.out.print("Enter film title: ");
        String title = userInput.nextLine();
        System.out.print("Enter film genre: ");
        String genre = userInput.nextLine();
        return TCProtocol.ADD + TCProtocol.DELIMITER + title + TCProtocol.DELIMITER + genre;
    }

    private static String removeFilm(Scanner userInput) {
        userInput.nextLine();
        System.out.print("Enter film title to remove: ");
        String title = userInput.nextLine();
        return TCProtocol.REMOVE + TCProtocol.DELIMITER + title;
    }
}

