package de.dhbw.ase;

public record SelectedMenu(
        MenuSelection menuSelection,
        Startable startable
) {

    public SelectedMenu(MenuSelection menuSelection) {
        this(menuSelection, null);
    }

    public SelectedMenu(Startable startable) {
        this(MenuSelection.SUBMENU, startable);
    }

    public enum MenuSelection {
        EXIT, BACK, SUBMENU, INVALID
    }
}
