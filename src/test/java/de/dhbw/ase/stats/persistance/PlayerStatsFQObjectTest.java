package de.dhbw.ase.stats.persistance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PlayerStatsFQObjectTest {

    @Test
    void fromLine() {
        PlayerStatsFQObject playerStatsFQObject = PlayerStatsFQObject.fromLine("test;1;2");
        Assertions.assertEquals(1, playerStatsFQObject.getRightAnswers());
        Assertions.assertEquals(2, playerStatsFQObject.getWrongAnswers());
    }

    @Test
    void testToString() {
        PlayerStatsFQObject playerStatsFQObject1 = new PlayerStatsFQObject("test", 1, 2);

        String toString = playerStatsFQObject1.toString();
        Assertions.assertEquals("test;1;2", toString);
    }

    @Test
    void add() {
        PlayerStatsFQObject playerStatsFQObject1 = new PlayerStatsFQObject("test", 1, 1);
        PlayerStatsFQObject playerStatsFQObject2 = new PlayerStatsFQObject("test", 1, 1);

        playerStatsFQObject1.add(playerStatsFQObject2);

        Assertions.assertEquals(2, playerStatsFQObject1.getRightAnswers());
        Assertions.assertEquals(2, playerStatsFQObject1.getWrongAnswers());
        Assertions.assertEquals("test", playerStatsFQObject1.getUsername());

        Assertions.assertEquals("test", playerStatsFQObject2.getUsername());
        Assertions.assertEquals(1, playerStatsFQObject2.getWrongAnswers());
        Assertions.assertEquals(1, playerStatsFQObject2.getRightAnswers());
    }

    @ParameterizedTest
    @MethodSource("compareToValues")
    void compareTo(int rightAnswer1, int rightAnswer2, int wrongAnswer1, int wrongAnswer2, int expected) {
        PlayerStatsFQObject playerStatsFQObject1 = new PlayerStatsFQObject("test", rightAnswer1, wrongAnswer1);
        PlayerStatsFQObject playerStatsFQObject2 = new PlayerStatsFQObject("test", rightAnswer2, wrongAnswer2);

        int i = playerStatsFQObject1.compareTo(playerStatsFQObject2);
        Assertions.assertEquals(expected, i);
    }

    private static Stream<Arguments> compareToValues() {
        return Stream.of(
                arguments(1, 2, 2, 3, -1),
                arguments(3, 2, 2, 3, 1),
                arguments(2, 2, 2, 3, 1),
                arguments(2, 2, 4, 3, -1),
                arguments(2, 2, 4, 4, 0)
        );
    }
}