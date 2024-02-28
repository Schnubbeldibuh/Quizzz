package menu;

public record SelectedMenu(MenuSelection menuSelection, Submenu submenu) {

    public enum MenuSelection {
        EXIT, BACK, SUBMENU, INVALID
    }
}
