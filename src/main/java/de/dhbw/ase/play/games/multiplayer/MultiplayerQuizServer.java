package de.dhbw.ase.play.games.multiplayer;

import de.dhbw.ase.play.games.multiplayer.core.MultiplayerServer;
import de.dhbw.ase.play.games.reader.MReader;
import de.dhbw.ase.play.games.reader.Question;
import de.dhbw.ase.play.games.reader.Reader;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class MultiplayerQuizServer extends MultiplayerServer {

    private List<Question> questionList;
    private int questionIndex = 0;
    private List<Question.Answer> currentAnswerList;
    private Set<String > userList;
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
            synchronized(userList) {
                userList.remove(username);
                checkIfRoundClosed();
            }
        }
    }

    @Override
    protected void startPlaying() {
        Reader mReader = new MReader();
        questionList = mReader.getQuestionList();
        sendMessageToAllClients("start game");
        Collections.shuffle(questionList);
        playQuestion();
    }

    private void checkIfRoundClosed() {
        if (userList.isEmpty()) {
            playQuestion();
        }
    }

    private void playQuestion() {
        if(questionList.size() == questionIndex) {
            sendMessageToAllClients("Round is finished!");
            //TODO Punktestand an alle Clients schicken
            return;
        }
        Question question = questionList.get(questionIndex++);
        currentAnswerList = question.getAnswerList();
        Collections.shuffle(currentAnswerList);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Next question:");
        stringBuilder.append(question.getQuestion());
        currentAnswerList.forEach(a -> {
                    stringBuilder.append(";");
                    stringBuilder.append(a.answer());
        });
        sendMessageToAllClients(stringBuilder.toString());
        userList = getUsernames();
    }
}
