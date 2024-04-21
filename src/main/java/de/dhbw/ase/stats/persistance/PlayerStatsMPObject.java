package de.dhbw.ase.stats.persistance;

public class PlayerStatsMPObject implements Comparable<PlayerStatsMPObject> {

    private String username;
    private int rightAnswers;
    private int wrongAnswers;
    private String completeLine;
    private boolean changed;
    private int points;

    public PlayerStatsMPObject(String username, int rightAnswers, int wrongAnswers, int points) {
        this.username = username;
        this.rightAnswers = rightAnswers;
        this.wrongAnswers = wrongAnswers;
        this.points = points;
    }

    public PlayerStatsMPObject(String completeLine) {
        this.completeLine = completeLine;
    }

    private void splitCompleteLine() {
        if (completeLine == null || username != null) {
            return;
        }
        String[] splittedLine = completeLine.split(";");
        username = splittedLine[0];
        points = Integer.parseInt(splittedLine[3]);
        wrongAnswers = Integer.parseInt(splittedLine[2]);
        rightAnswers = Integer.parseInt(splittedLine[1]);
    }

    public String getCompleteLine() {
        if (!changed && completeLine != null) {
            return completeLine;
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(username);
        stringBuilder.append(";");
        stringBuilder.append(rightAnswers);
        stringBuilder.append(";");
        stringBuilder.append(wrongAnswers);
        stringBuilder.append(";");
        stringBuilder.append(points);
        return stringBuilder.toString();
    }

    public void add(PlayerStatsMPObject playerStatsMPObject) {
        splitCompleteLine();
        playerStatsMPObject.splitCompleteLine();

        this.wrongAnswers += playerStatsMPObject.wrongAnswers;
        this.rightAnswers += playerStatsMPObject.rightAnswers;
        this.points += playerStatsMPObject.points;
        changed = true;
    }

    @Override
    public int compareTo(PlayerStatsMPObject o) {
        splitCompleteLine();

        if (points < o.points) {
            return -1;
        }
        if (points > o.points) {
            return 1;
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

    @Override
    public int hashCode() {
        splitCompleteLine();
        return username.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PlayerStatsMPObject)) {
            return false;
        }

        PlayerStatsMPObject o = (PlayerStatsMPObject) obj;
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

    public int getWrongAnswers() {
        splitCompleteLine();
        return wrongAnswers;
    }

    public int getPoints() {
        splitCompleteLine();
        return points;
    }
}