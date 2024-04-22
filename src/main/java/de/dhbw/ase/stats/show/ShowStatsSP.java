package de.dhbw.ase.stats.show;

import de.dhbw.ase.stats.persistance.PlayerStatsFQObject;
import de.dhbw.ase.stats.persistance.PlayerStatsSPObject;

public class ShowStatsSP extends ShowStats{
    public ShowStatsSP(String filename) {
        super(filename);
    }

    @Override
    protected void displayStats(String string) {
        PlayerStatsSPObject playerStatsSPObject = new PlayerStatsSPObject(string);
        System.out.printf("| %-14s | %-13s | %-14s | %-19s |%n",
                playerStatsSPObject.getUsername(),
                playerStatsSPObject.getRightAnswers(),
                playerStatsSPObject.getWrongAnswers(),
                playerStatsSPObject.getAverageAnswerTime());
    }

    @Override
    protected void displayFittingHeader() {
        System.out.printf("----------------------------------------------------------------------------%n");
        System.out.printf("| %-14s | %-13s | %-14s | %-19s |%n",
                "USERNAME", "RIGHT ANSWERS", "WRONG ANSWERS", "AVERAGE ANSWER TIME");
        System.out.printf("----------------------------------------------------------------------------%n");
    }
}
