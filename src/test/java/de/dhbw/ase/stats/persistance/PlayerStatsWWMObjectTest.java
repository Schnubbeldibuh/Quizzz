package de.dhbw.ase.stats.persistance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void compareTo() {
    }

    @Test
    void getRightAnswers() {
    }

    @Test
    void getPoints() {
    }

    @Test
    void getMoney() {
    }
}