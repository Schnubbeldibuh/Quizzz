package de.dhbw.ase.stats.persistance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerStatsMPObjectTest {

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
    void getPoints() {
    }
}