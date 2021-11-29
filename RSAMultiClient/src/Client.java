import java.net.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.*;

public class Client {
    static BigInteger[] pubKey = new BigInteger[2];
    static BigInteger[] privKey = new BigInteger[2];

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
            boolean checkMethod = false;

            BigInteger msgToCrypt = new BigInteger("0");
            ClientThread clientThread = new ClientThread(socket);
            checkMethod = KeyGen();

            do {
                if (clientName.equals("empty")) {
                    String answer;
                    do {
                        answer = "";
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
                    byte[] bufi = scanner.nextLine().getBytes("UTF-8");
                    msgToCrypt = new BigInteger(bufi);
                    BigInteger msgCrypted = msgToCrypt.modPow(pubKey[0], pubKey[1]);
                    output.println(message + " " + msgCrypted);
                    output.println(pubKey[0]);
                    output.println(pubKey[1]);
                }
            } while (!msgToCrypt.equals("exit"));

        } catch (Exception e) {
            System.out.println("Exception occured in client main: " + e.getMessage());
        }
    }

    public static boolean KeyGen() throws IOException {
        BigInteger p = new BigInteger("0");
        BigInteger q = new BigInteger("0");
        BigInteger n = new BigInteger("0");
        BigInteger z = new BigInteger("0");

        int linesNum = (int) Files.lines(Paths.get("prime.txt")).count();
        p = new BigInteger(Files.readAllLines(Paths.get("prime.txt")).get((int) (Math.random() * linesNum)));
        do {
            q = new BigInteger(Files.readAllLines(Paths.get("prime.txt")).get((int) (Math.random() * linesNum)));
        } while (q.equals(p));

        n = p.multiply(q);
        z = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        BigInteger e = new BigInteger("1");
        do {
            e = new BigInteger(Files.readAllLines(Paths.get("prime.txt")).get((int) (Math.random() * linesNum)));
        } while (e.compareTo(z) == 1 || e.equals(q) || e.equals(p));

        BigInteger d = new BigInteger("0");
        d = e.modInverse(z);

        privKey[0] = d;
        privKey[1] = n;

        pubKey[0] = e;
        pubKey[1] = n;

        return true;
    }
}
