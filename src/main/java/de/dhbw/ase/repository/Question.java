package de.dhbw.ase.repository;

import java.util.Collections;
import java.util.List;

public record Question(List<Answer> answerList, String question) {

    public Question(List<Answer> answerList, String question) {
        this.answerList = Collections.unmodifiableList(answerList);
        this.question = question;
    }

    public record Answer(String answer, boolean isRight) {
    }
}
