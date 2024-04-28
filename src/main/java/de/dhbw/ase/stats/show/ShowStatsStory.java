package de.dhbw.ase.stats.show;

import de.dhbw.ase.repository.StatsRepository;
import de.dhbw.ase.stats.persistance.PlayerStatsSPObject;
import de.dhbw.ase.stats.persistance.PlayerStatsStoryObject;

public class ShowStatsStory extends ShowStats<PlayerStatsStoryObject>{

    public ShowStatsStory(StatsRepository statsRepository) {
        super(statsRepository);
    }

    @Override
    protected PlayerStatsStoryObject mapLine(String line) {return PlayerStatsStoryObject.fromLine(line);}

    @Override
    protected void displayStats(PlayerStatsStoryObject state) {
        System.out.printf("| %14s | %22s | %18s |%n",
                state.getUsername(),
                state.getHighestAchievedLevel(),
                state.getTotalPlayedGames());
    }

    @Override
    protected void displayFittingHeader() {
        System.out.printf("------------------------------------------------------%n");
        System.out.printf("| %-14s | %-22s | %-18s |%n",
                "USERNAME", "HIGHEST ACHIEVED LEVEL", "TOTAL PLAYED GAMES");
        System.out.printf("------------------------------------------------------%n");
    }
}
