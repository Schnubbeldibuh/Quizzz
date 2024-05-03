package de.dhbw.ase.play.games.singelplayer;

import de.dhbw.ase.play.games.ExitException;
import de.dhbw.ase.play.games.multiplayer.core.HostDisconnectedException;
import de.dhbw.ase.repository.CouldNotAccessFileException;
import de.dhbw.ase.repository.QuestionRepository;
import de.dhbw.ase.repository.StatsRepository;
import de.dhbw.ase.repository.question.Question;
import de.dhbw.ase.stats.persistance.PlayerStatsSPObject;
import de.dhbw.ase.stats.persistance.PlayerStatsSurvivalObject;
import de.dhbw.ase.user.in.UserIn;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class SurvivalQuiz extends SingleplayerGame{

    private final QuestionRepository questionRepository;
    private final StatsRepository statsRepository;
    private ExecutorService listeningExecutor = Executors.newCachedThreadPool();
    private UserIn sc;
    private PlayerStatsSurvivalObject playerStatsSurvivalObject;

    public SurvivalQuiz(UserIn sc,
                        QuestionRepository questionRepository,
                        StatsRepository statsRepository) {
        super(sc);
        this.sc = sc;
        this.questionRepository = questionRepository;
        this.statsRepository = statsRepository;
    }

    @Override
    protected void startGame() throws ExitException, CouldNotAccessFileException {
        List<Question> questionList = questionRepository.getQuestionList(10);

        int rightAnswerCount = 0;
        int wrongAnswerCount = 0;
        int averageDeathLevel = 0;


        System.out.println();
        System.out.println("------------- Neue Runde Survival Modus -------------");

        for (int i = 0; i < questionList.size(); i++) {
            averageDeathLevel = i+1;
            List<Question.Answer> answerList = showQuestion(questionList.get(i));

            CompletionService<String> threadPool = new ExecutorCompletionService<>(listeningExecutor);

            Future<String> userThread = threadPool.submit(this::listenToUser);
            Future<String> timerThread = threadPool.submit(this::runTimer);


            String result = null;
            do {
                try {
                    Future<String> currentThread = threadPool.take();
                    if (currentThread == timerThread) {
                        break;
                    }

                    if (currentThread != userThread) {
                        continue;
                    }
                    result = currentThread.get();
                    break;
                } catch (InterruptedException e) {
                    System.out.println("Ein unerwarteter Fehler ist aufgetreten.");
                    listeningExecutor.shutdownNow();
                    throw new ExitException();

                } catch (ExecutionException e) {
                    if (e.getCause().getClass() == ExitException.class) {
                        listeningExecutor.shutdownNow();
                        throw new ExitException();
                    }
                    System.out.println("Ein unerwarteter Fehler ist aufgetreten.");
                    throw new ExitException();
                }
            } while (true);

            if (result == null) {
                System.out.println("Du warst leider zu langsam. Du bist tot.");
                break;
            }

            int answerIndex = mapAnswerToInt(result);
            boolean answerEvaluation = answerList.get(answerIndex).isRight();

            if (answerEvaluation) {
                rightAnswerCount++;
                continue;
            }
            System.out.println("Diese Antwort war leider Falsch.");

            Question.Answer rightAnswer = questionList.get(i)
                    .getAnswerList()
                    .stream()
                    .filter(Question.Answer::isRight)
                    .findFirst()
                    .get();
            System.out.println("Die richtige Frage wäre \"" + rightAnswer.answer() + "\" gewesen."
            );

            wrongAnswerCount++;
            if (wrongAnswerCount > 3) {
                System.out.println("Du bist tot.");
                break;
            }
        }
        System.out.println("Du hattest ingesamt " + rightAnswerCount + " richtige Antworten!");
        boolean survived = (averageDeathLevel == questionList.size());

        playerStatsSurvivalObject =
                new PlayerStatsSurvivalObject(getUsername(),
                        rightAnswerCount,
                        wrongAnswerCount,
                        averageDeathLevel,
                        survived ? 1:0,
                        1);
    }

    private int mapAnswerToInt(String answer) {
         return switch (answer.charAt(0)) {
            case 'a' -> 0;
            case 'b' -> 1;
            case 'c' -> 2;
            case 'd' -> 3;
            default -> -1;
        };
    }
    private String runTimer() throws InterruptedException {
        Thread.sleep(3000);
        return null;
    }

    private String listenToUser() throws ExitException {

        do {
            String input = sc.waitForNextLine(this);

            if (input.equals("exit")) {
                throw new ExitException();
            }

            if (!(input.equalsIgnoreCase("a")
                    || input.equalsIgnoreCase("b")
                    || input.equalsIgnoreCase("c")
                    || input.equalsIgnoreCase("d"))) {
                System.out.println("Ungültige Eingabe");
                continue;
            }
            return input;
        } while (true);
    }

    @Override
    protected void writeStats() throws CouldNotAccessFileException {
        List<PlayerStatsSurvivalObject> file =
                new ArrayList<>(statsRepository.readStats(PlayerStatsSurvivalObject::fromLine));

        int index = file.indexOf(playerStatsSurvivalObject);
        if (index == -1) {
            file.add(playerStatsSurvivalObject);
        } else {
            PlayerStatsSurvivalObject oldStats = file.get(index);
            oldStats.add(playerStatsSurvivalObject);
        }

        Collections.sort(file);
        Collections.reverse(file);

        statsRepository.writeStats(file);
    }
}