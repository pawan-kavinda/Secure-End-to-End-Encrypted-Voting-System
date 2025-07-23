public class Main {
    public static void main(String[] args) throws Exception {
        
        Authority authority = new Authority();
        VotingServer server = new VotingServer(authority);

        // Alice
        Voter alice = new Voter();

        //Alice verifies server
        String serverVerificationCode = alice.generateServerVerificationCode();
        String signedChallenge = server.signServerVerificationCode(serverVerificationCode);
       // String fakeSignedChallenge = "Ify/inUYXs/worJqaUjfevojxLMWZ52M7GMADlnsOspmx6s3cByH/fiMaTffTzAQDKFSRQEcCvFukXLttVePJIaFhDztkeXPBo87MGDQO3BRiVQvknt9nXo2oPL8cc0r6uUbiKZ8gWkmfMa3WWICmUEEz3IHlGvCZclwEkU+ONaWTtYpCilLnMWBKZD+2VdVK4DH4rkbIzBxSdMBwwl31Joxo0utYe6+LA2wArlvHDdf9Q9SinPsWMYc2GtXB7Sy07YkJD8KXAp+5uF4wmAjFOND+ePTX3rla/nKr+46asl8XPD1TXYKNCDXLn3hF9up1ifzxmhvDmt7rxMS8zmFww==";

        boolean isServerAuthenticated = alice.verifyServerResponse(serverVerificationCode, signedChallenge, server.getPublicKey());

        if (!isServerAuthenticated) {
            System.err.println("❌ Server verification failed. Aborting vote...");
            
            return;
        }

        System.out.println("✅ Server verified. Proceeding to vote...\n");

        
        //Alice prepares anonymous encrypted vote package
        String vote = "Alice votes: Candidate X";
        VotePackage pkg = alice.prepareAnonymousVote(vote, server.getPublicKey(), authority);


        //Send vote to server
        server.receiveAnonymousVote(pkg);

        // === Step 5: Attempt replay attack ===
        // System.out.println("\n Sending the same vote package again");
        // server.receiveAnonymousVote(pkg); // Rejected
        //String fakeSignature = "iSM+vu88k5QjGqHmZCk65nJZKZO3hdwUxUhktuEFDStNfrDim6bm4FnQ12zw2+4gBBnioZbzVo2PVK+gRTT64sEG7BprjtN6RqwzrtPROwtqOjvldGyOND55pL+tyN3YrT6uL6ZyAl7CGZe4P1OOIMAowSAkRvcE0B+JzJnRQ1fioqFHNbQTsmlo4szplDj0AjbGV3C7aTRY0xhnc1Dp67UwSe46FU1LK/tokaXkkKeEYBznmnaV2v28sIcr4zo7KdwIG6YImjbByr6CjigHQfocSVDo2lkKZC63whofwJvI+hMnoqXSLpai/xd9nKE29ueU25lx+E1hVobA5udRTQ==";
       // === Step 6: Simulate tampered signature (MIM attack) ===
        //System.out.println("\n[MIM Attack Attempt]");
        // VotePackage tamperedPkg = new VotePackage(
        //         pkg.encryptedVote,
        //         pkg.encryptedAESKey,
        //         fakeSignature,
        //         pkg.token,
        //         pkg.nonce
        // );
        // server.receiveAnonymousVote(tamperedPkg); Rejected
    }
}
