package de.dhbw.ase.questionmanagement;

public class QuestionObject {

    private String completeLine;
    private String questionIndex;
    private String question;
    private String rightAnswer;
    private String wrongAnswer1;
    private String wrongAnswer2;
    private String wrongAnswer3;

    public QuestionObject(String completeLine) {
        this.completeLine = completeLine;
    }

    private void splitCompleteLine() {
        if (completeLine == null) {
            return;
        }
        String[] splittedLine = completeLine.split(";");
        questionIndex = splittedLine[0];
        question = splittedLine[1];
        rightAnswer = splittedLine[2];
        wrongAnswer1 = splittedLine[3];
        wrongAnswer2 = splittedLine[4];
        wrongAnswer3 = splittedLine[5];
        completeLine = null;
    }

    public void setQuestionIndex(String questionIndex) {
        splitCompleteLine();
        this.questionIndex = questionIndex;
    }

    public void setQuestion(String question) {
        splitCompleteLine();
        this.question = question;
    }

    public void setRightAnswer(String rightAnswer) {
        splitCompleteLine();
        this.rightAnswer = rightAnswer;
    }

    public void setWrongAnswer1(String wrongAnswer1) {
        splitCompleteLine();
        this.wrongAnswer1 = wrongAnswer1;
    }

    public void setWrongAnswer2(String wrongAnswer2) {
        splitCompleteLine();
        this.wrongAnswer2 = wrongAnswer2;
    }

    public void setWrongAnswer3(String wrongAnswer3) {
        splitCompleteLine();
        this.wrongAnswer3 = wrongAnswer3;
    }

    public String getCompleteLine() {
        if (completeLine == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(questionIndex);
            stringBuilder.append(";");
            stringBuilder.append(question);
            stringBuilder.append(";");
            stringBuilder.append(rightAnswer);
            stringBuilder.append(";");
            stringBuilder.append(wrongAnswer1);
            stringBuilder.append(";");
            stringBuilder.append(wrongAnswer2);
            stringBuilder.append(";");
            stringBuilder.append(wrongAnswer3);
            return stringBuilder.toString();
        }
        return completeLine;
    }

    @Override
    public String toString() {
        return getCompleteLine();
    }
}
