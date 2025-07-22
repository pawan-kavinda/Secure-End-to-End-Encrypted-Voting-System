import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Base64;

public class Voter {
    public KeyPair keyPair;

    public Voter() throws Exception {
        keyPair = Utils.generateRSAKeyPair();
    }

    public VotePackage prepareVote(String vote, PublicKey serverPubKey) throws Exception {
        String hash = Utils.hashSHA256(vote);
        String signature = Utils.sign(hash, keyPair.getPrivate());

        SecretKey aesKey = Utils.generateAESKey();
        String encryptedVote = Utils.encryptAES(vote, aesKey);
        String encryptedAESKey = Utils.encryptRSA(Base64.getEncoder().encodeToString(aesKey.getEncoded()), serverPubKey);

        return new VotePackage(encryptedVote, encryptedAESKey, signature, keyPair.getPublic(), hash);
    }
}
