import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;

public class Utils {

    public static KeyPair generateRSAKeyPair() throws Exception {
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(2048);
        return gen.generateKeyPair();
    }

    public static SecretKey generateAESKey() throws Exception {
        KeyGenerator gen = KeyGenerator.getInstance("AES");
        gen.init(128);
        return gen.generateKey();
    }

    public static String encryptRSA(String plainText, PublicKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes()));
    }

    public static String decryptRSA(String encryptedText, PrivateKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] bytes = Base64.getDecoder().decode(encryptedText);
        return new String(cipher.doFinal(bytes));
    }

    public static String encryptAES(String plainText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes()));
    }

    public static String decryptAES(String cipherText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] bytes = Base64.getDecoder().decode(cipherText);
        return new String(cipher.doFinal(bytes));
    }

    public static String hashSHA256(String input) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return Base64.getEncoder().encodeToString(digest.digest(input.getBytes()));
    }

    public static String sign(String hash, PrivateKey key) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(key);
        signature.update(hash.getBytes());
        return Base64.getEncoder().encodeToString(signature.sign());
    }

    public static boolean verify(String hash, String sig, PublicKey key) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(key);
        signature.update(hash.getBytes());
        return signature.verify(Base64.getDecoder().decode(sig));
    }

    public static String generateNonce() {
        byte[] nonce = new byte[16];
        new SecureRandom().nextBytes(nonce);
        return Base64.getEncoder().encodeToString(nonce);
    }
}
