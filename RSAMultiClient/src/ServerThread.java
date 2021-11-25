import java.net.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ServerThread extends Thread {
    private Socket socket;
    private static ArrayList<ServerThread> threadList;
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
            output = new PrintWriter(socket.getOutputStream(), true);

            clientName = input.readLine();
            boolean checkCName = true;
            int countName = 0;
            for (ServerThread sT : threadList) {
                if (clientName.equals(sT.clientName)) {
                    countName++;
                    System.out.println(countName);
                    if (countName == 2) {
                        String answer = "already exists";
                        output.println(answer);
                        checkCName = false;
                        break;
                    }
                }
            }
            if (checkCName) {
                String answer = "nice";
                output.println(answer);
            }

            while (true) {
                recipientName = input.readLine();
                boolean checkWritingTo = true;
                if (this.clientName.equals(this.recipientName)) {
                    String answer = "cant write to urself";
                    this.output.println(answer);
                    checkWritingTo = false;
                    break;
                }
                if (checkWritingTo) {
                    String answer = "nice";
                    output.println(answer);
                }

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

    private void printToClient(String outputString) {
        for (ServerThread sT : threadList) {
            if (sT.clientName.equals(this.recipientName)) {
                sT.output.println(outputString);
            }
        }
    }
}
