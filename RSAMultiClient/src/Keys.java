import java.math.BigInteger;

/**
 * @author Gallo Valerio
 * @since 02/12/2021
 *        Classe che contiene il costruttore della lista di chiavi pubbliche dei
 *        client.
 */
public class Keys {
    /**
     * Nome del client al quale appartiene la chiave privata.
     */
    private String clientName;
    /**
     * Chiave pubblica del client.
     */
    private BigInteger[] key;

    /**
     * Metodo costruttore delle chiavi pubbliche.
     * 
     * @param clientName Nome del client al quale appartiene la chiave privata.
     * @param key        Chiave pubblica del client.
     */
    public Keys(String clientName, BigInteger[] key) {
        this.clientName = clientName;
        this.key = key;
    }

    /**
     * Metodo getter che rimanda al ServerThread il nome del client al quale
     * appartiene la chiave.
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Metodo getter che rimanda al ServerThread la chiave pubblica del client
     * richiesto.
     * 
     */
    public BigInteger[] getKey() {
        return key;
    }
}
