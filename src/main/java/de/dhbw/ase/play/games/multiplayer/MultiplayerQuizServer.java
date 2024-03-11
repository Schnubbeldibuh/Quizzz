package de.dhbw.ase.play.games.multiplayer;

import de.dhbw.ase.play.games.multiplayer.core.MultiplayerServer;
import de.dhbw.ase.play.games.reader.MReader;
import de.dhbw.ase.play.games.reader.Question;
import de.dhbw.ase.play.games.reader.Reader;

import java.util.Collections;
import java.util.List;

public class MultiplayerQuizServer extends MultiplayerServer {

    private List<Question> questionList;
    private int questionIndex = 0;
    private List<Question.Answer> currentAnswerList;

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
            //TODO
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

    private void playQuestion() {
        Question question = questionList.get(questionIndex++);
        currentAnswerList = question.getAnswerList();
        Collections.shuffle(currentAnswerList);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Next question:");
        stringBuilder.append(question.getQuestion());
        currentAnswerList.forEach(a -> {
            stringBuilder.append(";");
            stringBuilder.append(a);
        });
        sendMessageToAllClients(stringBuilder.toString());
    }
}
