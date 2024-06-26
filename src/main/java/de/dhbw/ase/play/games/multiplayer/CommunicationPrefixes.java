package de.dhbw.ase.play.games.multiplayer;

import java.util.Arrays;
import java.util.Optional;

public enum CommunicationPrefixes {
    USERNAME("username:"),
    DUPLIKATE_USERNAME("duplicate username"),
    SUCCESSFULLY_JOINED("successfully_joined"),
    NEXT_QUESTION("next_question:"),
    ANSWER("answer:"),
    START_GAME("start_game"),
    ROUND_FINISHED("round_finished"),
    ANSWER_EVALUATION("answer_evaluation:"),
    RIGHT_ANSWER("right_answer:"),
    STATS_TRANSFER("stats_transfer:"),
    STATS_TRANSFER_FINISHED("stats_transfer_finished"),
    GAMEMODE("gamemode:"),
    WRONG_GAMEMODE("wrong_gamemode");


    private final String string;

    CommunicationPrefixes(String string) {
        this.string = string;
    }

    public boolean checkPrefix(String input) {
        return input.startsWith(string);
    }

    public int getLength() {
        return string.length();
    }

    public static CommunicationPrefixes evaluateCase(String input) {
        Optional<CommunicationPrefixes> first = Arrays.stream(CommunicationPrefixes.values())
                .filter(e -> e.checkPrefix(input))
                .findFirst();

        if (first.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return first.get();
    }

    @Override
    public String toString() {
        return string;
    }
}
