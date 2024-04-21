package de.dhbw.ase.stats.show;

import de.dhbw.ase.Startable;
import de.dhbw.ase.stats.StatsMenu;
import de.dhbw.ase.stats.persistance.PlayerStatsMPQuickObejct;
import de.dhbw.ase.stats.persistance.PlayerStatsWWMObject;

public class ShowStatsWWM extends ShowStats {

    public ShowStatsWWM(String filename) {
        super(filename);
    }

    @Override
    protected void displayStats(String string) {
        PlayerStatsWWMObject playerStatsWWMObject = new PlayerStatsWWMObject(string);
        System.out.printf("| %-15s | %13s | %6s |%10s |%n",
                playerStatsWWMObject.getUsername(),
                playerStatsWWMObject.getRightAnswers(),
                playerStatsWWMObject.getPoints(),
                playerStatsWWMObject.getMoney());
    }

    @Override
    protected void displayFittingHeader() {
        System.out.printf("---------------------------------------------------------%n");
        System.out.printf("| %-15s | %-13s | %-6s |%-10s |%n", "USERNAME", "RIGHT ANSWERS", "POINTS", "MONEY");
        System.out.printf("---------------------------------------------------------%n");
    }

}
