package de.dhbw.ase.play.games.singelplayer;

import de.dhbw.ase.play.games.ExitException;
import de.dhbw.ase.play.games.repository.CouldNotAccessFileException;
import de.dhbw.ase.play.games.repository.Question;
import de.dhbw.ase.play.games.repository.QuestionRepository;
import de.dhbw.ase.play.games.repository.StatsRepository;
import de.dhbw.ase.stats.persistance.PlayerStatsSPObject;
import de.dhbw.ase.user.in.UserIn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SPQuiz extends SingleplayerGame {

    private final Categories category;
    private final QuestionRepository questionRepository;
    private final StatsRepository statsRepository;
    private PlayerStatsSPObject playerStatsSPObject;

    public SPQuiz(UserIn sc, Categories category,
                  QuestionRepository questionRepository,
                  StatsRepository statsRepository) {
        super(sc);
        this.category = category;
        this.questionRepository = questionRepository;
        this.statsRepository = statsRepository;
    }

    @Override
    protected void startGame() throws ExitException, CouldNotAccessFileException {
        List<Question> questionList = questionRepository.getQuestionList(8);
        int rightAnswerCount = 0;
        int wrongAnswerCount = 0;
        long averageAnswerTime;
        long totalAnswerTime = 0;

        System.out.println();
        System.out.println("------------- Neue Runde Singleplayer " + category.getString() + " -------------");

        for (int i = 0; i < questionList.size(); i++) {
            long beforeQuestion = System.nanoTime();
            boolean answerEvaluation = playQuestion(questionList.get(i));
            long afterQuestion = System.nanoTime();

            if (answerEvaluation) {
                System.out.println("Richtige Antwort!");
                rightAnswerCount++;
                continue;
            }
            System.out.println("Diese Antwort war leider Falsch.");
            totalAnswerTime += Math.abs(afterQuestion - beforeQuestion);

            Question.Answer rightAnswer = questionList.get(i).getAnswerList()
                    .stream()
                    .filter(Question.Answer::isRight)
                    .findFirst()
                    .get();
            System.out.println("Die richtige Frage wäre \"" + rightAnswer.answer() + "\" gewesen."
            );

            wrongAnswerCount++;
        }
        System.out.println("Du hattest ingesamt " + rightAnswerCount + " richtige Antworten!");
        averageAnswerTime = TimeUnit.NANOSECONDS.toMillis(totalAnswerTime / (questionList.size()));
        System.out.println("Du hast eine durchschnittliche Antwortzeit von " + averageAnswerTime + " Millisekunden.");

        playerStatsSPObject =
                new PlayerStatsSPObject(getUsername(), rightAnswerCount, wrongAnswerCount, averageAnswerTime);
    }

    @Override
    protected void writeStats() throws CouldNotAccessFileException {
        List<PlayerStatsSPObject> file =
                new ArrayList<>(statsRepository.readStats(PlayerStatsSPObject::fromLine));

        int index = file.indexOf(playerStatsSPObject);
        if (index == -1) {
            file.add(playerStatsSPObject);
        } else {
            PlayerStatsSPObject oldStats = file.get(index);
            oldStats.add(playerStatsSPObject);
        }

        Collections.sort(file);
        Collections.reverse(file);

        statsRepository.writeStats(file);
    }
}
