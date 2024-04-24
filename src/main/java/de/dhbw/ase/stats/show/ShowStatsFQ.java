package de.dhbw.ase.stats.show;

import de.dhbw.ase.play.games.repository.StatsRepository;
import de.dhbw.ase.stats.persistance.PlayerStatsFQObject;

public class ShowStatsFQ extends ShowStats<PlayerStatsFQObject>{

    public ShowStatsFQ(StatsRepository statsRepository) {
        super(statsRepository);
    }

    @Override
    protected void displayStats(PlayerStatsFQObject state) {
        System.out.printf("| %-14s | %13s | %14s |%n",
                state.getUsername(),
                state.getRightAnswers(),
                state.getWrongAnswers());
    }

    @Override
    protected PlayerStatsFQObject mapLine(String line) {
        return PlayerStatsFQObject.fromLine(line);
    }

    @Override
    protected void displayFittingHeader() {
        System.out.printf("---------------------------------------------------------%n");
        System.out.printf("| %-14s | %-13s | %-14s |%n", "USERNAME", "RIGHT ANSWERS", "WRONG ANSWERS");
        System.out.printf("---------------------------------------------------------%n");
    }
}
