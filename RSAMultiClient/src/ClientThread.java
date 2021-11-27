import java.net.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class ClientThread extends Thread {
    private Socket socket;
    private BufferedReader input;
    private String response;

    public ClientThread(Socket s) throws IOException {
        this.socket = s;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            while (true) {
                response = input.readLine();
                if (response.equals("va bene") || response.equals("already exists")
                        || response.equals("cant write to urself")) {
                    System.out.println("Passo al client " + response);
                } else {
                    System.out.println(response);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getResponse() {
        return response;
    }
}
