package de.dhbw.ase.stats.persistance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

class PlayerStatsMPObjectTest {

    private static Stream<Arguments> compareToValues() {
        return Stream.of(
                arguments(1, 2, 2, 3, 1, 1, -1),
                arguments(3, 2, 2, 3, 1, 1, 1),
                arguments(2, 2, 2, 3, 1, 1, 1),
                arguments(2, 2, 4, 3, 1, 1, -1),
                arguments(2, 2, 4, 4, 1, 1, 0)
                );
    }

    @Test
    void fromLine() {
        PlayerStatsMPObject playerStatsMPObject = PlayerStatsMPObject.fromLine("test;1;2;3");
        Assertions.assertEquals(1, playerStatsMPObject.getRightAnswers());
        Assertions.assertEquals(2, playerStatsMPObject.getWrongAnswers());
        Assertions.assertEquals(3, playerStatsMPObject.getPoints());
    }

    @Test
    void testToString() {
        PlayerStatsMPObject playerStatsMPObject1 = new PlayerStatsMPObject("test", 1, 2, 3);

        String toString = playerStatsMPObject1.toString();
        Assertions.assertEquals("test;1;2;3", toString);
    }

    @Test
    void add() {
        PlayerStatsMPObject playerStatsMPObject1 = new PlayerStatsMPObject("test", 1, 1, 1);
        PlayerStatsMPObject playerStatsMPObject2 = new PlayerStatsMPObject("test", 1, 1, 1);

        playerStatsMPObject1.add(playerStatsMPObject2);

        Assertions.assertEquals(2, playerStatsMPObject1.getRightAnswers());
        Assertions.assertEquals(2, playerStatsMPObject1.getWrongAnswers());
        Assertions.assertEquals(2, playerStatsMPObject1.getPoints());
        Assertions.assertEquals("test", playerStatsMPObject1.getUsername());

        Assertions.assertEquals("test", playerStatsMPObject2.getUsername());
        Assertions.assertEquals(1, playerStatsMPObject2.getWrongAnswers());
        Assertions.assertEquals(1, playerStatsMPObject2.getRightAnswers());
        Assertions.assertEquals(1, playerStatsMPObject2.getPoints());
    }

    @ParameterizedTest
    @MethodSource("compareToValues")
    void compareTo(int rightAnswer1, int rightAnswer2, int wrongAnswer1, int wrongAnswer2, int points1, int points2, int expected) {
        PlayerStatsMPObject playerStatsMPObject1 = new PlayerStatsMPObject("test", rightAnswer1, wrongAnswer1, points1);
        PlayerStatsMPObject playerStatsMPObject2 = new PlayerStatsMPObject("test", rightAnswer2, wrongAnswer2, points2);

        int i = playerStatsMPObject1.compareTo(playerStatsMPObject2);
        Assertions.assertEquals(expected, i);
    }
}