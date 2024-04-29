package de.dhbw.ase.stats.persistance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PlayerStatsWWMObjectTest {

    @Test
    void fromLine() {
        PlayerStatsWWMObject playerStatsWWMObject = PlayerStatsWWMObject.fromLine("test;1;2;3");
        Assertions.assertEquals(1, playerStatsWWMObject.getPoints());
        Assertions.assertEquals(2, playerStatsWWMObject.getMoney());
        Assertions.assertEquals(3, playerStatsWWMObject.getRightAnswers());
    }

    @Test
    void testToString() {
        PlayerStatsWWMObject playerStatsWWMObject1 = new PlayerStatsWWMObject("test", 1, 2, 3);

        String toString = playerStatsWWMObject1.toString();
        Assertions.assertEquals("test;1;2;3", toString);
    }

    @Test
    void add() {
        PlayerStatsWWMObject playerStatsWWMObject1 = new PlayerStatsWWMObject("test", 1, 1, 1);
        PlayerStatsWWMObject playerStatsWWMObject2 = new PlayerStatsWWMObject("test", 1, 1, 1);

        playerStatsWWMObject1.add(playerStatsWWMObject2);

        Assertions.assertEquals(2, playerStatsWWMObject1.getRightAnswers());
        Assertions.assertEquals(2, playerStatsWWMObject1.getMoney());
        Assertions.assertEquals(2, playerStatsWWMObject1.getPoints());
        Assertions.assertEquals("test", playerStatsWWMObject1.getUsername());

        Assertions.assertEquals("test", playerStatsWWMObject2.getUsername());
        Assertions.assertEquals(1, playerStatsWWMObject2.getMoney());
        Assertions.assertEquals(1, playerStatsWWMObject2.getRightAnswers());
        Assertions.assertEquals(1, playerStatsWWMObject2.getPoints());
    }

    @ParameterizedTest
    @MethodSource("compareToValues")
    void compareTo(int points1, int points2,
                   int money1, int money2,
                   int rightAnswers1, int rightAnswers2, int expected) {
        PlayerStatsWWMObject playerStatsWWMObject1 = new PlayerStatsWWMObject("test", points1, money1, rightAnswers1);
        PlayerStatsWWMObject playerStatsWWMObject2 = new PlayerStatsWWMObject("test", points2, money2, rightAnswers2);

        int i = playerStatsWWMObject1.compareTo(playerStatsWWMObject2);
        Assertions.assertEquals(expected, i);
    }

    private static Stream<Arguments> compareToValues() {
        return Stream.of(
                arguments(2, 3, 2, 3, 2, 3, -1),
                arguments(3, 2, 2, 3, 2, 3, 1),
                arguments(2, 2, 2, 3, 2, 3, -1),
                arguments(2, 2, 3, 2, 2, 3, 1),
                arguments(2, 2, 4, 4, 1, 1, 0)
        );
    }
}