package de.dhbw.ase.stats.persistance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerStatsSurvivalObjectTest {

    @Test
    void fromLine() {
        PlayerStatsSurvivalObject playerStatsSurvivalObject = PlayerStatsSurvivalObject.fromLine("test;1;2;3;4;5");
        Assertions.assertEquals(1, playerStatsSurvivalObject.getRightAnswers());
        Assertions.assertEquals(2, playerStatsSurvivalObject.getWrongAnswers());
        Assertions.assertEquals(3, playerStatsSurvivalObject.getAverageDeathLevel());
        Assertions.assertEquals(4, playerStatsSurvivalObject.getTotalSurvivedGames());
        Assertions.assertEquals(5, playerStatsSurvivalObject.getTotalPlayedGames());
    }

    @Test
    void add() {
        PlayerStatsSurvivalObject playerStatsSurvivalObject1 = new PlayerStatsSurvivalObject("test",
                1,
                1,
                2,
                1,
                3);
        PlayerStatsSurvivalObject playerStatsSurvivalObject2 = new PlayerStatsSurvivalObject("test",
                5,
                2,
                4,
                1,
                3);

        playerStatsSurvivalObject1.add(playerStatsSurvivalObject2);

        Assertions.assertEquals(6, playerStatsSurvivalObject1.getRightAnswers());
        Assertions.assertEquals(3, playerStatsSurvivalObject1.getWrongAnswers());
        Assertions.assertEquals(3, playerStatsSurvivalObject1.getAverageDeathLevel());
        Assertions.assertEquals(2, playerStatsSurvivalObject1.getTotalSurvivedGames());
        Assertions.assertEquals(6, playerStatsSurvivalObject1.getTotalPlayedGames());
        Assertions.assertEquals("test", playerStatsSurvivalObject1.getUsername());

        Assertions.assertEquals("test", playerStatsSurvivalObject2.getUsername());
        Assertions.assertEquals(5, playerStatsSurvivalObject2.getRightAnswers());
        Assertions.assertEquals(2, playerStatsSurvivalObject2.getWrongAnswers());
        Assertions.assertEquals(4, playerStatsSurvivalObject2.getAverageDeathLevel());
        Assertions.assertEquals(1, playerStatsSurvivalObject2.getTotalSurvivedGames());
        Assertions.assertEquals(3, playerStatsSurvivalObject2.getTotalPlayedGames());
    }

    @Test
    void testToString() {
    }

    @Test
    void compareTo() {
    }

    @Test
    void getRightAnswers() {
    }

    @Test
    void getWrongAnswers() {
    }

    @Test
    void getAverageDeathLevel() {
    }

    @Test
    void getTotalSurvivedGames() {
    }

    @Test
    void getTotalPlayedGames() {
    }
}