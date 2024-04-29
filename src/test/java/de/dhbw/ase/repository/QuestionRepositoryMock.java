package de.dhbw.ase.repository;

import de.dhbw.ase.repository.question.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class QuestionRepositoryMock implements QuestionRepository {

    public List<Question> questions = new ArrayList<>();
    public List<Question> savedQuestions;



    @Override
    public List<Question> getQuestionList(int amount) throws CouldNotAccessFileException {
        return questions.stream().limit(amount).toList();
    }

    @Override
    public List<Question> readCompleteFile() throws CouldNotAccessFileException {
        return questions;
    }

    @Override
    public void writeBackToFile(List<Question> lineList) throws CouldNotAccessFileException {
        savedQuestions = lineList;
    }

    @Override
    public void writeBackToFile(Set<Question> lineList) throws CouldNotAccessFileException {
        savedQuestions = new ArrayList<>(lineList);
    }
}