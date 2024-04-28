package de.dhbw.ase.play.games.singelplayer;

import de.dhbw.ase.play.games.ExitException;
import de.dhbw.ase.repository.CouldNotAccessFileException;
import de.dhbw.ase.repository.QuestionRepository;
import de.dhbw.ase.repository.StatsRepository;
import de.dhbw.ase.repository.question.Question;
import de.dhbw.ase.stats.persistance.PlayerStatsSPObject;
import de.dhbw.ase.stats.persistance.PlayerStatsStoryObject;
import de.dhbw.ase.user.in.UserIn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StoryQuiz extends SingleplayerGame{

    private final QuestionRepository questionRepository;
    private final StatsRepository statsRepository;
    private PlayerStatsStoryObject playerStatsStoryObject;

    public StoryQuiz(UserIn sc, 
                     QuestionRepository questionRepository, 
                     StatsRepository statsRepository) {
        super(sc);
        this.questionRepository = questionRepository;
        this.statsRepository = statsRepository;
    }

    @Override
    protected void startGame() throws ExitException, CouldNotAccessFileException {
        List<Question> questionList = questionRepository.getQuestionList(8);
        int currentLevel = 0;

        System.out.println();
        System.out.println("------------- Neue Runde Story Modus -------------");

        for (int i = 0; i < questionList.size(); i++) {
            currentLevel = i+1;
            boolean answerEvaluation = playQuestion(questionList.get(i));

            if (answerEvaluation) {
                System.out.println("Richtige Antwort!");
                continue;
            }
            System.out.println("Falsche Antwort. Die Geschichte ist für dich hier zu Ende.");

            Question.Answer rightAnswer = questionList.get(i)
                    .getAnswerList()
                    .stream()
                    .filter(Question.Answer::isRight)
                    .findFirst()
                    .get();
            System.out.println("Die richtige Frage wäre \"" + rightAnswer.answer() + "\" gewesen.");
            break;
        }
        System.out.println("Dein erreichtes Level: " + currentLevel);

        playerStatsStoryObject =
                new PlayerStatsStoryObject(getUsername(), currentLevel, 1);
    }

    @Override
    protected void writeStats() throws CouldNotAccessFileException {
        List<PlayerStatsStoryObject> file =
                new ArrayList<>(statsRepository.readStats(PlayerStatsStoryObject::fromLine));

        int index = file.indexOf(playerStatsStoryObject);
        if (index == -1) {
            file.add(playerStatsStoryObject);
        } else {
            PlayerStatsStoryObject oldStats = file.get(index);
            oldStats.add(playerStatsStoryObject);
        }

        Collections.sort(file);
        Collections.reverse(file);

        statsRepository.writeStats(file);
    }
}
