import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

public class VotingServer {
    private final KeyPair keyPair;
    private final Set<String> usedTokens = new HashSet<>();
    private final Authority authority;

    public VotingServer(Authority authority) throws Exception {
        this.authority = authority;
        keyPair = Utils.generateRSAKeyPair();
    }

    public String signServerVerificationCode(String serverVerificationCode) throws Exception {
        return Utils.sign(serverVerificationCode, keyPair.getPrivate());
    }
  
    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    public void receiveAnonymousVote(VotePackage pkg) throws Exception {
        receiveVote(pkg);
    }

    public void receiveVote(VotePackage pkg) throws Exception {
        System.out.println("Step 3 >>> Server verifying vote package...");
        if (usedTokens.contains(pkg.token)) {
            System.err.println("Duplicate vote detected (token reused). Vote rejected.");
            return;
        }

        boolean verified = Utils.verify(pkg.token, pkg.signedToken, authority.getPublicKey());
        if (!verified) {
            System.err.println("Invalid token signature. Vote rejected.");
            return;
        }
        System.out.println("Vote Package verified successfully.");
        

        // Decrypt AES key
        String aesKeyEncoded = Utils.decryptRSA(pkg.encryptedAESKey, keyPair.getPrivate());
        byte[] aesBytes = Base64.getDecoder().decode(aesKeyEncoded);
        SecretKey aesKey = new SecretKeySpec(aesBytes, "AES");

        // Decrypt vote
        String vote = Utils.decryptAES(pkg.encryptedVote, aesKey);

        usedTokens.add(pkg.token);
        System.out.println("Anonymous vote accepted: " + vote);
    }
}
