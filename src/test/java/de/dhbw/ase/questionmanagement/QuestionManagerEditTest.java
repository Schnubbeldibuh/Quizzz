package de.dhbw.ase.questionmanagement;

import de.dhbw.ase.repository.QuestionRepositoryMock;
import de.dhbw.ase.repository.question.Question;
import de.dhbw.ase.user.in.UserInMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class QuestionManagerEditTest {

    @Test
    void start() {

        QuestionRepositoryMock repository = new QuestionRepositoryMock();
        repository.stats = List.of(
                Question.Builder.createFakeQuestion()
        );

        String newQuestion = "Q";
        List<String> newAnswers = List.of("A", "B", "C", "D");
        List<String> userInput = new ArrayList<>(newAnswers);
        userInput.add(0, newQuestion);
        UserInMock sc = new UserInMock(userInput);

        QuestionManagerDeleteTest.SelectedLinesMock selectedLine = new QuestionManagerDeleteTest.SelectedLinesMock();
        QuestionManagerEdit manager = new QuestionManagerEdit(sc, repository, selectedLine);

        selectedLine.setSelectedLine(0);

        manager.start();

        int size = repository.savedStats.size();
        Assertions.assertEquals(1, size);

        Question question = repository.savedStats.get(0);
        question.getAnswerList().forEach(System.out::println);

        Assertions.assertEquals(newQuestion, question.getQuestion());

        Iterator<String> answerIterator = newAnswers.iterator();
        Iterator<Question.Answer> savedAnswerIterator = question.getAnswerList().iterator();
        while (answerIterator.hasNext()) {
            Assertions.assertEquals(answerIterator.next(), savedAnswerIterator.next().answer());
        }
        Assertions.assertFalse(savedAnswerIterator.hasNext());

        System.out.println(question.getCompleteLine());
    }
}