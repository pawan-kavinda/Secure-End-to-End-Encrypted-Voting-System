public class Main {
    public static void main(String[] args) throws Exception {
        Voter alice = new Voter();
        VotingServer server = new VotingServer();

        String vote = "Bob for President";
        VotePackage pkg = alice.prepareVote(vote, server.keyPair.getPublic());

        server.receiveVote(pkg);
    }
}
