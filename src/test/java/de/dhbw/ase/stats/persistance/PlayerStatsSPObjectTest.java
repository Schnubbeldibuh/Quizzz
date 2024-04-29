package de.dhbw.ase.stats.persistance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    void compareTo() {
    }

    @Test
    void testToString() {
    }

    @Test
    void getRightAnswers() {
    }

    @Test
    void getWrongAnswers() {
    }

    @Test
    void getAverageAnswerTime() {
    }
}