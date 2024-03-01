package de.dhbw.ase.play.games.reader;

import java.util.List;

public class WWMQuestion {

    public record Answer(String answer, boolean isRight) {}
    private final List<Answer> answerList;
    private final String question;
    public WWMQuestion(List<Answer> answerList, String question) {
        this.answerList = answerList;
        this.question = question;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public String getQuestion() {
        return question;
    }
}
