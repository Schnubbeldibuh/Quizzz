package de.dhbw.ase.stats.persistance;

public class PlayerStatsFQObject extends StatsObject implements Comparable<PlayerStatsFQObject> {

    private int rightAnswers;
    private int wrongAnswers;

    public PlayerStatsFQObject(String username, int rightAnswers, int wrongAnswers) {
        super(username);
        this.rightAnswers = rightAnswers;
        this.wrongAnswers = wrongAnswers;
    }

    public static PlayerStatsFQObject fromLine(String line) {
        String[] splittedLine = line.split(";");

        return new PlayerStatsFQObject(
                splittedLine[0],
                Integer.parseInt(splittedLine[1]),
                Integer.parseInt(splittedLine[2]));
    }

    @Override
    public String toString() {

        return getUsername() +
                ";" +
                rightAnswers +
                ";" +
                wrongAnswers;
    }

    public void add(PlayerStatsFQObject playerStatsWWMObject) {
        this.wrongAnswers += playerStatsWWMObject.wrongAnswers;
        this.rightAnswers += playerStatsWWMObject.rightAnswers;
    }

    @Override
    public int compareTo(PlayerStatsFQObject o) {
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
}
