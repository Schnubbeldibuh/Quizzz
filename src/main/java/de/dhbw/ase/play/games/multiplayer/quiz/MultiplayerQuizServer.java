package de.dhbw.ase.play.games.multiplayer.quiz;

import de.dhbw.ase.play.games.multiplayer.CommunicationPrefixes;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerServer;

import java.util.HashMap;
import java.util.Map;

public class MultiplayerQuizServer extends MultiplayerServer {

    protected MultiplayerQuizServer(int port) {
        super(port);
    }
    private Map<String, Player> pointsMap = new HashMap<>();
    private Map<String, Boolean> rightAnswerMap = new HashMap<>();

    @Override
    protected void processClientMessage(String username, String msg) {

        if (CommunicationPrefixes.ANSWER.checkPrefix(msg)) {
            int answerIndex = Integer.parseInt(msg.substring(CommunicationPrefixes.ANSWER.getLength()));
            boolean outcome = currentAnswerList.get(answerIndex).isRight();

            String outgoingMsg = CommunicationPrefixes.ANSWER_EVALUATION.getString() + outcome;
            sendMessageToClient(outgoingMsg, username);

            if (outcome) {
                pointsMap.get(username).increaseRightAnswer();
                rightAnswerMap.put(username, true);
            } else {
                pointsMap.get(username).increaseWrongAnswer();
                rightAnswerMap.put(username, false);
            }

            removeUser(username);
            boolean questionFinished = checkIfQuestionFinished();
            if (questionFinished) {
                calculatePoints();
                rightAnswerMap = new HashMap<>();
                playQuestion();
            }
        }
    }

    private void calculatePoints() {
        long countRightAnswers = rightAnswerMap.values()
                .stream()
                .filter(a -> a)
                .count();

        if (countRightAnswers == 0) {
            return;
        }

        if (countRightAnswers > 1) {
            rightAnswerMap.forEach((k, v) -> {
                if (v) {
                    pointsMap.get(k).increasePoints(1);
                }
            });
        } else {
            String username = rightAnswerMap.entrySet()
                    .stream()
                    .filter(Map.Entry::getValue)
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .get();
            pointsMap.get(username).increasePoints(5);
        }
    }

    @Override
    protected void initializeRound() {
        pointsMap = new HashMap<>();
        getUsernames().forEach(a -> pointsMap.put(a, new Player(a)));
    }
}