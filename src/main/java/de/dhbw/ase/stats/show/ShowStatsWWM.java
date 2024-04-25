package de.dhbw.ase.stats.show;

import de.dhbw.ase.repository.StatsRepository;
import de.dhbw.ase.stats.persistance.PlayerStatsWWMObject;

public class ShowStatsWWM extends ShowStats<PlayerStatsWWMObject> {

    public ShowStatsWWM(StatsRepository statsRepository) {
        super(statsRepository);
    }

    @Override
    protected void displayStats(PlayerStatsWWMObject state) {
        System.out.printf("| %-15s | %13s | %6s |%10s |%n",
                state.getUsername(),
                state.getRightAnswers(),
                state.getPoints(),
                state.getMoney());
    }

    @Override
    protected PlayerStatsWWMObject mapLine(String line) {
        return PlayerStatsWWMObject.fromLine(line);
    }

    @Override
    protected void displayFittingHeader() {
        System.out.printf("---------------------------------------------------------%n");
        System.out.printf("| %-15s | %-13s | %-6s |%-10s |%n", "USERNAME", "RIGHT ANSWERS", "POINTS", "MONEY");
        System.out.printf("---------------------------------------------------------%n");
    }

}
