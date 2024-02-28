package menu;

import java.util.Scanner;

public abstract class Submenu implements Startable {

    private final Scanner sc = new Scanner(System.in);

    protected abstract void showOptions();

    protected abstract SelectedMenu scanSelection(String input);

    @Override
    public SelectedMenu.MenuSelection start() {
        SelectedMenu.MenuSelection submenuSelection;
        do {
            showOptions();

            SelectedMenu selection;
            do {
                selection = scanSelection(sc.next());
            } while (selection.menuSelection() == SelectedMenu.MenuSelection.INVALID);

            if (selection.menuSelection() == SelectedMenu.MenuSelection.SUBMENU)
                // executing Submenu
                submenuSelection = selection.startable().start();
            else
                // if the user does not select a startable he exits the game or goes back to the  upper menu
                return selection.menuSelection();

        } while (submenuSelection != SelectedMenu.MenuSelection.EXIT);
        return submenuSelection;
    }
}
