package de.dhbw.ase.stats.persistance;

public class PlayerStatsFQObject implements Comparable<PlayerStatsFQObject> {

    private String username;
    private int rightAnswers;
    private int wrongAnswers;
    private String completeLine;
    private boolean changed;

    public PlayerStatsFQObject(String username, int rightAnswers, int wrongAnswers) {
        this.username = username;
        this.rightAnswers = rightAnswers;
        this.wrongAnswers = wrongAnswers;
    }

    public PlayerStatsFQObject(String completeLine) {
        this.completeLine = completeLine;
    }

    private void splitCompleteLine() {
        if (completeLine == null || username != null) {
            return;
        }
        String[] splittedLine = completeLine.split(";");
        username = splittedLine[0];
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
        return stringBuilder.toString();
    }

    public void add(PlayerStatsFQObject playerStatsWWMObject) {
        splitCompleteLine();
        playerStatsWWMObject.splitCompleteLine();

        this.wrongAnswers += playerStatsWWMObject.wrongAnswers;
        this.rightAnswers += playerStatsWWMObject.rightAnswers;
        changed = true;
    }

    @Override
    public int compareTo(PlayerStatsFQObject o) {
        splitCompleteLine();

        if (rightAnswers < o.rightAnswers) {
            return -1;
        }
        if (rightAnswers > o.rightAnswers) {
            return 1;
        }

        if (wrongAnswers < o.wrongAnswers) {
            return -1;
        }
        if (wrongAnswers > o.wrongAnswers) {
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
        if (!(obj instanceof PlayerStatsFQObject)) {
            return false;
        }


        PlayerStatsFQObject o = (PlayerStatsFQObject) obj;
        splitCompleteLine();
        o.splitCompleteLine();

        return username.equals(o.username);
    }

}
