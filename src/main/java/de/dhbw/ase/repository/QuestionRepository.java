package de.dhbw.ase.repository;

import de.dhbw.ase.repository.question.Question;

import java.util.List;
import java.util.Set;

public interface QuestionRepository {
    List<Question> getQuestionList(int amount) throws CouldNotAccessFileException;

    List<Question> readCompleteFile() throws CouldNotAccessFileException;

    void writeBackToFile(List<Question> lineList) throws CouldNotAccessFileException;
    void writeBackToFile(Set<Question> lineList) throws CouldNotAccessFileException;
}
