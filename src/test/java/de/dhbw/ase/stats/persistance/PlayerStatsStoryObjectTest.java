package de.dhbw.ase.stats.persistance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerStatsStoryObjectTest {

    @Test
    void fromLine() {
        PlayerStatsStoryObject playerStatsStoryObject = PlayerStatsStoryObject.fromLine("test;1;2");
        Assertions.assertEquals(1, playerStatsStoryObject.getHighestAchievedLevel());
        Assertions.assertEquals(2, playerStatsStoryObject.getTotalPlayedGames());
    }

    @Test
    void add() {
        PlayerStatsStoryObject playerStatsStoryObject1 = new PlayerStatsStoryObject("test", 1, 1);
        PlayerStatsStoryObject playerStatsStoryObject2 = new PlayerStatsStoryObject("test", 5, 2);

        playerStatsStoryObject1.add(playerStatsStoryObject2);

        Assertions.assertEquals(5, playerStatsStoryObject1.getHighestAchievedLevel());
        Assertions.assertEquals(3, playerStatsStoryObject1.getTotalPlayedGames());
        Assertions.assertEquals("test", playerStatsStoryObject1.getUsername());

        Assertions.assertEquals("test", playerStatsStoryObject2.getUsername());
        Assertions.assertEquals(5, playerStatsStoryObject2.getHighestAchievedLevel());
        Assertions.assertEquals(2, playerStatsStoryObject2.getTotalPlayedGames());
    }

    @Test
    void add2() {
        PlayerStatsStoryObject playerStatsStoryObject1 = new PlayerStatsStoryObject("test", 5, 3);
        PlayerStatsStoryObject playerStatsStoryObject2 = new PlayerStatsStoryObject("test", 2, 2);

        playerStatsStoryObject1.add(playerStatsStoryObject2);

        Assertions.assertEquals(5, playerStatsStoryObject1.getHighestAchievedLevel());
        Assertions.assertEquals(5, playerStatsStoryObject1.getTotalPlayedGames());
        Assertions.assertEquals("test", playerStatsStoryObject1.getUsername());

        Assertions.assertEquals("test", playerStatsStoryObject2.getUsername());
        Assertions.assertEquals(2, playerStatsStoryObject2.getHighestAchievedLevel());
        Assertions.assertEquals(2, playerStatsStoryObject2.getTotalPlayedGames());
    }

    @Test
    void compareTo() {
    }

    @Test
    void testToString() {
    }

    @Test
    void getHighestAchievedLevel() {
    }

    @Test
    void getTotalPlayedGames() {
    }
}