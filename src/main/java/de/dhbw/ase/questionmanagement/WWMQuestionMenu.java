package de.dhbw.ase.questionmanagement;

import de.dhbw.ase.Quizzz;
import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Submenu;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class WWMQuestionMenu extends Submenu {
    public WWMQuestionMenu(Scanner sc) {
        super(sc);
    }

    @Override
    protected Map<String, SelectedMenu> createSelectionMap() {
        Map<String, SelectedMenu> map = new HashMap<>();
        map.put("1", new SelectedMenu(new QuestionManager(getSc(), Quizzz.FILE_WWM_EASY)));
        map.put("2", new SelectedMenu(new QuestionManager(getSc(), Quizzz.FILE_WWM_MEDIUM)));
        map.put("3", new SelectedMenu(new QuestionManager(getSc(), Quizzz.FILE_WWM_HARD)));
        map.put("4", new SelectedMenu(new QuestionManager(getSc(), Quizzz.FILE_WWM_VERY_HARD)));
        map.put("5", new SelectedMenu(new QuestionManager(getSc(), Quizzz.FILE_WWM_EXPERT)));
        map.put("6", new SelectedMenu(SelectedMenu.MenuSelection.BACK));
        map.put("7", new SelectedMenu(SelectedMenu.MenuSelection.EXIT));

        return map;
    }

    @Override
    protected void showOptions() {
        System.out.println("Welchen Fragenkatalog von WWM möchtest du bearbeiten?");
        System.out.println("1 - Leicht");
        System.out.println("2 - Mittel");
        System.out.println("3 - Schwer");
        System.out.println("4 - Sehr schwer");
        System.out.println("5 - Millionenfrage");
        System.out.println("6 - Zurück");
        System.out.println("7 - Exit");
    }
}
