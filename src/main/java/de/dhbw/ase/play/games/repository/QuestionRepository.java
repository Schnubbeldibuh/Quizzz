package de.dhbw.ase.play.games.repository;

import java.util.List;

public interface QuestionRepository {
    List<Question> getQuestionList(int amount) throws CouldNotAccessFileException;
}
