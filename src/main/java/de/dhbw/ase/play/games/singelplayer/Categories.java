package de.dhbw.ase.play.games.singelplayer;

public enum Categories {
    GENERAL("Allgemeinwissen"),
    GEOGRAPHY("Geographie"),
    CINEMA_TV("Film und Fernsehen"),
    TECHNOLOGY_INTERNET("Technologie und Internet"),
    SPORTS("Sport"),
    FOOD("Essen und Trinken");

    private final String string;

    Categories(String string) {
        this.string = string;
    }
    public String getString() {
        return string;
    }
}