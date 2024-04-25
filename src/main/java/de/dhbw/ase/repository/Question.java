package de.dhbw.ase.repository;

import java.util.List;

public record Question(List<Answer> answerList, String question) {

    public record Answer(String answer, boolean isRight) {
    }
}
