package de.dhbw.ase.play.games.singelplayer;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.repository.QuestionRepository;
import de.dhbw.ase.repository.QuestionRepositoryMock;
import de.dhbw.ase.repository.StatsRepository;
import de.dhbw.ase.repository.StatsRepositoryMock;
import de.dhbw.ase.repository.question.Question;
import de.dhbw.ase.stats.persistance.PlayerStatsSPObject;
import de.dhbw.ase.user.in.UserInMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class SPQuizTest {
    @Test
    void spGameTestBlankStats() {
        UserInMock userInMock = new UserInMock();
        userInMock.inputs.offer("test");

        ArrayList<Question> questions = new ArrayList<>();
        while (questions.size() < 50) {
            questions.add(Question.Builder.createFakeQuestion());
        }

        QuestionRepositoryMock questionRepositoryMock = new QuestionRepositoryMock();
        questionRepositoryMock.questions = questions;

        StatsRepositoryMock statsRepositoryMock = new StatsRepositoryMock();

        SPQuizMock spQuizTest = new SPQuizMock(userInMock, Categories.FOOD, questionRepositoryMock, statsRepositoryMock);

        SelectedMenu.MenuSelection start = spQuizTest.start();
        Assertions.assertEquals(SelectedMenu.MenuSelection.BACK, start);

        List<PlayerStatsSPObject> savedStats = statsRepositoryMock.savedStats;
        Assertions.assertEquals(1, savedStats.size());

        PlayerStatsSPObject playerStatsSPObject = savedStats.get(0);
        Assertions.assertEquals(4, playerStatsSPObject.getRightAnswers());
        Assertions.assertEquals(4, playerStatsSPObject.getWrongAnswers());
    }

    @Test
    void spGameTestExistingStats() {
        UserInMock userInMock = new UserInMock();
        userInMock.inputs.offer("test");

        ArrayList<Question> questions = new ArrayList<>();
        while (questions.size() < 50) {
            questions.add(Question.Builder.createFakeQuestion());
        }

        QuestionRepositoryMock questionRepositoryMock = new QuestionRepositoryMock();
        questionRepositoryMock.questions = questions;

        StatsRepositoryMock statsRepositoryMock = new StatsRepositoryMock();
        statsRepositoryMock.stats = List.of(new PlayerStatsSPObject("test", 0, 0, 0));

        SPQuizMock spQuizTest = new SPQuizMock(userInMock, Categories.FOOD, questionRepositoryMock, statsRepositoryMock);

        SelectedMenu.MenuSelection start = spQuizTest.start();
        Assertions.assertEquals(SelectedMenu.MenuSelection.BACK, start);

        List<PlayerStatsSPObject> savedStats = statsRepositoryMock.savedStats;
        Assertions.assertEquals(1, savedStats.size());

        PlayerStatsSPObject playerStatsSPObject = savedStats.get(0);
        Assertions.assertEquals(4, playerStatsSPObject.getRightAnswers());
        Assertions.assertEquals(4, playerStatsSPObject.getWrongAnswers());
    }

    @Test
    void spGameTestExit() {
        UserInMock userInMock = new UserInMock();
        userInMock.inputs.offer("test");
        userInMock.inputs.offer("s");
        userInMock.inputs.offer("exit");

        ArrayList<Question> questions = new ArrayList<>();
        while (questions.size() < 50) {
            questions.add(Question.Builder.createFakeQuestion());
        }

        QuestionRepositoryMock questionRepositoryMock = new QuestionRepositoryMock();
        questionRepositoryMock.questions = questions;

        StatsRepositoryMock statsRepositoryMock = new StatsRepositoryMock();

        SPQuizMock spQuizTest = new SPQuizMock(userInMock, Categories.FOOD, questionRepositoryMock, statsRepositoryMock);

        SelectedMenu.MenuSelection start = spQuizTest.start();
        Assertions.assertEquals(SelectedMenu.MenuSelection.EXIT, start);

        List<PlayerStatsSPObject> savedStats = statsRepositoryMock.savedStats;
        Assertions.assertEquals(0, savedStats.size());
    }

    static class SPQuizMock extends SPQuiz {
        UserInMock sc;
        int answeredQuestions;

        public SPQuizMock(UserInMock sc, Categories category, QuestionRepository questionRepository, StatsRepository statsRepository) {
            super(sc, category, questionRepository, statsRepository);
            this.sc = sc;
        }

        @Override
        protected List<Question.Answer> showQuestion(Question question) {
            List<Question.Answer> answers = super.showQuestion(question);
            Question.Answer rightAnswer = answers
                    .stream()
                    .filter(Question.Answer::isRight)
                    .findFirst()
                    .get();

            int rightAnswerIndex = answers.indexOf(rightAnswer);
            sc.inputs.offer(answeredQuestions < 4 ? intToAnswer(rightAnswerIndex) :
                    rightAnswerIndex < 3 ? intToAnswer(++rightAnswerIndex) : intToAnswer(--rightAnswerIndex));
            answeredQuestions++;
            if (answeredQuestions == 8)
                sc.inputs.offer("2");

            return answers;
        }

        String intToAnswer(int i) {
            return switch (i) {
                case 0 -> "a";
                case 1 -> "b";
                case 2 -> "c";
                case 3 -> "d";
                default -> "";
            };
        }
    }
}
