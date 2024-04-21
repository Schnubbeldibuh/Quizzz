package de.dhbw.ase.stats.show;

import de.dhbw.ase.stats.persistance.PlayerStatsFQObject;
import de.dhbw.ase.stats.persistance.PlayerStatsMPQuickObejct;

public class ShowStatsMPQuick extends ShowStats{
    public ShowStatsMPQuick(String filename) {
        super(filename);
    }

    @Override
    protected void displayStats(String string) {
        PlayerStatsMPQuickObejct playerStatsMPQuickObejct = new PlayerStatsMPQuickObejct(string);
        System.out.printf("| %-15s | %13s | %14s |%14s |%n",
                playerStatsMPQuickObejct.getUsername(),
                playerStatsMPQuickObejct.getRightAnswers(),
                playerStatsMPQuickObejct.getWrongAnswers(),
                playerStatsMPQuickObejct.getFastestAnswer());
    }

    @Override
    protected void displayFittingHeader() {
        System.out.printf("---------------------------------------------------------------%n");
        System.out.printf("| %-15s | %-13s | %-14s |%-14s |%n", "USERNAME", "RIGHT ANSWERS", "WRONG ANSWERS", "FASTEST ANSWER");
        System.out.printf("---------------------------------------------------------------%n");
    }
}
