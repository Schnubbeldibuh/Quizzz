package de.dhbw.ase.stats.persistance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

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
        PlayerStatsSurvivalObject playerStatsSurvivalObject1 = new PlayerStatsSurvivalObject("test", 1, 2,3,4,5);

        String toString = playerStatsSurvivalObject1.toString();
        Assertions.assertEquals("test;1;2;3;4;5", toString);
    }

    @ParameterizedTest
    @MethodSource("compareToValues")
    void compareTo(int rightAnswers1, int rightAnswers2,
                   int wrongAnswers1, int wrongAnswers2,
                   int averageDeathLevel1, int averageDeathLevel2,
                   int totalSurvivedGames1, int totalSurvivedGames2,
                   int totalPlayedGames1, int totalPlayedGames2,
                   int expected) {
        PlayerStatsSurvivalObject playerStatsSurvivalObject1 = new PlayerStatsSurvivalObject("test", rightAnswers1, wrongAnswers1, averageDeathLevel1, totalSurvivedGames1, totalPlayedGames1);
        PlayerStatsSurvivalObject playerStatsSurvivalObject2 = new PlayerStatsSurvivalObject("test", rightAnswers2, wrongAnswers2, averageDeathLevel2, totalSurvivedGames2, totalPlayedGames2);

        int i = playerStatsSurvivalObject1.compareTo(playerStatsSurvivalObject2);
        Assertions.assertEquals(expected, i);
    }

    private static Stream<Arguments> compareToValues() {
        return Stream.of(
                arguments(2, 3, 2, 3, 2, 2, 2, 2, 2, 3, -1),
                arguments(3, 2, 2, 3, 2, 2, 2, 2, 2, 3, 1),
                arguments(2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 1),
                arguments(2, 2, 3, 2, 3, 3, 3, 3, 3, 3, -1),
                arguments(2, 2, 3, 3, 4, 3, 2, 2, 2, 3, 1),
                arguments(2, 2, 3, 3, 1, 2, 2, 2, 2, 3, -1),
                arguments(2, 2, 3, 3, 4, 4, 4, 3, 2, 3, 1),
                arguments(2, 2, 3, 3, 4, 4, 1, 2, 2, 3, -1),
                arguments(2, 2, 4, 4, 3, 3, 2, 2, 1, 1, 0)
        );
    }
}