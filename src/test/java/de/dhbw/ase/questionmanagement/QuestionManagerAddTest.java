package de.dhbw.ase.questionmanagement;

import de.dhbw.ase.repository.QuestionRepositoryMock;
import de.dhbw.ase.repository.question.Question;
import de.dhbw.ase.user.in.UserInMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

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

        int size = repository.savedQuestions.size();
        Assertions.assertEquals(1, size);

        Question question = repository.savedQuestions.get(0);
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
        repository.questions.add(q);

        QuestionManagerAdd manager = new QuestionManagerAdd(sc, repository);

        manager.start();

        Assertions.assertNull(repository.savedQuestions);
    }

    @Test
    void keepOrder() {

        QuestionRepositoryMock repository = new QuestionRepositoryMock();
        repository.questions = List.of(Question.Builder.createFakeQuestion(),
                Question.Builder.create()
                        .withQuestion("Q2")
                        .withRightAnswer("a1")
                        .withWrongAnswer("a2")
                        .withWrongAnswer("a3")
                        .withWrongAnswer("a4")
                        .build());

        Question q = Question.Builder.createFakeQuestion();
        List<String> userInput = new ArrayList<>();
        userInput.add(q.getQuestion());
        q.getAnswerList().stream().map(Question.Answer::answer).forEach(userInput::add);

        UserInMock sc = new UserInMock();
        sc.inputs.offer("Q3");
        sc.inputs.offer("An1");
        sc.inputs.offer("An2");
        sc.inputs.offer("An3");
        sc.inputs.offer("An4");

        QuestionManagerAdd manager = new QuestionManagerAdd(sc, repository);

        manager.start();

        Assertions.assertEquals(Question.Builder.createFakeQuestion().getQuestion(), repository.savedQuestions.get(0).getQuestion());
        Assertions.assertEquals("Q2", repository.savedQuestions.get(1).getQuestion());
        Assertions.assertEquals("Q3", repository.savedQuestions.get(2).getQuestion());
    }

    @Test
    void dontAddDuplicate() {

        QuestionRepositoryMock repository = new QuestionRepositoryMock();
        repository.questions = new ArrayList<>(List.of(Question.Builder.createFakeQuestion()));

        Question q = Question.Builder.createFakeQuestion();
        List<String> userInput = new ArrayList<>();
        userInput.add(q.getQuestion());
        q.getAnswerList().stream().map(Question.Answer::answer).forEach(userInput::add);
        UserInMock sc = new UserInMock(userInput);

        QuestionManagerAdd manager = new QuestionManagerAdd(sc, repository);

        manager.start();

        Assertions.assertNull(repository.savedQuestions);
    }
}