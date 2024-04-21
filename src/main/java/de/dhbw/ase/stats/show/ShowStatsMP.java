package de.dhbw.ase.stats.show;

import de.dhbw.ase.stats.persistance.PlayerStatsMPObject;
import de.dhbw.ase.stats.persistance.PlayerStatsWWMObject;

public class ShowStatsMP extends ShowStats{
    public ShowStatsMP(String filename) {
        super(filename);
    }

    @Override
    protected void displayStats(String string) {
        PlayerStatsMPObject playerStatsMPObject = new PlayerStatsMPObject(string);
        System.out.printf("| %-15s | %13s | %14s |%6s |%n",
                playerStatsMPObject.getUsername(),
                playerStatsMPObject.getRightAnswers(),
                playerStatsMPObject.getWrongAnswers(),
                playerStatsMPObject.getPoints());
    }

    @Override
    protected void displayFittingHeader() {
        System.out.printf("--------------------------------------------------------------%n");
        System.out.printf("| %-15s | %-13s | %-14s |%-6s |%n", "USERNAME", "RIGHT ANSWERS", "WRONG ANSWERS", "POINTS");
        System.out.printf("--------------------------------------------------------------%n");
    }
}
