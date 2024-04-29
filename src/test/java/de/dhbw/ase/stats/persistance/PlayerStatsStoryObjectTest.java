package de.dhbw.ase.stats.persistance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PlayerStatsStoryObjectTest {

    @Test
    void fromLine() {
        PlayerStatsStoryObject playerStatsStoryObject = PlayerStatsStoryObject.fromLine("test;1;2");
        Assertions.assertEquals(1, playerStatsStoryObject.getHighestAchievedLevel());
        Assertions.assertEquals(2, playerStatsStoryObject.getTotalPlayedGames());
    }

    @Test
    void add() {
        PlayerStatsStoryObject playerStatsStoryObject1 = new PlayerStatsStoryObject("test", 1, 1);
        PlayerStatsStoryObject playerStatsStoryObject2 = new PlayerStatsStoryObject("test", 5, 2);

        playerStatsStoryObject1.add(playerStatsStoryObject2);

        Assertions.assertEquals(5, playerStatsStoryObject1.getHighestAchievedLevel());
        Assertions.assertEquals(3, playerStatsStoryObject1.getTotalPlayedGames());
        Assertions.assertEquals("test", playerStatsStoryObject1.getUsername());

        Assertions.assertEquals("test", playerStatsStoryObject2.getUsername());
        Assertions.assertEquals(5, playerStatsStoryObject2.getHighestAchievedLevel());
        Assertions.assertEquals(2, playerStatsStoryObject2.getTotalPlayedGames());
    }

    @Test
    void add2() {
        PlayerStatsStoryObject playerStatsStoryObject1 = new PlayerStatsStoryObject("test", 5, 3);
        PlayerStatsStoryObject playerStatsStoryObject2 = new PlayerStatsStoryObject("test", 2, 2);

        playerStatsStoryObject1.add(playerStatsStoryObject2);

        Assertions.assertEquals(5, playerStatsStoryObject1.getHighestAchievedLevel());
        Assertions.assertEquals(5, playerStatsStoryObject1.getTotalPlayedGames());
        Assertions.assertEquals("test", playerStatsStoryObject1.getUsername());

        Assertions.assertEquals("test", playerStatsStoryObject2.getUsername());
        Assertions.assertEquals(2, playerStatsStoryObject2.getHighestAchievedLevel());
        Assertions.assertEquals(2, playerStatsStoryObject2.getTotalPlayedGames());
    }

    @Test
    void testToString() {
        PlayerStatsStoryObject playerStatsStoryObject1 = new PlayerStatsStoryObject("test", 1, 2);

        String toString = playerStatsStoryObject1.toString();
        Assertions.assertEquals("test;1;2", toString);
    }

    @ParameterizedTest
    @MethodSource("compareToValues")
    void compareTo(int highestAchievedLevel1, int highestAchievedLevel2, int totalPlayedGames1, int totalPlayedGames2, int expected) {
        PlayerStatsStoryObject playerStatsStoryObject1 = new PlayerStatsStoryObject("test", highestAchievedLevel1, totalPlayedGames1);
        PlayerStatsStoryObject playerStatsStoryObject2 = new PlayerStatsStoryObject("test", highestAchievedLevel2, totalPlayedGames2);

        int i = playerStatsStoryObject1.compareTo(playerStatsStoryObject2);
        Assertions.assertEquals(expected, i);
    }

    private static Stream<Arguments> compareToValues() {
        return Stream.of(
                arguments(3, 2, 2, 3, 1),
                arguments(1, 2, 2, 3, -1),
                arguments(2, 2, 3, 2, 1),
                arguments(2, 2, 2, 3, -1),
                arguments(2, 2, 4, 4, 0)
        );
    }
}