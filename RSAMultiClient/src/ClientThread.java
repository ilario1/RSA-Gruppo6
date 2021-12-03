import java.net.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

/**
 * @author Gallo Valerio
 * @since 02/12/2021
 *        Classe ClientThread che contiene il metodo costruttore dell'oggetto
 *        ClientThread, e il metodo runnable associato.
 *        In questa classe viene fatta la ricezione dei messaggi mandati dal
 *        server, e la decriptazione dei messaggi in arrivo.
 * 
 */
public class ClientThread extends Thread {
    /**
     * Socket del client associato al thread.
     */
    private Socket socket;
    /**
     * Oggetto utilizzato per prendere in input le informazioni mandate dal server.
     */
    private BufferedReader input;
    /**
     * Oggetto che contiene l'informazione mandata dal server.
     */
    private String response = "";
    /**
     * Chiave pubblica del client che deve ricevere l'informazione.
     */
    private static BigInteger[] key = new BigInteger[2];
    /**
     * Chiave privata del client associato al thread.
     */
    private BigInteger[] thisClientKey;

    /**
     * 
     * @param s       Socket del client associato al thread
     * @param privKey Chiave private del client associato al thread.
     * @throws IOException Serve per gestire gli errori.
     *                     Metodo costruttore del ClientThread.
     */
    public ClientThread(Socket s, BigInteger[] privKey) throws IOException {
        this.socket = s;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.thisClientKey = privKey;
    }

    /**
     * Metodo runnable del thread associato al client, prende in input tutte le
     * informazioni mandate dal server al client, smista la chiave del client
     * ricevente, e decripta il messaggio inviato da un altro client attraverso la
     * sua stessa chiave privata.
     * 
     */
    @Override
    public void run() {
        try {
            while (true) {
                response = input.readLine();

                if (response.equals("invio key")) {
                    key[0] = new BigInteger(input.readLine());
                    key[1] = new BigInteger(input.readLine());
                } else if (response.equals("va bene") || response.equals("already exists")
                        || response.equals("cant write to urself") || response.equals("1")) {
                } else {
                    synchronized (this) {
                        BigInteger responseDecrypted = new BigInteger(response);
                        responseDecrypted = responseDecrypted.modPow(thisClientKey[0], thisClientKey[1]);
                        System.out.println(new String(responseDecrypted.toByteArray()));
                    }
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

    /**
     * Metodo getter che manda alla classe madre Client il risultato del controllo
     * del nome del client.
     * 
     */
    public String getResponse() {
        return response;
    }

    /**
     * Metodo getter che manda alla classe madre Client la chiave pubblica del
     * client ricevente, arrivata dal server.
     * 
     */
    public static BigInteger[] getKey() {
        return key;
    }
}
