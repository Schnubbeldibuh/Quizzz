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

    public int getRightAnswers() {
        return rightAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public int getPoints() {
        return points;
    }

    public String getUsername() {
        return username;
    }

    public Player(String username) {
        this.username = username;
    }
}
