package de.dhbw.ase.play.games.singelplayer;

import de.dhbw.ase.Quizzz;
import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Submenu;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SingleplayerMenu extends Submenu {

    public SingleplayerMenu(Scanner sc) {super(sc);}

    @Override
    protected Map<String, SelectedMenu> createSelectionMap() {
        Map<String, SelectedMenu> map = new HashMap<>();
        map.put("1", new SelectedMenu(new WerWirdMillionaer(getSc(), Quizzz.FILE_STATS_WWM)));
        map.put("2", new SelectedMenu(new FindQuestionsQuiz(getSc(), Quizzz.FILE_STATS_FQ)));
        map.put("3", new SelectedMenu(new SingleplayerCategoryMenu(getSc())));
        map.put("4", new SelectedMenu(SelectedMenu.MenuSelection.BACK));
        map.put("5", new SelectedMenu(SelectedMenu.MenuSelection.EXIT));

        return map;
    }

    @Override
    protected void showOptions() {
        System.out.println("Welchen Singleplayer-Modus möchtest du spielen?");
        System.out.println("1 - Wer wird Millionär?");
        System.out.println("2 - Fragenfinder");
        System.out.println("3 - Kategorien");
        System.out.println("4 - Zurück");
        System.out.println("5 - Exit");
    }
}
