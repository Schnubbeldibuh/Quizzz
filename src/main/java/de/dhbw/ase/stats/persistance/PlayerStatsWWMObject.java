package de.dhbw.ase.stats.persistance;

public class PlayerStatsWWMObject extends StatsObject implements Comparable<PlayerStatsWWMObject> {

    private int points;
    private int money;
    private int rightAnswers;

    public PlayerStatsWWMObject(String username, int points, int money, int rightAnswers) {
        super(username);
        this.points = points;
        this.money = money;
        this.rightAnswers = rightAnswers;
    }

    public static PlayerStatsWWMObject fromLine(String line) {
        String[] splittedLine = line.split(";");

        return new PlayerStatsWWMObject(
                        splittedLine[0],
                        Integer.parseInt(splittedLine[1]),
                        Integer.parseInt(splittedLine[2]),
                        Integer.parseInt(splittedLine[3]));
    }

    @Override
    public String toString() {
        return getUsername() +
                ";" +
                points +
                ";" +
                money +
                ";" +
                rightAnswers;
    }

    public void add(PlayerStatsWWMObject playerStatsWWMObject) {
        this.money += playerStatsWWMObject.money;
        this.points += playerStatsWWMObject.points;
        this.rightAnswers += playerStatsWWMObject.rightAnswers;
    }

    @Override
    public int compareTo(PlayerStatsWWMObject o) {
        if (points < o.points) {
            return -1;
        }
        if (points > o.points) {
            return 1;
        }

        if (money < o.money) {
            return -1;
        }
        if (money > o.money) {
            return 1;
        }
        return 0;
    }

    public int getRightAnswers() {
        return rightAnswers;
    }

    public int getPoints() {
        return points;
    }

    public int getMoney() {
        return money;
    }
}
