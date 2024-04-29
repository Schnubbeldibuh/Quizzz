package de.dhbw.ase.stats.persistance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PlayerStatsMPQuickObjectTest {

    @Test
    void fromeLine() {
        PlayerStatsMPQuickObject playerStatsMPQuickObject = PlayerStatsMPQuickObject.fromLine("test;1;2;3");
        Assertions.assertEquals(1, playerStatsMPQuickObject.getRightAnswers());
        Assertions.assertEquals(2, playerStatsMPQuickObject.getWrongAnswers());
        Assertions.assertEquals(3, playerStatsMPQuickObject.getFastestAnswer());
    }

    @Test
    void testToString() {
        PlayerStatsMPQuickObject playerStatsMPQuickObject1 = new PlayerStatsMPQuickObject("test", 1, 2, 3);

        String toString = playerStatsMPQuickObject1.toString();
        Assertions.assertEquals("test;1;2;3", toString);
    }

    @Test
    void add() {
        PlayerStatsMPQuickObject playerStatsMPQuickObject1 = new PlayerStatsMPQuickObject("test", 1, 1, 1);
        PlayerStatsMPQuickObject playerStatsMPQuickObject2 = new PlayerStatsMPQuickObject("test", 1, 1, 1);

        playerStatsMPQuickObject1.add(playerStatsMPQuickObject2);

        Assertions.assertEquals(2, playerStatsMPQuickObject1.getRightAnswers());
        Assertions.assertEquals(2, playerStatsMPQuickObject1.getWrongAnswers());
        Assertions.assertEquals(2, playerStatsMPQuickObject1.getFastestAnswer());
        Assertions.assertEquals("test", playerStatsMPQuickObject1.getUsername());

        Assertions.assertEquals("test", playerStatsMPQuickObject2.getUsername());
        Assertions.assertEquals(1, playerStatsMPQuickObject2.getWrongAnswers());
        Assertions.assertEquals(1, playerStatsMPQuickObject2.getRightAnswers());
        Assertions.assertEquals(1, playerStatsMPQuickObject2.getFastestAnswer());
    }

    @ParameterizedTest
    @MethodSource("compareToValues")
    void compareTo(int rightAnswer1, int rightAnswer2, int fastestAnswer1, int fastestAnswer2, int wrongAnswer1, int wrongAnswer2, int expected) {
        PlayerStatsMPQuickObject playerStatsMPQuickObject1 = new PlayerStatsMPQuickObject("test", rightAnswer1, wrongAnswer1, fastestAnswer1 );
        PlayerStatsMPQuickObject playerStatsMPQuickObject2 = new PlayerStatsMPQuickObject("test", rightAnswer2, wrongAnswer2, fastestAnswer2);

        int i = playerStatsMPQuickObject1.compareTo(playerStatsMPQuickObject2);
        Assertions.assertEquals(expected, i);
    }

    private static Stream<Arguments> compareToValues() {
        return Stream.of(
                arguments(1, 2, 2, 3, 1, 1, -1),
                arguments(3, 2, 2, 3, 1, 1, 1),
                arguments(2, 2, 2, 3, 1, 1, -1),
                arguments(2, 2, 4, 3, 1, 1, 1),
                arguments(2, 2, 3, 3, 1, 2, 1),
                arguments(2, 2, 3, 3, 2, 1, -1),
                arguments(2, 2, 4, 4, 1, 1, 0)
        );
    }
}