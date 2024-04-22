package de.dhbw.ase.stats.persistance;

public class PlayerStatsSPObject implements Comparable<PlayerStatsSPObject>{

    private String username;
    private int rightAnswers;
    private int wrongAnswers;
    private long averageAnswerTime;
    private String category;
    private String completeLine;
    private boolean changed;

    public PlayerStatsSPObject(String username, int rightAnswers, int wrongAnswers, String category) {
        this.username = username;
        this.rightAnswers = rightAnswers;
        this.wrongAnswers = wrongAnswers;
        this.category = category;
    }

    public PlayerStatsSPObject(String completeLine) {
        this.completeLine = completeLine;
    }

    private void splitCompleteLine() {
        if (completeLine == null || username != null) {
            return;
        }
        String[] splittedLine = completeLine.split(";");
        username = splittedLine[0];
        averageAnswerTime = Long.parseLong(splittedLine[3]);
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
        stringBuilder.append(averageAnswerTime);
        return stringBuilder.toString();
    }

    public void add(PlayerStatsSPObject playerStatsSPObject) {
        splitCompleteLine();
        playerStatsSPObject.splitCompleteLine();
        int totalQuestionsBefore = wrongAnswers + rightAnswers;
        int totalQuestionsAfter = playerStatsSPObject.wrongAnswers + playerStatsSPObject.rightAnswers;

        this.wrongAnswers += playerStatsSPObject.wrongAnswers;
        this.rightAnswers += playerStatsSPObject.rightAnswers;
        this.averageAnswerTime =
                ((totalQuestionsBefore * averageAnswerTime)
                        + (totalQuestionsAfter * playerStatsSPObject.averageAnswerTime))
                / (totalQuestionsBefore + totalQuestionsAfter);
        changed = true;
    }

    @Override
    public int compareTo(PlayerStatsSPObject o) {
        splitCompleteLine();

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

        if (averageAnswerTime < o.averageAnswerTime) {
            return 1;
        }
        if (averageAnswerTime > o.averageAnswerTime) {
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
        if (!(obj instanceof PlayerStatsSPObject)) {
            return false;
        }

        PlayerStatsSPObject o = (PlayerStatsSPObject) obj;
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

    public long getAverageAnswerTime() {
        splitCompleteLine();
        return averageAnswerTime;
    }
}