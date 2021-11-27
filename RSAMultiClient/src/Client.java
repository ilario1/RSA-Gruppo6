import java.net.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000)) {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            String userInput;
            String response;
            String clientName = "empty";
            String recipientName;
            int contThread = 0;

            ClientThread clientThread = new ClientThread(socket);

            do {
                if (clientName.equals("empty")) {
                    String answer = "";
                    do {
                        System.out.println("Enter your name: ");
                        userInput = scanner.nextLine();
                        output.println(userInput);
                        if (contThread == 0) {
                            clientThread.start();
                            contThread = 1;
                        }
                        do {
                            answer = clientThread.getResponse();
                        } while (answer.equals(""));
                        System.out.println(answer);
                    } while (answer.equals("already exists"));

                    clientName = userInput;
                    if (userInput.equals("exit")) {
                        break;
                    }

                } else {
                    String message = ("(" + clientName + ")" + " message: ");

                    /*
                     * String answer = ""; do {
                     * 
                     * answer = input.readLine(); } while (answer.equals("cant write to urself"));
                     */

                    System.out.println("Writing to: ");
                    recipientName = scanner.nextLine();
                    output.println(recipientName);

                    System.out.println("Your message: ");
                    userInput = scanner.nextLine();
                    output.println(message + " " + userInput);
                    if (userInput.equals("exit")) {
                        break;
                    }
                }
            } while (!userInput.equals("exit"));

        } catch (Exception e) {
            System.out.println("Exception occured in client main: " + e.getMessage());
        }
    }
}
