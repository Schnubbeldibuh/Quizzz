package de.dhbw.ase.play.games.multiplayer.quickquiz;

import de.dhbw.ase.play.games.multiplayer.core.MultiplayerServer;

import java.util.HashSet;

public class MultiplayerQuickServer extends MultiplayerServer {

    protected MultiplayerQuickServer(int port) {
        super(port);
    }

    @Override
    protected synchronized void processClientMessage(String username, String msg) {
        if (msg.startsWith("Answer:")) {
            String answer = msg.substring("Answer:".length());
            String[] answerArray = answer.split(";");
            int answerIndex = Integer.parseInt(answerArray[0]);
            int answerId = Integer.parseInt(answerArray[1]);
            if (questionIndex > answerId) {
                return;
            }
            boolean outcome = currentAnswerList.get(answerIndex).isRight();
            String outgoingMsg = "Answer evaluation:" + outcome;
            sendMessageToClient(outgoingMsg, username);

            if (outcome) {
                sendMessageToAllClients("Right answer:" + username);
                userList = new HashSet<>();
            }

            checkIfRoundClosed(username);
        }
    }
}
