package de.dhbw.ase.stats.persistance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerStatsFQObjectTest {

    @Test
    void fromLine() {
        PlayerStatsFQObject playerStatsFQObject = PlayerStatsFQObject.fromLine("test;1;2");
        Assertions.assertEquals(1, playerStatsFQObject.getRightAnswers());
        Assertions.assertEquals(2, playerStatsFQObject.getWrongAnswers());
    }

    @Test
    void testToString() {
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

    @Test
    void compareTo() {
    }

    @Test
    void getRightAnswers() {
    }

    @Test
    void getWrongAnswers() {
    }
}