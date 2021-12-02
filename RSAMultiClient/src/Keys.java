import java.math.BigInteger;

public class Keys {
    private String clientName;
    private BigInteger[] key;

    public Keys(String clientName, BigInteger[] key) {
        this.clientName = clientName;
        this.key = key;
    }

    public String getClientName() {
        return clientName;
    }

    public BigInteger[] getKey() {
        return key;
    }
}
