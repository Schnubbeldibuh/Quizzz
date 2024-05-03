package de.dhbw.ase.play.games.singelplayer;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.repository.QuestionRepositoryMock;
import de.dhbw.ase.repository.StatsRepositoryMock;
import de.dhbw.ase.repository.question.Question;
import de.dhbw.ase.stats.persistance.PlayerStatsFQObject;
import de.dhbw.ase.stats.persistance.PlayerStatsSPObject;
import de.dhbw.ase.user.in.UserInMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FindQuestionsQuizTest {
    @Test
    void fqGameTestBlankStats() {
        UserInMock userInMock = new UserInMock();
        userInMock.inputs.offer("test");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("b");
        userInMock.inputs.offer("b");
        userInMock.inputs.offer("c");
        userInMock.inputs.offer("d");
        userInMock.inputs.offer("d");
        userInMock.inputs.offer("b");
        userInMock.inputs.offer("c");
        userInMock.inputs.offer("d");
        userInMock.inputs.offer("2");

        ArrayList<Question> questions = new ArrayList<>();
        while (questions.size() < 50) {
            questions.add(Question.Builder.createFakeQuestion());
        }

        QuestionRepositoryMock questionRepositoryMock = new QuestionRepositoryMock();
        questionRepositoryMock.questions = questions;

        StatsRepositoryMock statsRepositoryMock = new StatsRepositoryMock();

        FindQuestionsQuiz fqQuizTest = new FindQuestionsQuiz(userInMock, questionRepositoryMock, statsRepositoryMock);

        SelectedMenu.MenuSelection start = fqQuizTest.start();
        Assertions.assertEquals(SelectedMenu.MenuSelection.BACK, start);

        List<PlayerStatsFQObject> savedStats = statsRepositoryMock.savedStats;
        Assertions.assertEquals(1, savedStats.size());

        PlayerStatsFQObject playerStatsFQObject = savedStats.get(0);
        Assertions.assertEquals(7, playerStatsFQObject.getRightAnswers());
        Assertions.assertEquals(8, playerStatsFQObject.getWrongAnswers());
    }

    @Test
    void fqGameTestExistingStats() {
        UserInMock userInMock = new UserInMock();
        userInMock.inputs.offer("test");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("b");
        userInMock.inputs.offer("b");
        userInMock.inputs.offer("c");
        userInMock.inputs.offer("d");
        userInMock.inputs.offer("d");
        userInMock.inputs.offer("b");
        userInMock.inputs.offer("c");
        userInMock.inputs.offer("d");
        userInMock.inputs.offer("2");

        ArrayList<Question> questions = new ArrayList<>();
        while (questions.size() < 50) {
            questions.add(Question.Builder.createFakeQuestion());
        }

        QuestionRepositoryMock questionRepositoryMock = new QuestionRepositoryMock();
        questionRepositoryMock.questions = questions;

        StatsRepositoryMock statsRepositoryMock = new StatsRepositoryMock();
        statsRepositoryMock.stats = List.of(new PlayerStatsFQObject("test", 1, 1));

        FindQuestionsQuiz fqQuizTest = new FindQuestionsQuiz(userInMock, questionRepositoryMock, statsRepositoryMock);

        SelectedMenu.MenuSelection start = fqQuizTest.start();
        Assertions.assertEquals(SelectedMenu.MenuSelection.BACK, start);

        List<PlayerStatsFQObject> savedStats = statsRepositoryMock.savedStats;
        Assertions.assertEquals(1, savedStats.size());

        PlayerStatsFQObject playerStatsFQObject = savedStats.get(0);
        Assertions.assertEquals(8, playerStatsFQObject.getRightAnswers());
        Assertions.assertEquals(9, playerStatsFQObject.getWrongAnswers());
    }
}