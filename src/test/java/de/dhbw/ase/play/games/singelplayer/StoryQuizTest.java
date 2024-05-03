package de.dhbw.ase.play.games.singelplayer;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.repository.QuestionRepositoryMock;
import de.dhbw.ase.repository.StatsRepositoryMock;
import de.dhbw.ase.repository.question.Question;
import de.dhbw.ase.stats.persistance.PlayerStatsSPObject;
import de.dhbw.ase.stats.persistance.PlayerStatsStoryObject;
import de.dhbw.ase.user.in.UserInMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StoryQuizTest {
    @Test
    void storyGameTestBlankStats() {
        UserInMock userInMock = new UserInMock();
        userInMock.inputs.offer("test");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("b");
        userInMock.inputs.offer("2");

        ArrayList<Question> questions = new ArrayList<>();
        while (questions.size() < 8) {
            questions.add(Question.Builder.createFakeQuestion());
        }

        QuestionRepositoryMock questionRepositoryMock = new QuestionRepositoryMock();
        questionRepositoryMock.questions = questions;

        StatsRepositoryMock statsRepositoryMock = new StatsRepositoryMock();

        StoryQuiz storyQuizTest = new StoryQuiz(userInMock, questionRepositoryMock, statsRepositoryMock);

        SelectedMenu.MenuSelection start = storyQuizTest.start();
        Assertions.assertEquals(SelectedMenu.MenuSelection.BACK, start);

        List<PlayerStatsStoryObject> savedStats = statsRepositoryMock.savedStats;
        Assertions.assertEquals(1, savedStats.size());

        PlayerStatsStoryObject playerStatsStoryObject = savedStats.get(0);
        Assertions.assertEquals(6, playerStatsStoryObject.getHighestAchievedLevel());
        Assertions.assertEquals(1, playerStatsStoryObject.getTotalPlayedGames());
    }

    @Test
    void storyGameTestExistingStats1() {
        UserInMock userInMock = new UserInMock();
        userInMock.inputs.offer("test");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("b");
        userInMock.inputs.offer("2");

        ArrayList<Question> questions = new ArrayList<>();
        while (questions.size() < 8) {
            questions.add(Question.Builder.createFakeQuestion());
        }

        QuestionRepositoryMock questionRepositoryMock = new QuestionRepositoryMock();
        questionRepositoryMock.questions = questions;

        StatsRepositoryMock statsRepositoryMock = new StatsRepositoryMock();
        statsRepositoryMock.stats = List.of(new PlayerStatsStoryObject("test", 4, 2));

        StoryQuiz storyQuizTest = new StoryQuiz(userInMock, questionRepositoryMock, statsRepositoryMock);

        SelectedMenu.MenuSelection start = storyQuizTest.start();
        Assertions.assertEquals(SelectedMenu.MenuSelection.BACK, start);

        List<PlayerStatsStoryObject> savedStats = statsRepositoryMock.savedStats;
        Assertions.assertEquals(1, savedStats.size());

        PlayerStatsStoryObject playerStatsStoryObject = savedStats.get(0);
        Assertions.assertEquals(6, playerStatsStoryObject.getHighestAchievedLevel());
        Assertions.assertEquals(3, playerStatsStoryObject.getTotalPlayedGames());
    }

    @Test
    void storyGameTestExistingStats2() {
        UserInMock userInMock = new UserInMock();
        userInMock.inputs.offer("test");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("a");
        userInMock.inputs.offer("b");
        userInMock.inputs.offer("2");

        ArrayList<Question> questions = new ArrayList<>();
        while (questions.size() < 8) {
            questions.add(Question.Builder.createFakeQuestion());
        }

        QuestionRepositoryMock questionRepositoryMock = new QuestionRepositoryMock();
        questionRepositoryMock.questions = questions;

        StatsRepositoryMock statsRepositoryMock = new StatsRepositoryMock();
        statsRepositoryMock.stats = List.of(new PlayerStatsStoryObject("test", 8, 2));

        StoryQuiz storyQuizTest = new StoryQuiz(userInMock, questionRepositoryMock, statsRepositoryMock);

        SelectedMenu.MenuSelection start = storyQuizTest.start();
        Assertions.assertEquals(SelectedMenu.MenuSelection.BACK, start);

        List<PlayerStatsStoryObject> savedStats = statsRepositoryMock.savedStats;
        Assertions.assertEquals(1, savedStats.size());

        PlayerStatsStoryObject playerStatsStoryObject = savedStats.get(0);
        Assertions.assertEquals(8, playerStatsStoryObject.getHighestAchievedLevel());
        Assertions.assertEquals(3, playerStatsStoryObject.getTotalPlayedGames());
    }
}