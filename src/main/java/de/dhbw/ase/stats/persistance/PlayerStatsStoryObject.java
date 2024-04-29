package de.dhbw.ase.stats.persistance;

public class PlayerStatsStoryObject extends StatsObject implements Comparable<PlayerStatsStoryObject>{

    private int highestAchievedLevel;
    private int totalPlayedGames;

    public PlayerStatsStoryObject(String username, int highestAchievedLevel, int totalPlayedGames) {
        super(username);
        this.highestAchievedLevel = highestAchievedLevel;
        this.totalPlayedGames = totalPlayedGames;
    }

    public static PlayerStatsStoryObject fromLine(String line) {
        String[] splittedLine = line.split(";");

        return new PlayerStatsStoryObject(
                splittedLine[0],
                Integer.parseInt(splittedLine[1]),
                Integer.parseInt(splittedLine[2]));
    }

    public void add(PlayerStatsStoryObject playerStatsStoryObject) {
        this.totalPlayedGames += playerStatsStoryObject.totalPlayedGames;
        this.highestAchievedLevel = Math.max(playerStatsStoryObject.highestAchievedLevel, this.highestAchievedLevel);
    }

    @Override
    public int compareTo(PlayerStatsStoryObject o) {
        if (highestAchievedLevel > o.highestAchievedLevel) {
            return 1;
        }
        if (highestAchievedLevel < o.highestAchievedLevel) {
            return -1;
        }

        if (totalPlayedGames > o.totalPlayedGames) {
            return 1;
        }
        if (totalPlayedGames < o.totalPlayedGames) {
            return -1;
        }

        return 0;
    }

    @Override
    public String toString() {
        return getUsername() +
                ";" +
                highestAchievedLevel +
                ";" +
                totalPlayedGames;
    }

    public int getHighestAchievedLevel() {
        return highestAchievedLevel;
    }

    public int getTotalPlayedGames() {
        return totalPlayedGames;
    }
}
