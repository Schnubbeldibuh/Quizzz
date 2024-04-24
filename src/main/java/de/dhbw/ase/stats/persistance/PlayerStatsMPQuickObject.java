package de.dhbw.ase.stats.persistance;

public class PlayerStatsMPQuickObject extends StatsObject implements Comparable<PlayerStatsMPQuickObject>{

    private int rightAnswers;
    private int wrongAnswers;
    private long fastestAnswer;

    public PlayerStatsMPQuickObject(String username, int rightAnswers, int wrongAnswers, long fastestAnswer) {
        super(username);
        this.rightAnswers = rightAnswers;
        this.wrongAnswers = wrongAnswers;
        this.fastestAnswer = fastestAnswer;
    }

    public static PlayerStatsMPQuickObject fromeLine(String line) {
        String[] splittedLine = line.split(";");

        return new PlayerStatsMPQuickObject(
                splittedLine[0],
                Integer.parseInt(splittedLine[1]),
                Integer.parseInt(splittedLine[2]),
                Long.parseLong(splittedLine[3]));
    }

    @Override
    public String toString() {
        return getUsername() +
                ";" +
                rightAnswers +
                ";" +
                wrongAnswers +
                ";" +
                fastestAnswer;
    }

    public void add(PlayerStatsMPQuickObject playerStatsMPQuickObject) {
        this.wrongAnswers += playerStatsMPQuickObject.wrongAnswers;
        this.rightAnswers += playerStatsMPQuickObject.rightAnswers;
        this.fastestAnswer += playerStatsMPQuickObject.fastestAnswer;
    }

    @Override
    public int compareTo(PlayerStatsMPQuickObject o) {
        if (rightAnswers < o.rightAnswers) {
            return -1;
        }
        if (rightAnswers > o.rightAnswers) {
            return 1;
        }

        if (fastestAnswer < o.fastestAnswer) {
            return -1;
        }
        if (fastestAnswer > o.fastestAnswer) {
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

    public long getFastestAnswer() {
        return fastestAnswer;
    }
}