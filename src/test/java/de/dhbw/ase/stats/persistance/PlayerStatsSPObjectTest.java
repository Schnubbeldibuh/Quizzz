package de.dhbw.ase.stats.persistance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PlayerStatsSPObjectTest {

    @Test
    void fromLine() {
        PlayerStatsSPObject playerStatsSPObject = PlayerStatsSPObject.fromLine("test;1;2;3");
        Assertions.assertEquals(1, playerStatsSPObject.getRightAnswers());
        Assertions.assertEquals(2, playerStatsSPObject.getWrongAnswers());
        Assertions.assertEquals(3, playerStatsSPObject.getAverageAnswerTime());
    }

    @Test
    void add() {
        PlayerStatsSPObject playerStatsSPObject1 = new PlayerStatsSPObject("test", 1, 1, 1);
        PlayerStatsSPObject playerStatsSPObject2 = new PlayerStatsSPObject("test", 1, 1, 3);

        playerStatsSPObject1.add(playerStatsSPObject2);

        Assertions.assertEquals(2, playerStatsSPObject1.getRightAnswers());
        Assertions.assertEquals(2, playerStatsSPObject1.getWrongAnswers());
        Assertions.assertEquals(2, playerStatsSPObject1.getAverageAnswerTime());
        Assertions.assertEquals("test", playerStatsSPObject1.getUsername());

        Assertions.assertEquals("test", playerStatsSPObject2.getUsername());
        Assertions.assertEquals(1, playerStatsSPObject2.getWrongAnswers());
        Assertions.assertEquals(1, playerStatsSPObject2.getRightAnswers());
        Assertions.assertEquals(3, playerStatsSPObject2.getAverageAnswerTime());
    }

    @Test
    void testToString() {
        PlayerStatsSPObject playerStatsSPObject1 = new PlayerStatsSPObject("test", 1, 2, 3);

        String toString = playerStatsSPObject1.toString();
        Assertions.assertEquals("test;1;2;3", toString);
    }

    @ParameterizedTest
    @MethodSource("compareToValues")
    void compareTo(int rightAnswer1, int rightAnswer2, int wrongAnswer1, int wrongAnswer2, int averageAnswerTime1, int averageAnswerTime2, int expected) {
        PlayerStatsSPObject playerStatsSPObject1 = new PlayerStatsSPObject("test", rightAnswer1, wrongAnswer1, averageAnswerTime1);
        PlayerStatsSPObject playerStatsSPObject2 = new PlayerStatsSPObject("test", rightAnswer2, wrongAnswer2, averageAnswerTime2);

        int i = playerStatsSPObject1.compareTo(playerStatsSPObject2);
        Assertions.assertEquals(expected, i);
    }

    private static Stream<Arguments> compareToValues() {
        return Stream.of(
                arguments(1, 2, 2, 3, 1, 1, -1),
                arguments(3, 2, 2, 3, 1, 1, 1),
                arguments(2, 2, 2, 3, 1, 1, 1),
                arguments(2, 2, 4, 3, 1, 1, -1),
                arguments(2, 2, 3, 3, 1, 2, 1),
                arguments(2, 2, 3, 3, 2, 1, -1),
                arguments(2, 2, 4, 4, 1, 1, 0)
        );
    }
}