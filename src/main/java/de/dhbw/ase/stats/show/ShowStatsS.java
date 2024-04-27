package de.dhbw.ase.stats.show;

import de.dhbw.ase.repository.StatsRepository;
import de.dhbw.ase.stats.persistance.PlayerStatsSPObject;
import de.dhbw.ase.stats.persistance.PlayerStatsSurvivalObject;

public class ShowStatsS extends ShowStats<PlayerStatsSurvivalObject>{
    public ShowStatsS(StatsRepository statsRepository) {
        super(statsRepository);
    }

    @Override
    protected PlayerStatsSurvivalObject mapLine(String line) {
        return PlayerStatsSurvivalObject.fromLine(line);
    }

    @Override
    protected void displayStats(PlayerStatsSurvivalObject state) {
        System.out.printf("| %-14s | %-13s | %-14s | %-19s | %-20s | %-18s |%n",
                state.getUsername(),
                state.getRightAnswers(),
                state.getWrongAnswers(),
                state.getAverageDeathLevel(),
                state.getTotalSurvivedGames(),
                state.getTotalPlayedGames());
    }

    @Override
    protected void displayFittingHeader() {
        System.out.printf("--------------------------------------------------" +
                "----------------------------------------------------------------%n");
        System.out.printf("| %-14s | %-13s | %-14s | %-19s | %-20s | %-18s |%n",
                "USERNAME",
                "RIGHT ANSWERS",
                "WRONG ANSWERS",
                "AVERAGE DEATH LEVEL",
                "TOTAL SURVIVED GAMES",
                "TOTAL PLAYED GAMES");
        System.out.printf("----------------------------------------------------" +
                "--------------------------------------------------------------%n");
    }
}
