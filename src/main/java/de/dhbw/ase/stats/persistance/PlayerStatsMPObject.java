package de.dhbw.ase.stats.persistance;

public class PlayerStatsMPObject extends StatsObject implements Comparable<PlayerStatsMPObject> {

    private int rightAnswers;
    private int wrongAnswers;
    private int points;

    public PlayerStatsMPObject(String username, int rightAnswers, int wrongAnswers, int points) {
        super(username);
        this.rightAnswers = rightAnswers;
        this.wrongAnswers = wrongAnswers;
        this.points = points;
    }

    public static PlayerStatsMPObject fromLine(String line) {
        String[] splittedLine = line.split(";");

        return new PlayerStatsMPObject(
                splittedLine[0],
                Integer.parseInt(splittedLine[1]),
                Integer.parseInt(splittedLine[2]),
                Integer.parseInt(splittedLine[3]));
    }

    @Override
    public String toString() {
        return getUsername() +
                ";" +
                rightAnswers +
                ";" +
                wrongAnswers +
                ";" +
                points;
    }

    public void add(PlayerStatsMPObject playerStatsMPObject) {
        this.wrongAnswers += playerStatsMPObject.wrongAnswers;
        this.rightAnswers += playerStatsMPObject.rightAnswers;
        this.points += playerStatsMPObject.points;
    }

    @Override
    public int compareTo(PlayerStatsMPObject o) {
        if (rightAnswers < o.rightAnswers) {
            return -1;
        }
        if (rightAnswers > o.rightAnswers) {
            return 1;
        }

        if (wrongAnswers < o.wrongAnswers) {
            return 1;
        }
        if (wrongAnswers > o.wrongAnswers) {
            return -1;
        }

        return 0;
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
}