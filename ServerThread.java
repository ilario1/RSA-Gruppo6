import java.net.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ServerThread extends Thread {
    private Socket socket;
    private ArrayList<ServerThread> threadList;
    private PrintWriter output;

    public ServerThread(Socket socket, ArrayList<ServerThread> threadList) {
        this.socket = socket;
        this.threadList = threadList;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                String outputString = input.readLine();

                if (outputString.equals("exit")) {
                    break;
                }
                printToAllClients(outputString);
            }
        } catch (Exception e) {
            System.out.println("Socket closed");
        }
    }

    private void printToAllClients(String outputString) {
        for (ServerThread sT : threadList) {
            sT.output.println(outputString);
        }
    }
}
