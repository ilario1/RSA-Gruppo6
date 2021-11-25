import java.net.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ClientThread extends Thread {
    private Socket socket;
    private BufferedReader input;

    public ClientThread(Socket s) throws IOException {
        this.socket = s;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            while (true) {
                String response = input.readLine();
                if (response.equals("nice") || response.equals("already exists")
                        || response.equals("cant write to urself")) {
                    output.println(response);
                } else {
                    System.out.println(response);
                }
            }
        } catch (Exception e) {
            System.out.println("sroigroihgrsoihg");
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
