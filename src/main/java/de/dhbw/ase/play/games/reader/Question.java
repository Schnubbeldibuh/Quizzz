package de.dhbw.ase.play.games.reader;

import java.util.List;

public class Question {

    public record Answer(String answer, boolean isRight) {}
    private final List<Answer> answerList;
    private final String question;
    public Question(List<Answer> answerList, String question) {
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
