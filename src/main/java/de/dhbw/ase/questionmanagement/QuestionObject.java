package de.dhbw.ase.questionmanagement;

public class QuestionObject implements Comparable<QuestionObject> {

    private int questionIndex;
    private String question;
    private String rightAnswer;
    private String wrongAnswer1;
    private String wrongAnswer2;
    private String wrongAnswer3;

    public QuestionObject(String completeLine) {
        splitCompleteLine(completeLine);
    }

    private QuestionObject(int questionIndex,
                          String question,
                          String rightAnswer,
                          String wrongAnswer1,
                          String wrongAnswer2,
                          String wrongAnswer3) {
        this.questionIndex = questionIndex;
        this.question = question;
        this.rightAnswer = rightAnswer;
        this.wrongAnswer1 = wrongAnswer1;
        this.wrongAnswer2 = wrongAnswer2;
        this.wrongAnswer3 = wrongAnswer3;
    }

    private void splitCompleteLine(String completeLine) {
        if (completeLine == null) {
            return;
        }
        String[] splittedLine = completeLine.split(";");
        questionIndex = Integer.parseInt(splittedLine[0]);
        question = splittedLine[1];
        rightAnswer = splittedLine[2];
        wrongAnswer1 = splittedLine[3];
        wrongAnswer2 = splittedLine[4];
        wrongAnswer3 = splittedLine[5];
    }

    public int getQuestionIndex() {
        return questionIndex;
    }

    public String getQuestion() {
        return question;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public String getWrongAnswer1() {
        return wrongAnswer1;
    }

    public String getWrongAnswer2() {
        return wrongAnswer2;
    }

    public String getWrongAnswer3() {
        return wrongAnswer3;
    }

    public String getCompleteLine() {
        return questionIndex +
                ";" +
                question +
                ";" +
                rightAnswer +
                ";" +
                wrongAnswer1 +
                ";" +
                wrongAnswer2 +
                ";" +
                wrongAnswer3;
    }

    @Override
    public String toString() {
        return getCompleteLine();
    }

    @Override
    public int compareTo(QuestionObject o) {
        // Yes, it should be this way
        return Integer.compare(o.questionIndex, questionIndex);
    }


    public static final class Builder {
        private int questionIndex;
        private String question;
        private String rightAnswer;
        private String wrongAnswer1;
        private String wrongAnswer2;
        private String wrongAnswer3;

        private Builder() {
        }

        public static Builder create() {
            return new Builder();
        }

        public Builder withQuestionIndex(int questionIndex) {
            this.questionIndex = questionIndex;
            return this;
        }

        public Builder withQuestion(String question) {
            this.question = question;
            return this;
        }

        public Builder withRightAnswer(String rightAnswer) {
            this.rightAnswer = rightAnswer;
            return this;
        }

        public Builder withWrongAnswer1(String wrongAnswer1) {
            this.wrongAnswer1 = wrongAnswer1;
            return this;
        }

        public Builder withWrongAnswer2(String wrongAnswer2) {
            this.wrongAnswer2 = wrongAnswer2;
            return this;
        }

        public Builder withWrongAnswer3(String wrongAnswer3) {
            this.wrongAnswer3 = wrongAnswer3;
            return this;
        }

        public Builder fromQuestionObject(QuestionObject questionObject) {
            this.questionIndex = questionObject.getQuestionIndex();
            this.question = questionObject.getQuestion();
            this.rightAnswer = questionObject.getRightAnswer();
            this.wrongAnswer1 = questionObject.getWrongAnswer1();
            this.wrongAnswer2 = questionObject.getWrongAnswer2();
            this.wrongAnswer3 = questionObject.getWrongAnswer3();
            return this;
        }

        public QuestionObject build() {
            return new QuestionObject(
                    questionIndex,
                    question,
                    rightAnswer,
                    wrongAnswer1,
                    wrongAnswer2,
                    wrongAnswer3);
        }
    }
}
