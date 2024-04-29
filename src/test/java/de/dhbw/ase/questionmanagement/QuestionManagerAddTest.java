package de.dhbw.ase.questionmanagement;

import de.dhbw.ase.repository.QuestionRepositoryMock;
import de.dhbw.ase.repository.question.Question;
import de.dhbw.ase.user.in.UserInMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class QuestionManagerAddTest {

    @Test
    void start() {

        QuestionRepositoryMock repository = new QuestionRepositoryMock();

        String newQuestion = "Q";
        List<String> newAnswers = List.of("A", "B", "C", "D");
        List<String> userInput = new ArrayList<>(newAnswers);
        userInput.add(0, newQuestion);
        UserInMock sc = new UserInMock(userInput);

        QuestionManagerAdd manager = new QuestionManagerAdd(sc, repository);

        manager.start();

        int size = repository.savedStats.size();
        Assertions.assertEquals(1, size);

        Question question = repository.savedStats.get(0);
        Assertions.assertEquals(newQuestion, question.getQuestion());

        Iterator<String> answerIterator = newAnswers.iterator();
        Iterator<Question.Answer> savedAnswerIterator = question.getAnswerList().iterator();
        while (answerIterator.hasNext()) {
            Assertions.assertEquals(answerIterator.next(), savedAnswerIterator.next().answer());
        }
        Assertions.assertFalse(savedAnswerIterator.hasNext());
    }

    @Test
    void start2() {

        QuestionRepositoryMock repository = new QuestionRepositoryMock();

        Question q = Question.Builder.createFakeQuestion();
        List<String> userInput = new ArrayList<>();
        userInput.add(q.getQuestion());
        q.getAnswerList().stream().map(Question.Answer::answer).forEach(userInput::add);
        UserInMock sc = new UserInMock(userInput);
        repository.stats.add(q);

        QuestionManagerAdd manager = new QuestionManagerAdd(sc, repository);

        manager.start();

        Assertions.assertNull(repository.savedStats);
    }
}