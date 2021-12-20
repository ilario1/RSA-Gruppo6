import java.net.*;
import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.*;

/**
 * @author Gallo Valerio
 * @since 02/12/2021
 *        Classe Client, contiene l'inserimento del nome del client, l'invio e
 *        la crittazione del messaggio ad un altro client, e la generazione
 *        delle relative chiavi pubblica e privata.
 *        Per leggere le informazioni inviate dal server in tempo reale, viene
 *        utilizzato un thread associato al client che è sempre pronto a
 *        ricevere i messaggi dal server.
 */
public class Client {
    /**
     * Chiave pubblica del client.
     */
    static BigInteger[] pubKey = new BigInteger[2];
    /**
     * Chiave privata del client.
     */
    static BigInteger[] privKey = new BigInteger[2];
    /**
     * Chiave pubblica del client che deve ricevere il messaggio.
     */
    static BigInteger[] pubKeyReciever = new BigInteger[2];

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 6500); Scanner scanner = new Scanner(System.in);) {
            /**
             * Oggetto utilizzato per mandare le infomazioni al server.
             */
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            /**
             * Oggetto che contiene l'input dell'utente.
             */
            String userInput;
            /**
             * Nome assegnato dall'utente al client, se vale "empty" viene fatta
             * l'assegnazione di un nome scelto dall'utente.
             */
            String clientName = "empty";
            /**
             * Nome del client che deve ricevere il messaggio.
             */
            String recipientName;
            /**
             * Valore che serve per determinare se il ClientThread associato al client è
             * stato avviato.
             */
            int contThread = 0;
            /**
             * Messaggio originario da criptare.
             */
            BigInteger msgToCrypt = new BigInteger("0");

            KeyGen();
            /**
             * Oggetto ClientThread che inizializza il thread associato al client.
             */
            ClientThread clientThread = new ClientThread(socket, privKey);

            while (true) {
                if (clientName.equals("empty")) {
                    String answer;
                    do {
                        answer = "";
                        System.out.println("Enter your name: ");
                        userInput = scanner.nextLine();
                        output.println(userInput);
                        if (contThread == 0) {
                            clientThread.start();
                            contThread = 1;
                        }
                        do {
                            answer = clientThread.getResponse();
                        } while (answer.equals(""));
                        System.out.println(answer);
                    } while (answer.equals("already exists"));

                    clientName = userInput;
                    output.println(pubKey[0]);
                    output.println(pubKey[1]);

                } else {
                    System.out.println("Writing to: ");
                    recipientName = scanner.nextLine();
                    output.println(recipientName);

                    String msg;
                    System.out.println("Your message: ");
                    msg = scanner.nextLine();
                    byte[] bufi = ("<" + clientName + "> " + msg).getBytes();
                    msgToCrypt = new BigInteger(bufi);
                    pubKeyReciever = ClientThread.getKey();
                    BigInteger msgCrypted = msgToCrypt.modPow(pubKeyReciever[0], pubKeyReciever[1]);
                    output.println(msgCrypted);

                }
            }

        } catch (Exception e) {
            System.out.println("Exception occured in client main: " + e.getMessage());
        }
    }

    /**
     * Metodo che genera le chiavi pubblica e privata del client, prendendo i numeri
     * primi da un file.
     */
    public static void KeyGen() {
        BigInteger p = new BigInteger("0");
        BigInteger q = new BigInteger("0");
        BigInteger n = new BigInteger("0");
        BigInteger z = new BigInteger("0");
        try {
            String fileName = "prime.txt";
            int linesNum = (int) Files.lines(Paths.get(fileName)).count();
            p = new BigInteger(Files.readAllLines(Paths.get(fileName)).get((int) (Math.random() * linesNum)));
            do {
                q = new BigInteger(Files.readAllLines(Paths.get(fileName)).get((int) (Math.random() * linesNum)));
            } while (q.equals(p));

            n = p.multiply(q);
            z = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

            BigInteger e = new BigInteger("1");
            do {
                e = new BigInteger(Files.readAllLines(Paths.get(fileName)).get((int) (Math.random() * linesNum)));
            } while (e.compareTo(z) == 1 || e.equals(q) || e.equals(p));

            BigInteger d = new BigInteger("0");
            d = e.modInverse(z);

            privKey[0] = d;
            privKey[1] = n;

            pubKey[0] = e;
            pubKey[1] = n;

        } catch (Exception e) {
            e.getMessage();
        }

    }
}
