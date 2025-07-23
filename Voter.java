import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Base64;

public class Voter {
    public KeyPair keyPair;
   
    public boolean verifyServerResponse(String serverVerificationCode, String signedServerVerificationCode, PublicKey serverPubKey) throws Exception {
       System.out.println("Step 1 >>> Alice verifies server response...");
       System.out.println("");
       System.out.println("Server Verification Code: " + serverVerificationCode);
       System.out.println("");
       System.out.println("Signed Server Verification Code: " + signedServerVerificationCode);
       System.out.println("");
       System.out.println("Server Public Key: " + serverPubKey);
       System.out.println("");
       return Utils.verify(serverVerificationCode, signedServerVerificationCode, serverPubKey);
    }

    public String generateServerVerificationCode() {
        byte[] bytes = new byte[16];
        new SecureRandom().nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }

    public VotePackage prepareAnonymousVote(String vote, PublicKey serverPubKey, Authority authority) throws Exception {
        SecretKey aesKey = Utils.generateAESKey();
        String encryptedVote = Utils.encryptAES(vote, aesKey);
        String encryptedAESKey = Utils.encryptRSA(Base64.getEncoder().encodeToString(aesKey.getEncoded()), serverPubKey);

        String nonce = Utils.generateNonce();
        String token = Utils.hashSHA256(vote + nonce);
        String signedToken = authority.signToken(token);

        VotePackage pkg = new VotePackage(encryptedVote, encryptedAESKey, signedToken, token, nonce);        
        System.out.println("Step 2 >>> Alice sends vote package");
        System.out.println("");
        System.out.println("Encrypted Vote: " + pkg.encryptedVote);
        System.out.println("");
        System.out.println("Encrypted AES Key: " + pkg.encryptedAESKey);
        System.out.println("");
        System.out.println("Signed Token: " + pkg.signedToken);
        System.out.println("");
        System.out.println("Token: " + pkg.token);
        System.out.println("");
        System.out.println("Nonce: " + pkg.nonce);
        System.out.println("");

        
        return pkg;
    }
}
