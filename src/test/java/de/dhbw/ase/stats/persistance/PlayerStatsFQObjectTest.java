package de.dhbw.ase.stats.persistance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayerStatsFQObjectTest {

    @Test
    public void testAdd() {
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
}