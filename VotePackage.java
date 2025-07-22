import java.security.PublicKey;

public class VotePackage {
    public String encryptedVote;
    public String encryptedAESKey;
    public String signature;
    public PublicKey voterPubKey;
    public String hash;

    public VotePackage(String encryptedVote, String encryptedAESKey, String signature, PublicKey voterPubKey, String hash) {
        this.encryptedVote = encryptedVote;
        this.encryptedAESKey = encryptedAESKey;
        this.signature = signature;
        this.voterPubKey = voterPubKey;
        this.hash = hash;
    }
}
