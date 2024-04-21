package de.dhbw.ase.stats.persistance;

public class PlayerStatsWWMObject implements Comparable<PlayerStatsWWMObject> {

    private String username;
    private int points;
    private int money;
    private int rightAnswers;
    private String completeLine;
    private boolean changed;

    public PlayerStatsWWMObject(String username, int points, int money, int rightAnswers) {
        this.username = username;
        this.points = points;
        this.money = money;
        this.rightAnswers = rightAnswers;
    }

    public PlayerStatsWWMObject(String completeLine) {
        this.completeLine = completeLine;
    }

    private void splitCompleteLine() {
        if (completeLine == null || username != null) {
            return;
        }
        String[] splittedLine = completeLine.split(";");
        username = splittedLine[0];
        points = Integer.parseInt(splittedLine[1]);
        money = Integer.parseInt(splittedLine[2]);
        rightAnswers = Integer.parseInt(splittedLine[3]);
    }

    public String getCompleteLine() {
        if (!changed && completeLine != null) {
            return completeLine;
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(username);
        stringBuilder.append(";");
        stringBuilder.append(points);
        stringBuilder.append(";");
        stringBuilder.append(money);
        stringBuilder.append(";");
        stringBuilder.append(rightAnswers);
        return stringBuilder.toString();
    }

    public void add(PlayerStatsWWMObject playerStatsWWMObject) {
        splitCompleteLine();
        playerStatsWWMObject.splitCompleteLine();

        this.money += playerStatsWWMObject.money;
        this.points += playerStatsWWMObject.points;
        this.rightAnswers += playerStatsWWMObject.rightAnswers;
        changed = true;
    }

    @Override
    public int compareTo(PlayerStatsWWMObject o) {
        splitCompleteLine();

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

    @Override
    public int hashCode() {
        splitCompleteLine();
        return username.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PlayerStatsWWMObject)) {
            return false;
        }


        PlayerStatsWWMObject o = (PlayerStatsWWMObject) obj;
        splitCompleteLine();
        o.splitCompleteLine();

        return username.equals(o.username);
    }

    public String getUsername() {
        splitCompleteLine();
        return username;
    }

    public int getRightAnswers() {
        splitCompleteLine();
        return rightAnswers;
    }

    public int getPoints() {
        splitCompleteLine();
        return points;
    }

    public int getMoney() {
        return money;
    }
}
