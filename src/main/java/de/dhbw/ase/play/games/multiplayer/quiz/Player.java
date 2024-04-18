package de.dhbw.ase.play.games.multiplayer.quiz;

public class Player {

    private final String username;
    private int rightAnswers;
    private int wrongAnswers;
    private int points;

    public void increaseRightAnswer() {
        rightAnswers++;
    }

    public void increaseWrongAnswer() {
        wrongAnswers++;
    }
    public void increasePoints(int value) {
        points += value;
    }


    public String getUsername() {
        return username;
    }

    public Player(String username) {
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
        builder.append(points);

        return builder.toString();
    }
}
