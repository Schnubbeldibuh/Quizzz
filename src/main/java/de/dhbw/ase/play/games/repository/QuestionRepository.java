package de.dhbw.ase.play.games.repository;

import de.dhbw.ase.questionmanagement.QuestionObject;

import java.util.List;

public interface QuestionRepository {
    List<Question> getQuestionList(int amount) throws CouldNotAccessFileException;

    List<Question> readCompleteFile() throws CouldNotAccessFileException;
    List<QuestionObject> readCompleteFileAsQuestionObject() throws CouldNotAccessFileException;

    List<String> readCompleteFileAsString() throws CouldNotAccessFileException;

    void writeBackToFile(List<QuestionObject> lineList) throws CouldNotAccessFileException;
}
