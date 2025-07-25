# 🗳️ Secure Voting System (Java)

This project implements a **secure voting system** in Java, demonstrating cryptographic techniques to ensure:

- 🕵️ **Anonymity** of voters  
- ✅ **Integrity** and **authenticity** of votes  
- 🔒 **Confidentiality** of ballot content

It uses **RSA** for asymmetric encryption/signature and **AES** for symmetric encryption.

---
## 🔐 Cryptographic Security Goals

### 1. **Confidentiality**
- The vote content is encrypted using **AES** (symmetric encryption).
- The AES key is then encrypted using the **server's RSA public key**.
- Only the server can decrypt the AES key using its **private key**.

✅ Ensures only the server can read the actual vote.

---

### 2. **Integrity**
- The vote is hashed using **SHA-256**.
- The hash is signed using the **voter's RSA private key**.
- The server verifies the signature using the **voter’s public key**.

✅ Guarantees the vote content wasn't tampered with.

---

### 3. **Authenticity**
- The signature on the vote hash ensures it came from the legitimate voter.
- Replay attacks are prevented if the system maintains a list of vote hashes or timestamps.

✅ Validates voter identity cryptographically.

---

### 4. **Anonymity**
- The vote is encrypted and not linked to personal identity (apart from the public key).
- If a separate token or anonymization layer is introduced, voter tracking can be further minimized.

⚠️ Currently, public key is included in `VotePackage`, so **pseudonymity** is provided. For full anonymity, blind signatures or a mixnet would be needed.

---

## ⚠️ Insecurity Test Cases

### 🔁 Replay Attack (Not Currently Prevented)
- Reusing a valid `VotePackage` to submit a vote again.

**Test**: Call `server.receiveVote(pkg)` multiple times.  
**Expected**: Server should detect duplicates (e.g., using hash/token/timestamp).

---

### ✏️ Vote Tampering
- Change `pkg.encryptedVote` or `pkg.hash` before sending to the server.

**Test**: Modify `pkg.encryptedVote = "fake"` before sending.  
**Expected**: Server fails signature verification.

---

### 🔓 Man-in-the-Middle (MITM)
- Intercepting and altering the AES key or vote package.

**Test**: Alter `pkg.encryptedAESKey` or signature.  
**Expected**: Server should detect mismatch in decryption or verification.

---

### 👤 Identity Leakage
- PublicKey is shared in the vote package.

**Implication**: While names are not used, repeated public keys can be correlated.

🔧 **Improvement**: Use one-time voting keys or blind signatures.

---

## ▶️ How to Run

1. **Compile**
```bash
javac *.java
java Main

