package de.dhbw.ase.stats.persistance;

public class PlayerStatsSurvivalObject extends StatsObject implements Comparable<PlayerStatsSurvivalObject>{

    private int rightAnswers;
    private int wrongAnswers;
    private int averageDeathLevel;
    private int totalSurvivedGames;
    private int totalPlayedGames;

    public PlayerStatsSurvivalObject(String username,
                                     int rightAnswers,
                                     int wrongAnswers,
                                     int averageDeathLevel,
                                     int totalSurvivedGames,
                                     int totalPlayedGames) {
        super(username);
        this.rightAnswers = rightAnswers;
        this.wrongAnswers = wrongAnswers;
        this.averageDeathLevel = averageDeathLevel;
        this.totalSurvivedGames = totalSurvivedGames;
        this.totalPlayedGames = totalPlayedGames;
    }

    public static PlayerStatsSurvivalObject fromLine(String line) {
        String[] splittedLine = line.split(";");

        return new PlayerStatsSurvivalObject(
                splittedLine[0],
                Integer.parseInt(splittedLine[1]),
                Integer.parseInt(splittedLine[2]),
                Integer.parseInt(splittedLine[3]),
                Integer.parseInt(splittedLine[4]),
                Integer.parseInt(splittedLine[5]));
    }

    public void add(PlayerStatsSurvivalObject playerStatsSurvivalObject) {
        this.rightAnswers += playerStatsSurvivalObject.rightAnswers;
        this.wrongAnswers += playerStatsSurvivalObject.wrongAnswers;
        this.totalSurvivedGames += playerStatsSurvivalObject.totalSurvivedGames;

        this.averageDeathLevel =
                ((totalPlayedGames * averageDeathLevel)
                        + (playerStatsSurvivalObject.totalPlayedGames * playerStatsSurvivalObject.averageDeathLevel))
                        / (totalPlayedGames + playerStatsSurvivalObject.totalPlayedGames);

        this.totalPlayedGames += playerStatsSurvivalObject.totalPlayedGames;
    }

    @Override
    public String toString() {
        return getUsername() +
                ";" +
                rightAnswers +
                ";" +
                wrongAnswers +
                ";" +
                averageDeathLevel +
                ";" +
                totalSurvivedGames +
                ";" +
                totalPlayedGames;
    }

    @Override
    public int compareTo(PlayerStatsSurvivalObject o) {
        if (totalSurvivedGames > o.totalSurvivedGames) {
            return 1;
        }
        if (totalSurvivedGames < o.totalSurvivedGames) {
            return -1;
        }

        if (averageDeathLevel > o.averageDeathLevel) {
            return 1;
        }
        if (averageDeathLevel < o.averageDeathLevel) {
            return -1;
        }

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

    public int getAverageDeathLevel() {
        return averageDeathLevel;
    }

    public int getTotalSurvivedGames() {
        return totalSurvivedGames;
    }

    public int getTotalPlayedGames() {
        return totalPlayedGames;
    }
}
