package de.dhbw.ase.repository.question;

import java.util.*;

public class Question {

    private final SortedSet<Answer> answerList;

    private final String question;

    private Question(SortedSet<Answer> answerList, String question) {
        this.answerList = Collections.unmodifiableSortedSet(answerList);
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public SortedSet<Answer> getAnswerList() {
        return answerList;
    }

    public String getCompleteLine() {
        StringBuilder builder = new StringBuilder();

        builder.append(question);
        builder.append(";");

        String rightAnswer = answerList
                .stream()
                .filter(a -> a.isRight)
                .findFirst()
                .get()
                .toString();
        builder.append(rightAnswer);

        answerList
                .stream()
                .filter(a -> !a.isRight)
                .map(Record::toString)
                .forEach(a -> {
                    builder.append(";");
                    builder.append(a);
                });

        return builder.toString();
    }

    @Override
    public String toString() {
        return getCompleteLine();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Question that = (Question) o;
        if (!that.question.equals(question)) {
            return false;
        }

        boolean rightAnswer = that.answerList
                .stream()
                .map(answerList::contains)
                .anyMatch(a -> !a);
        return !rightAnswer;
    }

    @Override
    public int hashCode() {
        Iterator<Answer> iterator = answerList.iterator();
        return Objects.hash(
                question,
                iterator.next(),
                iterator.next(),
                iterator.next(),
                iterator.next()
        );
    }

    public record Answer(String answer, boolean isRight) implements Comparable<Answer> {

        @Override
        public String toString() {
            return answer;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Answer answer1 = (Answer) o;
            return Objects.equals(answer, answer1.answer);
        }

        @Override
        public int hashCode() {
            return Objects.hash(answer);
        }



        @Override
        public int compareTo(Answer o) {
            if (isRight && !o.isRight) {
                return 1;
            }
            if (!isRight && o.isRight) {
                return -1;
            }

            return answer.compareTo(o.answer);
        }
    }

    public static final class Builder {
        private String question;
        private final SortedSet<Answer> answerList = new TreeSet<>();

        private Builder() {
        }

        public static Builder create() {
            return new Builder();
        }

        public static Question createFakeQuestion() {
            Question fakeQuestion = null;
            try {
                fakeQuestion = Question.Builder.create().withQuestion(
                                "Es gab nicht genug Fragen zu diesem Spielmodus. " +
                                        "Also wähle die Richtige Antwort:")
                        .withRightAnswer("Richtig")
                        .withWrongAnswer("Falsch1")
                        .withWrongAnswer("Falsch2")
                        .withWrongAnswer("Falsch3")
                        .build();
            } catch (AnswerProblemException ignored) {
            }
            return fakeQuestion;
        }

        public Builder withQuestion(String question) {
            this.question = question;
            return this;
        }

        public Builder withRightAnswer(String rightAnswer) throws AnswerProblemException {
            Answer newAnswer = new Answer(rightAnswer, true);
            boolean successfulAdded = answerList.add(newAnswer);
            if (!successfulAdded) {
                throw new AnswerProblemException("Answer already exists");
            }
            return this;
        }

        public Builder withWrongAnswer(String wrongAnswer) throws AnswerProblemException {
            Answer newAnswer = new Answer(wrongAnswer, false);
            boolean successfulAdded = answerList.add(newAnswer);
            if (!successfulAdded) {
                throw new AnswerProblemException("Answer already exists");
            }
            return this;
        }

        public Builder fromCompleteLine(String completeLine) throws AnswerProblemException {
            String[] splittedLine = completeLine.split(";");
            question = splittedLine[1];
            answerList.add(new Answer(splittedLine[2], true));
            answerList.add(new Answer(splittedLine[3], false));
            answerList.add(new Answer(splittedLine[4], false));
            answerList.add(new Answer(splittedLine[5], false));

            if (answerList.size() != 4) {
                throw new AnswerProblemException("Answer already exists");
            }
            return this;
        }

        public Question build() throws AnswerProblemException {
            if (answerList.size() != 4) {
                throw new AnswerProblemException("there ar more or less than 4 answers");
            }

            return new Question(
                    answerList,
                    question);
        }
    }
}
