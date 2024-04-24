package de.dhbw.ase.stats.show;

import de.dhbw.ase.play.games.repository.StatsRepository;
import de.dhbw.ase.stats.persistance.PlayerStatsSPObject;

public class ShowStatsSP extends ShowStats<PlayerStatsSPObject> {

    public ShowStatsSP(StatsRepository statsRepository) {
        super(statsRepository);
    }

    @Override
    protected PlayerStatsSPObject mapLine(String line) {
        return PlayerStatsSPObject.fromLine(line);
    }

    @Override
    protected void displayStats(PlayerStatsSPObject state) {
        System.out.printf("| %-14s | %-13s | %-14s | %-19s |%n",
                state.getUsername(),
                state.getRightAnswers(),
                state.getWrongAnswers(),
                state.getAverageAnswerTime());
    }

    @Override
    protected void displayFittingHeader() {
        System.out.printf("----------------------------------------------------------------------------%n");
        System.out.printf("| %-14s | %-13s | %-14s | %-19s |%n",
                "USERNAME", "RIGHT ANSWERS", "WRONG ANSWERS", "AVERAGE ANSWER TIME");
        System.out.printf("----------------------------------------------------------------------------%n");
    }
}
