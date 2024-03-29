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
        try (Socket dataSocket = new Socket(TCProtocol.HOST, TCProtocol.PORT)) {


            try (Scanner input = new Scanner(dataSocket.getInputStream());
                 PrintWriter output = new PrintWriter(dataSocket.getOutputStream())) {
                boolean validSession = true;
                while (validSession) {
                    System.out.println("Please select an option:");
                    System.out.println("1. Search by title");
                    System.out.println("2. Search by genre");
                    System.out.println("3. Add a film");
                    System.out.println("4. Remove a film");
                    System.out.println("5. Exit");


                    System.out.print("Your choice: ");
                    int choice = userInput.nextInt();
                    userInput.nextLine();


                    String message = "";
                    switch (choice) {
                        case 1:
                            System.out.print("Enter film title: ");
                            message = "searchByName%%" + userInput.nextLine();
                            break;
                        case 2:
                            System.out.print("Enter genre: ");
                            message = "searchByGenre%%" + userInput.nextLine();
                            break;
                        case 3:
                            System.out.print("Enter film title: ");
                            String title = userInput.nextLine();
                            System.out.print("Enter film genre: ");
                            String genre = userInput.nextLine();
                            message = "add%%" + title + "%%" + genre;
                            break;
                        case 4:
                            System.out.print("Enter film title to remove: ");
                            message = "remove%%" + userInput.nextLine();
                            break;
                        case 5:
                            message = TCProtocol.EXIT;
                            validSession = false;
                            break;
                        default:
                            System.out.println("Invalid choice. Please select a valid option.");
                            continue;
                    }

                    // Send message to server
                    output.println(message);
                    output.flush();

                    // Receive message from server
                    String response = input.nextLine();
                    // Display result to user
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
        // Close connection to server
    }

}
