import java.net.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;

public class ServerThread extends Thread {
    private Socket socket;
    private static ArrayList<ServerThread> threadList;
    private static ArrayList<Keys> pubKeys;
    public PrintWriter output;
    private String recipientName;
    public String clientName;

    public ServerThread(Socket socket, ArrayList<ServerThread> threadList) {
        this.socket = socket;
        this.threadList = threadList;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

            while (true) {
                clientName = input.readLine();
                boolean checkCName = true;
                int countName = 0;
                for (ServerThread sT : threadList) {
                    if (this.clientName.equals(sT.clientName)) {
                        countName++;
                        System.out.println(countName);
                        if (countName == 2) {
                            System.out.println("trovato nome uguale");
                            String answerC1 = "already exists";
                            output.println(answerC1);
                            checkCName = false;
                            break;
                        }
                    }
                }
                if (checkCName) {
                    System.out.println("nome disponibile");
                    String answerC1 = "va bene";
                    output.println(answerC1);
                    break;
                }
            }
            while (true) {
                recipientName = input.readLine();
                /*
                 * boolean checkWritingTo = true; if
                 * (this.clientName.equals(this.recipientName)) { String answer =
                 * "cant write to urself"; this.output.println(answer); checkWritingTo = false;
                 * } if (checkWritingTo) { String answerC2 = "va bene";
                 * output.println(answerC2); }
                 */
                String outputString = input.readLine();
                int indexValue = 0;
                boolean check = false;
                for (int i = 0; i < pubKeys.size(); i++) {
                    if (pubKeys.get(i).getClientName().equals(this.clientName)) {
                        check = true;
                    }
                }
                if (!check) {
                    BigInteger firstKeyPart = new BigInteger(input.readLine());
                    BigInteger secondKeyPart = new BigInteger(input.readLine());
                    BigInteger[] key = { firstKeyPart, secondKeyPart };
                    Keys pubKey = new Keys(clientName, key);
                    pubKeys.add(pubKey);
                }

                for (int i = 0; i < pubKeys.size(); i++) {
                    if (pubKeys.get(i).getClientName().equals(this.recipientName)) {
                        indexValue = i;
                    }
                }
                if (outputString.equals("exit")) {
                    break;
                }
                printToClient(outputString, pubKeys.get(indexValue).getKey());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Socket closed");
        }
    }

    private void printToClient(String outputString, BigInteger[] key) {
        for (ServerThread sT : threadList) {
            if (sT.clientName.equals(this.recipientName)) {
                sT.output.println(outputString);
                sT.output.println(key[0]);
                sT.output.println(key[1]);
            }
        }
    }
}
