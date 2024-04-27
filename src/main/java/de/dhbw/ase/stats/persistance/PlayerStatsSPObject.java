package de.dhbw.ase.stats.persistance;

public class PlayerStatsSPObject extends StatsObject implements Comparable<PlayerStatsSPObject>{

    private int rightAnswers;
    private int wrongAnswers;
    private long averageAnswerTime;

    public PlayerStatsSPObject(String username, int rightAnswers, int wrongAnswers, long averageAnswerTime) {
        super(username);
        this.rightAnswers = rightAnswers;
        this.wrongAnswers = wrongAnswers;
        this.averageAnswerTime = averageAnswerTime;
    }

    public static PlayerStatsSPObject fromLine(String line) {
        String[] splittedLine = line.split(";");

        return new PlayerStatsSPObject(
                splittedLine[0],
                Integer.parseInt(splittedLine[1]),
                Integer.parseInt(splittedLine[2]),
                Integer.parseInt(splittedLine[3]));
    }

    public void add(PlayerStatsSPObject playerStatsSPObject) {
        int totalQuestionsBefore = wrongAnswers + rightAnswers;
        int totalQuestionsAfter = playerStatsSPObject.wrongAnswers + playerStatsSPObject.rightAnswers;
        this.averageAnswerTime =
                ((totalQuestionsBefore * averageAnswerTime)
                        + (totalQuestionsAfter * playerStatsSPObject.averageAnswerTime))
                / (totalQuestionsBefore + totalQuestionsAfter);

        this.wrongAnswers += playerStatsSPObject.wrongAnswers;
        this.rightAnswers += playerStatsSPObject.rightAnswers;
    }

    @Override
    public int compareTo(PlayerStatsSPObject o) {
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
    public String toString() {
        return getUsername() +
                ";" +
                rightAnswers +
                ";" +
                wrongAnswers +
                ";" +
                averageAnswerTime;
    }

    public int getRightAnswers() {
        return rightAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public long getAverageAnswerTime() {
        return averageAnswerTime;
    }
}