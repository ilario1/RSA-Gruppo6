import java.net.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;

/**
 * @author Gallo Valerio
 * @since 02/12/2021
 *        Classe ServerThread, contiene il costruttore degli ogetti di tipo
 *        ServerThread, e il metodo runnable di questi ultimi.
 *        Il metodo runnable si divide in due parti, nella prima viene
 *        effettuato il controllo che il nome del client associato al
 *        ServerThread non sia uguale ad un altro
 *        già esistente, inoltre viene anche condivisa la chiave pubblica del
 *        client connesso con il Server. Nella seconda parte del metodo invece
 *        viene effettuata la condivisione
 *        della chiave del ricevente con il client che sta scrivendo, e l'invio
 *        del messaggio criptato al client ricevente.
 */
public class ServerThread extends Thread {
    /**
     * Oggetto che contiente il socket del client connesso al server,
     */
    private Socket socket;
    /**
     * ArrayList che contiene tutti i ServerThread associati al server.
     */
    private static ArrayList<ServerThread> threadList;
    /**
     * ArrayList che contiene tutte le chiavi pubbliche dei client connessi al
     * server.
     */
    private static ArrayList<Keys> pubKeys = new ArrayList<Keys>();
    /**
     * Oggetto utilizzato per inviare le informazioni al client, che vengono
     * ricevute dal ClientThread.
     */
    public PrintWriter output;
    /**
     * Nome del client ricevente.
     */
    private String recipientName;
    /**
     * Nome del client associato al ServerThread.
     */
    public String clientName;
    /**
     * Variabile di controllo.
     */
    private boolean nameIn = false;

    /**
     * 
     * @param socket     Socket del client associato al ServerThread.
     * @param threadList ArrayList che contiente tutti i ServerThread.
     *                   Metodo costruttore del ServerThread.
     */
    public ServerThread(Socket socket, ArrayList<ServerThread> threadList) {
        this.socket = socket;
        ServerThread.threadList = threadList;
    }

    @Override
    public void run() {
        try {
            /**
             * Oggetto utilizzato per ricevere dati dal client.
             */
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
                printToClient(outputString);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Socket closed");
        }
    }

    /**
     * 
     * @param outputString Messaggio da inviare al ricevente.
     *                     Metodo che manda il messaggio criptato al client
     *                     ricevente.
     */
    private synchronized void printToClient(String outputString) {
        for (ServerThread sT : threadList) {
            if (sT.clientName.equals(this.recipientName)) {
                sT.output.println(outputString);
            }
        }
    }

    /**
     * 
     * @param clientName Nome del client connesso al ServerThread.
     * @param key        Chiave pubblica del client ricevente.
     *                   Metodo che manda la chiave pubblica del client ricevente al
     *                   client connesso al ServerThread, in modo da criptare il
     *                   messaggio da mandare.
     */
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
