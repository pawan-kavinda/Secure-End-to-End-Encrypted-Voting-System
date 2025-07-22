import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyPair;
import java.util.Base64;

public class VotingServer {
    public KeyPair keyPair;

    public VotingServer() throws Exception {
        keyPair = Utils.generateRSAKeyPair();
    }

    public void receiveVote(VotePackage pkg) throws Exception {
        // Decrypt AES key
        String aesKeyEncoded = Utils.decryptRSA(pkg.encryptedAESKey, keyPair.getPrivate());
        byte[] aesBytes = Base64.getDecoder().decode(aesKeyEncoded);
        SecretKey aesKey = new SecretKeySpec(aesBytes, "AES");

        // Decrypt vote
        String vote = Utils.decryptAES(pkg.encryptedVote, aesKey);

        // Verify signature
        String recomputedHash = Utils.hashSHA256(vote);
        boolean valid = Utils.verify(recomputedHash, pkg.signature, pkg.voterPubKey);

        if (valid) {
            System.out.println("Vote verified: " + vote);
        } else {
            System.out.println("Vote verification failed.");
        }
    }
}
