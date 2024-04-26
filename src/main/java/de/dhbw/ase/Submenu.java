package de.dhbw.ase;

import de.dhbw.ase.user.in.UserIn;

import java.util.HashMap;
import java.util.Map;

public abstract class Submenu implements Startable {

    private final UserIn sc;

    private final Map<String, SelectedMenu> selectionMap = new HashMap<>();

    public Submenu(UserIn sc) {
        this.sc = sc;
    }

    protected abstract Map<String, SelectedMenu> generateSelectionMap();

    protected abstract void showOptions();

    @Override
    public SelectedMenu.MenuSelection start() {
        fillSelectionMap();

        SelectedMenu.MenuSelection submenuSelection;
        do {
            System.out.println();
            showOptions();

            SelectedMenu selection = scanUntilValidInput();

            if (selection.menuSelection() == SelectedMenu.MenuSelection.SUBMENU) {
                // executing Submenu
                submenuSelection = selection.startable().start();
            } else {
                // if the user does not select a startable he exits the game or goes back to the  upper menu
                return selection.menuSelection();
            }
        } while (submenuSelection != SelectedMenu.MenuSelection.EXIT);
        return submenuSelection;
    }

    protected SelectedMenu.MenuSelection startOnlyOnes() {
        fillSelectionMap();

        SelectedMenu.MenuSelection submenuSelection;
        System.out.println();
        showOptions();

        SelectedMenu selection = scanUntilValidInput();

        if (selection.menuSelection() == SelectedMenu.MenuSelection.SUBMENU) {
            // executing Submenu
            submenuSelection = selection.startable().start();
        } else {
            // if the user does not select a startable he exits the game or goes back to the  upper menu
            return selection.menuSelection();
        }
        return submenuSelection;
    }

    private void fillSelectionMap() {
        if (!selectionMap.isEmpty()) {
            return;
        }
        selectionMap.putAll(generateSelectionMap());
    }

    private SelectedMenu scanUntilValidInput() {
        SelectedMenu selection;
        do {
            String input;
            input = sc.waitForNextLine(this);

            selection = scanSelection(input);
            if (selection.menuSelection() != SelectedMenu.MenuSelection.INVALID)
                return selection;
            System.out.println("Invalide Eingabe. Bitte erneut wählen.");
        } while (true);
    }

    private SelectedMenu scanSelection(String input) {
        return selectionMap.getOrDefault(input, new SelectedMenu(SelectedMenu.MenuSelection.INVALID));
    }
}
