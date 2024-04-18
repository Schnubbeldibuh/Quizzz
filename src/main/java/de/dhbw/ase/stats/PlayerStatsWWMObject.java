package de.dhbw.ase.stats;

public class PlayerStatsWWMObject implements Comparable<PlayerStatsWWMObject> {

    private String username;
    private int points;
    private int money;
    private String completeLine;

    public PlayerStatsWWMObject(String username, int points, int money) {
        this.username = username;
        this.points = points;
        this.money = money;
    }

    public PlayerStatsWWMObject(String completeLine) {
        this.completeLine = completeLine;
    }

    private void splitCompleteLine() {
        if (completeLine == null) {
            return;
        }
        String[] splittedLine = completeLine.split(";");
        username = splittedLine[0];
        points = Integer.parseInt(splittedLine[1]);
        money = Integer.parseInt(splittedLine[2]);
        completeLine = null;
    }

    public String getCompleteLine() {
        if (completeLine != null) {
            return completeLine;
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(username);
        stringBuilder.append(";");
        stringBuilder.append(points);
        stringBuilder.append(";");
        stringBuilder.append(money);
        return stringBuilder.toString();
    }

    public void add(PlayerStatsWWMObject playerStatsWWMObject) {
        this.money += playerStatsWWMObject.money;
        this.points += playerStatsWWMObject.points;
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

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PlayerStatsWWMObject)) {
            return false;
        }

        PlayerStatsWWMObject o = (PlayerStatsWWMObject) obj;
        return username.equals(o.username);
    }

}
