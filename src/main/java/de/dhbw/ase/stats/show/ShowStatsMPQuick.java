package de.dhbw.ase.stats.show;

import de.dhbw.ase.repository.StatsRepository;
import de.dhbw.ase.stats.persistance.PlayerStatsMPQuickObject;

public class ShowStatsMPQuick extends ShowStats<PlayerStatsMPQuickObject> {

    public ShowStatsMPQuick(StatsRepository statsRepository) {
        super(statsRepository);
    }


    @Override
    protected PlayerStatsMPQuickObject mapLine(String line) {
        return PlayerStatsMPQuickObject.fromeLine(line);
    }

    @Override
    protected void displayStats(PlayerStatsMPQuickObject state) {
        System.out.printf("| %-15s | %13s | %14s |%14s |%n",
                state.getUsername(),
                state.getRightAnswers(),
                state.getWrongAnswers(),
                state.getFastestAnswer());
    }

    @Override
    protected void displayFittingHeader() {
        System.out.printf("---------------------------------------------------------------%n");
        System.out.printf("| %-15s | %-13s | %-14s |%-14s |%n", "USERNAME", "RIGHT ANSWERS", "WRONG ANSWERS", "FASTEST ANSWER");
        System.out.printf("---------------------------------------------------------------%n");
    }
}
