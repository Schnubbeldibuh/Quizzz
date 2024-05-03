package de.dhbw.ase.play.games.singelplayer.wwm;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.play.games.singelplayer.Categories;
import de.dhbw.ase.play.games.singelplayer.SPQuiz;
import de.dhbw.ase.repository.QuestionRepositoryMock;
import de.dhbw.ase.repository.StatsRepositoryMock;
import de.dhbw.ase.repository.question.Question;
import de.dhbw.ase.stats.persistance.PlayerStatsSPObject;
import de.dhbw.ase.stats.persistance.PlayerStatsWWMObject;
import de.dhbw.ase.user.in.UserInMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WerWirdMillionaerTest {
    @Test
    void wwmGameTestBlankStats() {
        UserInMock userInMock = new UserInMock();
        userInMock.inputs.offer("test");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("1");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("1");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("1");
        userInMock.inputs.offer("c");
        userInMock.inputs.offer("2");

        ArrayList<Question> questions = new ArrayList<>();
        while (questions.size() < 50) {
            questions.add(Question.Builder.createFakeQuestion());
        }

        QuestionRepositoryMock questionRepositoryMock = new QuestionRepositoryMock();
        questionRepositoryMock.questions = questions;

        StatsRepositoryMock statsRepositoryMock = new StatsRepositoryMock();

        WerWirdMillionaer wwmQuizTest = new WerWirdMillionaer(userInMock,
                statsRepositoryMock,
                questionRepositoryMock,
                questionRepositoryMock,
                questionRepositoryMock,
                questionRepositoryMock,
                questionRepositoryMock);

        SelectedMenu.MenuSelection start = wwmQuizTest.start();
        Assertions.assertEquals(SelectedMenu.MenuSelection.BACK, start);

        List<PlayerStatsWWMObject> savedStats = statsRepositoryMock.savedStats;
        Assertions.assertEquals(1, savedStats.size());

        PlayerStatsWWMObject playerStatsWWMObject = savedStats.get(0);
        Assertions.assertEquals(3, playerStatsWWMObject.getRightAnswers());
        Assertions.assertEquals(3, playerStatsWWMObject.getPoints());
        Assertions.assertEquals(0, playerStatsWWMObject.getMoney());
    }

    @Test
    void wwmGameTestExistingStats1() {
        UserInMock userInMock = new UserInMock();
        userInMock.inputs.offer("test");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("1");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("1");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("1");
        userInMock.inputs.offer("c");
        userInMock.inputs.offer("2");

        ArrayList<Question> questions = new ArrayList<>();
        while (questions.size() < 50) {
            questions.add(Question.Builder.createFakeQuestion());
        }

        QuestionRepositoryMock questionRepositoryMock = new QuestionRepositoryMock();
        questionRepositoryMock.questions = questions;

        StatsRepositoryMock statsRepositoryMock = new StatsRepositoryMock();
        statsRepositoryMock.stats = List.of(new PlayerStatsWWMObject("test", 3, 50, 1));

        WerWirdMillionaer wwmQuizTest = new WerWirdMillionaer(userInMock,
                statsRepositoryMock,
                questionRepositoryMock,
                questionRepositoryMock,
                questionRepositoryMock,
                questionRepositoryMock,
                questionRepositoryMock);

        SelectedMenu.MenuSelection start = wwmQuizTest.start();
        Assertions.assertEquals(SelectedMenu.MenuSelection.BACK, start);

        List<PlayerStatsWWMObject> savedStats = statsRepositoryMock.savedStats;
        Assertions.assertEquals(1, savedStats.size());

        PlayerStatsWWMObject playerStatsWWMObject = savedStats.get(0);
        Assertions.assertEquals(4, playerStatsWWMObject.getRightAnswers());
        Assertions.assertEquals(6, playerStatsWWMObject.getPoints());
        Assertions.assertEquals(50, playerStatsWWMObject.getMoney());
    }

    @Test
    void wwmGameTestExistingStats2() {
        UserInMock userInMock = new UserInMock();
        userInMock.inputs.offer("test");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("1");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("1");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("1");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("1");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("2");
        userInMock.inputs.offer("2");

        ArrayList<Question> questions = new ArrayList<>();
        while (questions.size() < 50) {
            questions.add(Question.Builder.createFakeQuestion());
        }

        QuestionRepositoryMock questionRepositoryMock = new QuestionRepositoryMock();
        questionRepositoryMock.questions = questions;

        StatsRepositoryMock statsRepositoryMock = new StatsRepositoryMock();
        statsRepositoryMock.stats = List.of(new PlayerStatsWWMObject("test", 3, 50, 1));

        WerWirdMillionaer wwmQuizTest = new WerWirdMillionaer(userInMock,
                statsRepositoryMock,
                questionRepositoryMock,
                questionRepositoryMock,
                questionRepositoryMock,
                questionRepositoryMock,
                questionRepositoryMock);

        SelectedMenu.MenuSelection start = wwmQuizTest.start();
        Assertions.assertEquals(SelectedMenu.MenuSelection.BACK, start);

        List<PlayerStatsWWMObject> savedStats = statsRepositoryMock.savedStats;
        Assertions.assertEquals(1, savedStats.size());

        PlayerStatsWWMObject playerStatsWWMObject = savedStats.get(0);
        Assertions.assertEquals(6, playerStatsWWMObject.getRightAnswers());
        Assertions.assertEquals(8, playerStatsWWMObject.getPoints());
        Assertions.assertEquals(550, playerStatsWWMObject.getMoney());
    }
}