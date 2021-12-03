import java.net.*;
import java.util.*;

/**
 * @author Gallo Valerio
 * @since 02/12/2021
 *        Classe Server che accetta i client quando vengono avviati, e avvia un
 *        ServerThread ogni volta che un client viene accettato,
 *        immagazzinandoli in un ArrayList.
 */
public class Server {
    public static void main(String[] args) {
        /**
         * ArrayList in cui sono conenuti gli oggetti di tipo ServerThread.
         */
        ArrayList<ServerThread> threadList = new ArrayList<ServerThread>();

        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(clientSocket, threadList);

                threadList.add(serverThread);
                serverThread.start();
            }
        } catch (Exception e) {
            System.out.println("Error occured: " + e.getMessage());
        }
    }
}
