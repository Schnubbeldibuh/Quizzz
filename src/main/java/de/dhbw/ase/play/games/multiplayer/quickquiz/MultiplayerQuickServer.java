package de.dhbw.ase.play.games.multiplayer.quickquiz;

import de.dhbw.ase.play.games.multiplayer.CommunicationPrefixes;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerServer;
import de.dhbw.ase.play.games.repository.QuestionRepository;

import java.util.HashMap;
import java.util.Map;

public class MultiplayerQuickServer extends MultiplayerServer {

    private Map<String, PlayerQuick> pointsMap = new HashMap<>();
    private volatile boolean firstAnswer = true;

    public MultiplayerQuickServer(int port, QuestionRepository questionRepository) {
        super(port, questionRepository);
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
                pointsMap.get(username).increaseRightAnswer();
                if (firstAnswer) {
                    pointsMap.get(username).increaseFastesAnswer();
                }
                userList.clear();
            } else {
                firstAnswer = false;
                pointsMap.get(username).increaseWrongAnswer();
                removeUser(username);
            }

            if (checkIfQuestionFinished()) {
                firstAnswer = true;
                playQuestion();
            }
        }
    }

    @Override
    protected void sendStatsToAllClients() {
        pointsMap.values()
                .forEach(p -> sendMessageToAllClients(CommunicationPrefixes.STATS_TRANSFER + p.toString()));

        sendMessageToAllClients(CommunicationPrefixes.STATS_TRANSFER_FINISHED.toString());
    }

    @Override
    protected void initializeRound() {
        pointsMap = new HashMap<>();
        getUsernames().forEach(a -> pointsMap.put(a, new PlayerQuick(a)));
    }
}
