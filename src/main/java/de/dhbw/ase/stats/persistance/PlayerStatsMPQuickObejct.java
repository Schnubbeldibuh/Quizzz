package de.dhbw.ase.stats.persistance;

public class PlayerStatsMPQuickObejct implements Comparable<PlayerStatsMPQuickObejct>{

    private String username;
    private int rightAnswers;
    private int wrongAnswers;
    private String completeLine;
    private boolean changed;
    private int fastestAnswer;

    public PlayerStatsMPQuickObejct(String username, int rightAnswers, int wrongAnswers, int fastestAnswer) {
        this.username = username;
        this.rightAnswers = rightAnswers;
        this.wrongAnswers = wrongAnswers;
        this.fastestAnswer = fastestAnswer;
    }

    public PlayerStatsMPQuickObejct(String completeLine) {
        this.completeLine = completeLine;
    }

    private void splitCompleteLine() {
        if (completeLine == null || username != null) {
            return;
        }
        String[] splittedLine = completeLine.split(";");
        username = splittedLine[0];
        fastestAnswer = Integer.parseInt(splittedLine[3]);
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
        stringBuilder.append(fastestAnswer);
        return stringBuilder.toString();
    }

    public void add(PlayerStatsMPQuickObejct playerStatsMPQuickObejct) {
        splitCompleteLine();
        playerStatsMPQuickObejct.splitCompleteLine();

        this.wrongAnswers += playerStatsMPQuickObejct.wrongAnswers;
        this.rightAnswers += playerStatsMPQuickObejct.rightAnswers;
        this.fastestAnswer += playerStatsMPQuickObejct.fastestAnswer;
        changed = true;
    }

    @Override
    public int compareTo(PlayerStatsMPQuickObejct o) {
        splitCompleteLine();

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

    @Override
    public int hashCode() {
        splitCompleteLine();
        return username.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PlayerStatsMPQuickObejct)) {
            return false;
        }

        PlayerStatsMPQuickObejct o = (PlayerStatsMPQuickObejct) obj;
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

    public int getFastestAnswer() {
        splitCompleteLine();
        return fastestAnswer;
    }
}