package de.dhbw.ase.play.games.singelplayer;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.repository.QuestionRepositoryMock;
import de.dhbw.ase.repository.StatsRepositoryMock;
import de.dhbw.ase.repository.question.Question;
import de.dhbw.ase.stats.persistance.PlayerStatsSPObject;
import de.dhbw.ase.stats.persistance.PlayerStatsSurvivalObject;
import de.dhbw.ase.user.in.UserInMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SurvivalQuizTest {
    @Test
    void survivalGameTestBlankStats() {
        UserInMock userInMock = new UserInMock();
        userInMock.inputs.offer("test");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("b");
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

        SurvivalQuiz survivalQuizTest = new SurvivalQuiz(userInMock, questionRepositoryMock, statsRepositoryMock);

        SelectedMenu.MenuSelection start = survivalQuizTest.start();
        Assertions.assertEquals(SelectedMenu.MenuSelection.BACK, start);

        List<PlayerStatsSurvivalObject> savedStats = statsRepositoryMock.savedStats;
        Assertions.assertEquals(1, savedStats.size());

        PlayerStatsSurvivalObject playerStatsSurvivalObject = savedStats.get(0);
        Assertions.assertEquals(4, playerStatsSurvivalObject.getRightAnswers());
        Assertions.assertEquals(4, playerStatsSurvivalObject.getWrongAnswers());
        Assertions.assertEquals(8, playerStatsSurvivalObject.getAverageDeathLevel());
        Assertions.assertEquals(1, playerStatsSurvivalObject.getTotalPlayedGames());
        Assertions.assertEquals(0, playerStatsSurvivalObject.getTotalSurvivedGames());
    }

    @Test
    void survivalGameTestExistingStats() {
        UserInMock userInMock = new UserInMock();
        userInMock.inputs.offer("test");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("b");
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
        statsRepositoryMock.stats = List.of(new PlayerStatsSurvivalObject("test", 2, 2, 6, 1, 1));

        SurvivalQuiz survivalQuizTest = new SurvivalQuiz(userInMock, questionRepositoryMock, statsRepositoryMock);

        SelectedMenu.MenuSelection start = survivalQuizTest.start();
        Assertions.assertEquals(SelectedMenu.MenuSelection.BACK, start);

        List<PlayerStatsSurvivalObject> savedStats = statsRepositoryMock.savedStats;
        Assertions.assertEquals(1, savedStats.size());

        PlayerStatsSurvivalObject playerStatsSurvivalObject = savedStats.get(0);
        Assertions.assertEquals(6, playerStatsSurvivalObject.getRightAnswers());
        Assertions.assertEquals(6, playerStatsSurvivalObject.getWrongAnswers());
        Assertions.assertEquals(7, playerStatsSurvivalObject.getAverageDeathLevel());
        Assertions.assertEquals(2, playerStatsSurvivalObject.getTotalPlayedGames());
        Assertions.assertEquals(1, playerStatsSurvivalObject.getTotalSurvivedGames());
    }
}