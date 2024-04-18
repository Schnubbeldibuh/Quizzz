package de.dhbw.ase.play.games.multiplayer.quickquiz;

import de.dhbw.ase.play.games.multiplayer.CommunicationPrefixes;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerServer;

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
            String outgoingMsg = CommunicationPrefixes.ANSWER_EVALUATION.toString() + outcome;
            sendMessageToClient(outgoingMsg, username);

            if (outcome) {
                sendMessageToAllClients(CommunicationPrefixes.RIGHT_ANSWER + username);
                userList.clear();
            }

            removeUser(username);
            if (checkIfQuestionFinished()) {
                playQuestion();
            }
        }
    }

    @Override
    protected void sendStatsToAllClients() {

    }

    @Override
    protected void initializeRound() {
        System.out.println("hahahaha");
        //TODO
    }
}
