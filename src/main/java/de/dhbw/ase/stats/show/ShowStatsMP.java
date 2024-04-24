package de.dhbw.ase.stats.show;

import de.dhbw.ase.play.games.repository.StatsRepository;
import de.dhbw.ase.stats.persistance.PlayerStatsMPObject;

public class ShowStatsMP extends ShowStats<PlayerStatsMPObject>{

    public ShowStatsMP(StatsRepository statsRepository) {
        super(statsRepository);
    }

    @Override
    protected PlayerStatsMPObject mapLine(String line) {
        return PlayerStatsMPObject.fromLine(line);
    }

    @Override
    protected void displayStats(PlayerStatsMPObject state) {
        System.out.printf("| %-15s | %13s | %14s |%6s |%n",
                state.getUsername(),
                state.getRightAnswers(),
                state.getWrongAnswers(),
                state.getPoints());
    }

    @Override
    protected void displayFittingHeader() {
        System.out.printf("--------------------------------------------------------------%n");
        System.out.printf("| %-15s | %-13s | %-14s |%-6s |%n", "USERNAME", "RIGHT ANSWERS", "WRONG ANSWERS", "POINTS");
        System.out.printf("--------------------------------------------------------------%n");
    }
}
