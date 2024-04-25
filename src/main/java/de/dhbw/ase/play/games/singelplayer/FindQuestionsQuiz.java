package de.dhbw.ase.play.games.singelplayer;

import de.dhbw.ase.play.games.ExitException;
import de.dhbw.ase.repository.CouldNotAccessFileException;
import de.dhbw.ase.repository.Question;
import de.dhbw.ase.repository.QuestionRepository;
import de.dhbw.ase.repository.StatsRepository;
import de.dhbw.ase.stats.persistance.PlayerStatsFQObject;
import de.dhbw.ase.user.in.UserIn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FindQuestionsQuiz extends SingleplayerGame {

    private final QuestionRepository questionRepository;
    private final StatsRepository statsRepository;

    private PlayerStatsFQObject playerStatsFQObject;

    public FindQuestionsQuiz(UserIn sc, QuestionRepository questionRepository, StatsRepository statsRepository) {
        super(sc);
        this.questionRepository = questionRepository;
        this.statsRepository = statsRepository;
    }

    @Override
    protected void startGame() throws ExitException, CouldNotAccessFileException {
        List<Question> questionList = questionRepository.getQuestionList(15);
        int rightAnswerCount = 0;
        int wrongAnswerCount = 0;

        System.out.println();
        System.out.println("------------- Neue Runde Fragenfinder -------------");

        for (int i = 0; i < questionList.size(); i++) {
            boolean answerEvaluation = playQuestion(questionList.get(i));
            if (answerEvaluation) {
                System.out.println("Richtige Antwort!");
                rightAnswerCount++;
                continue;
            }
            System.out.println("Diese Antwort war leider Falsch.");

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

        playerStatsFQObject = new PlayerStatsFQObject(getUsername(), rightAnswerCount, wrongAnswerCount);
    }


    @Override
    protected void writeStats() throws CouldNotAccessFileException {
        List<PlayerStatsFQObject> file =
                new ArrayList<>(statsRepository.readStats(PlayerStatsFQObject::fromLine));

        int index = file.indexOf(playerStatsFQObject);
        if (index == -1) {
            file.add(playerStatsFQObject);
        } else {
            PlayerStatsFQObject oldStats = file.get(index);
            oldStats.add(playerStatsFQObject);
        }

        Collections.sort(file);
        Collections.reverse(file);

        statsRepository.writeStats(file);
    }
}
