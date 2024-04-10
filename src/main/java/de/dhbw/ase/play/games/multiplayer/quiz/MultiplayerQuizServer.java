package de.dhbw.ase.play.games.multiplayer.quiz;

import de.dhbw.ase.play.games.multiplayer.CommunicationPrefixes;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerServer;

public class MultiplayerQuizServer extends MultiplayerServer {

    protected MultiplayerQuizServer(int port) {
        super(port);
    }

    @Override
    protected void processClientMessage(String username, String msg) {

        if (CommunicationPrefixes.ANSWER.checkPrefix(msg)) {
            int answerIndex = Integer.parseInt(msg.substring(CommunicationPrefixes.ANSWER.getLength()));
            boolean outcome = currentAnswerList.get(answerIndex).isRight();

            String outgoingMsg = CommunicationPrefixes.ANSWER_EVALUATION.getString() + outcome;
            sendMessageToClient(outgoingMsg, username);

            removeUserAndPlayNextQuestionIfNeeded(username);
        }
    }
}
