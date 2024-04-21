package de.dhbw.ase.play.games.multiplayer.quickquiz;

public class PlayerQuick {

    private final String username;
    private int rightAnswers;
    private int wrongAnswers;
    private int fastestAnswer;

    public void increaseRightAnswer() {
        rightAnswers++;
    }

    public void increaseWrongAnswer() {
        wrongAnswers++;
    }

    public void increaseFastesAnswer() {
        fastestAnswer++;
    }


    public String getUsername() {
        return username;
    }

    public PlayerQuick(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(username);
        builder.append(";");
        builder.append(rightAnswers);
        builder.append(";");
        builder.append(wrongAnswers);
        builder.append(";");
        builder.append(fastestAnswer);

        return builder.toString();
    }
}
