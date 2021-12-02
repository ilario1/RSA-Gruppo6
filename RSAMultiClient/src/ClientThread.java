import java.net.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class ClientThread extends Thread {
    private Socket socket;
    private BufferedReader input;
    private String response = "";
    private static BigInteger[] key;
    private BigInteger[] thisClientKey;
    private boolean check = true;

    public ClientThread(Socket s, BigInteger[] privKey) throws IOException {
        this.socket = s;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.thisClientKey = privKey;
    }

    @Override
    public void run() {
        try {
            while (true) {
                response = input.readLine();
                if (!(response.equals("va bene") || response.equals("already exists"))) {
                    key[0] = new BigInteger(input.readLine());
                    key[1] = new BigInteger(input.readLine());
                }
                if (response.equals("va bene") || response.equals("already exists")
                        || response.equals("cant write to urself")) {
                } else {
                    BigInteger responseDecrypted = new BigInteger(response);
                    responseDecrypted = responseDecrypted.modPow(thisClientKey[0], thisClientKey[1]);
                    System.out.println(new String(responseDecrypted.toByteArray()));
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

    public static BigInteger[] getKey() {
        return key;
    }
}
