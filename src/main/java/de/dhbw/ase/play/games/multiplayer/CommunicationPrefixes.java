package de.dhbw.ase.play.games.multiplayer;

public enum CommunicationPrefixes {
    USERNAME("username:"),
    DUPLIKATE_USERNAME("duplicate username"),
    SUCCESSFULLY_JOINED("successfully_joined"),
    NEXT_QUESTION("next_question:"),
    ANSWER("answer:"),
    START_GAME("startgame"),
    ROUND_FINISHED("round_finished");

    private final String string;

    CommunicationPrefixes(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public boolean checkPrefix(String input) {
        return input.startsWith(string);
    }

    public int getLength() {
        return string.length();
    }
}
