package de.dhbw.ase;

import de.dhbw.ase.play.PlayMenu;
import de.dhbw.ase.questionmanagement.QuestionMenu;
import de.dhbw.ase.stats.StatsMenu;
import de.dhbw.ase.user.in.UserIn;
import de.dhbw.ase.user.out.ConsoleOut;

import java.util.HashMap;
import java.util.Map;

public class Mainmenu extends Submenu {

    private final UserIn sc;

    public Mainmenu(UserIn sc) {
        super(sc);
        this.sc = sc;
    }

    public void startGame() {
        System.out.println("Herzlichen Willkommen bei Quizzz!");
        start();
        //System.exit(0);
        sc.stop(new ConsoleOut());
    }

    @Override
    protected Map<String, SelectedMenu> createSelectionMap() {
        Map<String, SelectedMenu> map = new HashMap<>();
        map.put("1", new SelectedMenu(new PlayMenu(getSc())));
        map.put("2", new SelectedMenu(new QuestionMenu(getSc())));
        map.put("3", new SelectedMenu(new StatsMenu(getSc())));
        map.put("4", new SelectedMenu(SelectedMenu.MenuSelection.EXIT));

        return map;
    }

    @Override
    protected void showOptions() {
        System.out.println("Hauptmenü");
        System.out.println("Wähle: ");
        System.out.println("1 - Spielen");
        System.out.println("2 - Fragenverwaltung");
        System.out.println("3 - Statistiken");
        System.out.println("4 - Exit");
    }
}
