package de.dhbw.ase.play.games.multiplayer.quickquiz;

import de.dhbw.ase.play.games.multiplayer.CommunicationPrefixes;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerServer;

import java.util.HashSet;

public class MultiplayerQuickServer extends MultiplayerServer {

    protected MultiplayerQuickServer(int port) {
        super(port);
    }

    @Override
    protected synchronized void processClientMessage(String username, String msg) {

        if (CommunicationPrefixes.ANSWER.checkPrefix(msg)) {
            String answer = msg.substring(CommunicationPrefixes.ANSWER.getLength());
            String[] answerArray = answer.split(";");
            int answerIndex = Integer.parseInt(answerArray[0]);
            int answerId = Integer.parseInt(answerArray[1]);

            if (questionIndex > answerId) {
                return;
            }

            boolean outcome = currentAnswerList.get(answerIndex).isRight();
            String outgoingMsg = CommunicationPrefixes.ANSWER_EVALUATION.getString() + outcome;
            sendMessageToClient(outgoingMsg, username);

            if (outcome) {
                sendMessageToAllClients(CommunicationPrefixes.RIGHT_ANSWER.getString() + username);
                userList = new HashSet<>();
            }

            checkIfRoundClosed(username);
        }
    }
}
