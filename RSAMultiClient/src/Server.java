import java.net.*;
import java.util.*;

public class Server {
    public static void main(String[] args) {
        ArrayList<ServerThread> threadList = new ArrayList<ServerThread>();

        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(clientSocket, threadList);

                threadList.add(serverThread);
                serverThread.start();
            }
        } catch (Exception e) {
            System.out.println("Error occured: " + e.getStackTrace());
        }
    }
}
