package de.dhbw.ase.stats.persistance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    void getFastestAnswer() {
    }
}