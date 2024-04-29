package de.dhbw.ase.repository;

import de.dhbw.ase.repository.question.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class QuestionRepositoryMock implements QuestionRepository {

    public List<Question> stats;
    public List<Question> savedStats;



    @Override
    public List<Question> getQuestionList(int amount) throws CouldNotAccessFileException {
        return null;
    }

    @Override
    public List<Question> readCompleteFile() throws CouldNotAccessFileException {
        return stats;
    }

    @Override
    public void writeBackToFile(List<Question> lineList) throws CouldNotAccessFileException {
        savedStats = lineList;
    }

    @Override
    public void writeBackToFile(Set<Question> lineList) throws CouldNotAccessFileException {
        savedStats = new ArrayList<>(lineList);
    }
}