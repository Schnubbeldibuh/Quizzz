package de.dhbw.ase.stats.show;

import de.dhbw.ase.stats.persistance.PlayerStatsFQObject;

public class ShowStatsFQ extends ShowStats{
    public ShowStatsFQ(String filename) {
        super(filename);
    }

    @Override
    protected void displayStats(String string) {
        PlayerStatsFQObject playerStatsFQObject = new PlayerStatsFQObject(string);
        System.out.printf("| %-14s | %13s | %14s |%n",
                playerStatsFQObject.getUsername(),
                playerStatsFQObject.getRightAnswers(),
                playerStatsFQObject.getWrongAnswers());
    }

    @Override
    protected void displayFittingHeader() {
        System.out.printf("---------------------------------------------------------%n");
        System.out.printf("| %-14s | %-13s | %-14s |%n", "USERNAME", "RIGHT ANSWERS", "WRONG ANSWERS");
        System.out.printf("---------------------------------------------------------%n");
    }
}
