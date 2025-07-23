import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Authority {
    private final KeyPair keyPair;

    public Authority() throws Exception {
        keyPair = Utils.generateRSAKeyPair();
    }

    public String signToken(String token) throws Exception {
        return Utils.sign(token, keyPair.getPrivate());
    }

    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }
}
