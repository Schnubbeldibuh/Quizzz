package de.dhbw.ase;

import java.util.Map;
import java.util.Scanner;

public abstract class Submenu implements Startable {

    private final Scanner sc;

    private final Map<Character, SelectedMenu> selectionMap;

    public Submenu(Scanner sc) {
        this.sc = sc;
        selectionMap = createSelectionMap();
    }

    protected abstract Map<Character, SelectedMenu> createSelectionMap();

    protected abstract void showOptions();

    @Override
    public SelectedMenu.MenuSelection start() {
        SelectedMenu.MenuSelection submenuSelection;
        do {
            System.out.println();
            showOptions();

            SelectedMenu selection = scanUntilValidInput();

            if (selection.menuSelection() == SelectedMenu.MenuSelection.SUBMENU)
                // executing Submenu
                submenuSelection = selection.startable().start();
            else
                // if the user does not select a startable he exits the game or goes back to the  upper menu
                return selection.menuSelection();

        } while (submenuSelection != SelectedMenu.MenuSelection.EXIT);
        return submenuSelection;
    }

    private SelectedMenu scanUntilValidInput() {
        SelectedMenu selection;
        do {
            String input = sc.next();
            if (input.length() == 1) {
                selection = scanSelection(input.charAt(0));
                if (selection.menuSelection() != SelectedMenu.MenuSelection.INVALID)
                    return selection;
            }
            System.out.println("Invalide Eingabe. Bitte erneut wählen.");
        } while (true);
    }

    private SelectedMenu scanSelection(char input) {
        return selectionMap.getOrDefault(input, new SelectedMenu(SelectedMenu.MenuSelection.INVALID));
    }

    protected Scanner getSc() {
        return sc;
    }
}
