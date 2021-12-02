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
    private static ArrayList<Keys> pubKeys = new ArrayList<Keys>();
    public PrintWriter output;
    private String recipientName;
    public String clientName;
    private boolean nameIn = false;

    public ServerThread(Socket socket, ArrayList<ServerThread> threadList) {
        this.socket = socket;
        ServerThread.threadList = threadList;
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
                }
                BigInteger firstKeyPart = new BigInteger(input.readLine());
                BigInteger secondKeyPart = new BigInteger(input.readLine());
                BigInteger[] key = { firstKeyPart, secondKeyPart };
                Keys pubKey = new Keys(clientName, key);
                pubKeys.add(pubKey);
                nameIn = true;
                break;
            }

            while (nameIn) {
                recipientName = input.readLine();
                int indexValue = 0;
                for (int i = 0; i < pubKeys.size(); i++) {
                    if (pubKeys.get(i).getClientName().equals(this.recipientName)) {
                        indexValue = i;
                    }
                }

                printKeyToClient(clientName, pubKeys.get(indexValue).getKey());

                String outputString = input.readLine();

                if (outputString.equals("exit")) {
                    break;
                }
                printToClient(outputString);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Socket closed");
        }
    }

    private synchronized void printToClient(String outputString) {
        for (ServerThread sT : threadList) {
            if (sT.clientName.equals(this.recipientName)) {
                sT.output.println(outputString);
            }
        }
    }

    private synchronized void printKeyToClient(String clientName, BigInteger[] key) {
        for (ServerThread sT : threadList) {
            if (sT.clientName.equals(this.clientName)) {
                sT.output.println("invio key");
                sT.output.println(key[0]);
                sT.output.println(key[1]);
            }
        }
    }
}
