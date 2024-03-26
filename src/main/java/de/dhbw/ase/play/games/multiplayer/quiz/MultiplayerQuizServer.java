package de.dhbw.ase.play.games.multiplayer.quiz;

import de.dhbw.ase.play.games.multiplayer.core.MultiplayerServer;

public class MultiplayerQuizServer extends MultiplayerServer {

    protected MultiplayerQuizServer(int port) {
        super(port);
    }

    @Override
    protected void processClientMessage(String username, String msg) {
        if (msg.startsWith("Answer:")) {
            int answerIndex = Integer.parseInt(msg.substring("Answer:".length()));
            boolean outcome = currentAnswerList.get(answerIndex).isRight();
            String outgoingMsg = "Answer evaluation:" + outcome;
            sendMessageToClient(outgoingMsg, username);
            checkIfRoundClosed(username);
        }
    }
}
