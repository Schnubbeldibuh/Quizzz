package menu;

public record SelectedMenu(MenuSelection menuSelection, Startable startable) {

    public enum MenuSelection {
        EXIT, BACK, SUBMENU, INVALID
    }
}
