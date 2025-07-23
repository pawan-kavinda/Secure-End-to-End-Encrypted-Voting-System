public class VotePackage {
    public String encryptedVote;
    public String encryptedAESKey;
    public String signedToken;
    public String token;
    public String nonce;

    public VotePackage(String encryptedVote, String encryptedAESKey, String signedToken, String token, String nonce) {
        this.encryptedVote = encryptedVote;
        this.encryptedAESKey = encryptedAESKey;
        this.signedToken = signedToken;
        this.token = token;
        this.nonce = nonce;
    }
}
