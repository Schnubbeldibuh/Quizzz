package de.dhbw.ase.play.games.singelplayer.wwm;

import de.dhbw.ase.play.games.ExitException;
import de.dhbw.ase.play.games.singelplayer.SingleplayerGame;
import de.dhbw.ase.repository.CouldNotAccessFileException;
import de.dhbw.ase.repository.question.Question;
import de.dhbw.ase.repository.QuestionRepository;
import de.dhbw.ase.repository.StatsRepository;
import de.dhbw.ase.stats.persistance.PlayerStatsWWMObject;
import de.dhbw.ase.user.in.UserIn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WerWirdMillionaer extends SingleplayerGame {

    private final QuestionRepository easyRepo;
    private final QuestionRepository mediumRepo;
    private final QuestionRepository hardRepo;
    private final QuestionRepository veryHardRepo;
    private final QuestionRepository expertRepo;
    private final StatsRepository statsRepository;
    private PlayerStatsWWMObject playerStatsWWMObject;
    private JokerMenu jokerMenu;

    private final UserIn sc;

    public WerWirdMillionaer(UserIn sc, StatsRepository statsRepository,
                             QuestionRepository easyRepo,
                             QuestionRepository mediumRepo,
                             QuestionRepository hardRepo,
                             QuestionRepository veryHardRepo,
                             QuestionRepository expertRepo) {
        super(sc);
        this.sc = sc;
        this.easyRepo = easyRepo;
        this.mediumRepo = mediumRepo;
        this.hardRepo = hardRepo;
        this.veryHardRepo = veryHardRepo;
        this.expertRepo = expertRepo;
        this.statsRepository = statsRepository;
    }

    @Override
    protected void startGame() throws ExitException, CouldNotAccessFileException {
        List<Question> questionList = generateQuestionList();

        System.out.println();
        System.out.println("------------- Neue Runde WWM -------------");
        System.out.println();
        System.out.println("Um einen Joker zu verwenden, kann in jeder Runde <Joker> eingegeben werden.");

        for (int i = 0; i < questionList.size(); i++) {
            WWMLevels currentLevel = WWMLevels.values()[i];
            showQuestionLevel(i);
            jokerMenu.setAnswerLists(questionList.get(i).getAnswerList());
            boolean answerEvaluation = playQuestion(questionList.get(i));
            if (answerEvaluation) {
                System.out.println("Richtige Antwort!");
                System.out.println();
                System.out.println("1 - Weiterspielen");
                System.out.println("2 - Geld nehmen um die Anzeigenhauptmeisterstrafe bezahlen");

                String input;
                do {
                    input = sc.waitForNextLine(this);
                } while (!(input.equals("1") || input.equals("2")));

                if (input.equals("1")) {
                    continue;
                }

                System.out.println();
                System.out.println("Herzlichen Glückwunsch zu " + currentLevel.level + "!");
                System.out.println();
                playerStatsWWMObject = new PlayerStatsWWMObject(
                        getUsername(),
                        currentLevel.getPoints(),
                        currentLevel.getMoneyWon(),
                        currentLevel.getRightAnswers());
                return;
            }

            System.out.println("Diese Antwort war leider Falsch.");
            Question.Answer rightAnswer = questionList.get(i)
                    .getAnswerList()
                    .stream()
                    .filter(Question.Answer::isRight)
                    .findFirst()
                    .get();
            System.out.println("Die richtige Antwort wäre \"" + rightAnswer.answer() + "\" gewesen.");

            if (i >= 5) {
                System.out.println("Herzlichen Glückwunsch zu 500€!");
            } else {
                System.out.println("Sie haben leider hart reingeschissen und 0€ gewonnen.");
            }
            // Sicherheitsstufe bei 16.000 € noch möglich

            System.out.println();

            playerStatsWWMObject = new PlayerStatsWWMObject(getUsername(),
                    currentLevel.getPointsIfLost(),
                    currentLevel.getMoneyWonIfLost(),
                    currentLevel.getRightAnswers());

            return;
        }

        //Wenn die for-Schleife erfolgreich durchlaufen wurde, wurde die Millionfrage richtig beantworte.
        playerStatsWWMObject = new PlayerStatsWWMObject(getUsername(),
                WWMLevels.WON.getPoints(),
                WWMLevels.WON.getMoneyWon(),
                WWMLevels.WON.getRightAnswers());
    }

    @Override
    protected int scanUntilValidInput() throws ExitException {
        int selection;
        do {
            String input = sc.waitForNextLine(this).toLowerCase();
            if (input.equalsIgnoreCase("exit")) {
                throw new ExitException();
            }
            if (input.equalsIgnoreCase("joker")) {
                jokerMenu.start();
            }
            if (input.length() == 1) {
                selection = switch (input.charAt(0)) {
                    case 'a' -> 0;
                    case 'b' -> 1;
                    case 'c' -> 2;
                    case 'd' -> 3;
                    default -> -1;
                };
                if (selection != -1)
                    return selection;
            }
            System.out.println("Invalide Eingabe. Bitte erneut antworten.");
        } while (true);
    }

    private List<Question> generateQuestionList() throws CouldNotAccessFileException {
        List<Question> questionList = new ArrayList<>();
        questionList.addAll(easyRepo.getQuestionList(5));
        questionList.addAll(mediumRepo.getQuestionList(4));
        questionList.addAll(hardRepo.getQuestionList(3));
        questionList.addAll(veryHardRepo.getQuestionList(2));
        questionList.addAll(expertRepo.getQuestionList(1));
        return questionList;
    }


    private void showQuestionLevel(int level) {
        System.out.println();
        System.out.println("Level: " + level+1 + ", Gewinnstufe: " + WWMLevels.values()[level].getLevel());
    }

    private enum WWMLevels {
        ONE ("50€", 0, 1, 0, 50),
        TWO ("100€", 1, 2, 0, 100),
        THREE ("200€", 2, 3, 0, 200),
        FOUR ("300€", 3, 4, 0, 300),
        FIVE ("500€", 4, 5, 0, 500),
        SIX ("1.000€", 5, 10, 500, 1000),
        SEVEN ("2.000€",10, 15, 500, 2000),
        EIGHT ("4.000€", 15, 20, 500, 4000),
        NINE ("8.000€", 20, 25, 500, 8000),
        TEN ("16.000€", 25, 35, 500, 16000),
        ELEVEN ("32.000€", 35, 45, 500, 32000),
        TWELVE ("64.000€", 45, 55, 500, 64000),
        THIRTEEN ("125.000€", 55, 75, 500, 125000),
        FOURTEEN ("500.000€", 75, 95, 500, 500000),
        FIFTEEN ("1.000.000€", 95, 200, 500, 1000000),
        WON("WON", 0, 200, 0, 1000000);

        private final String level;
        private final int pointsIfLost;
        private final int points;
        private final int rightAnswers;
        private final int moneyWonIfLost;
        private final int moneyWon;


        WWMLevels(String level, int pointsIfLost, int points, int moneyWonIfLost, int moneyWon) {
            this.level = level;
            this.pointsIfLost = pointsIfLost;
            this.points = points;
            this.rightAnswers = this.ordinal();
            this.moneyWonIfLost = moneyWonIfLost;
            this.moneyWon = moneyWon;
        }

        public String getLevel() {
            return level;
        }

        public int getPoints() {
            return points;
        }

        public int getRightAnswers() {
            return rightAnswers;
        }

        public int getMoneyWonIfLost() {
            return moneyWonIfLost;
        }

        public int getMoneyWon() {
            return moneyWon;
        }

        public int getPointsIfLost() {
            return pointsIfLost;
        }
    }

    @Override
    protected void writeStats() throws CouldNotAccessFileException {
        List<PlayerStatsWWMObject> file =
                new ArrayList<>(statsRepository.readStats(PlayerStatsWWMObject::fromLine));

        int index = file.indexOf(playerStatsWWMObject);
        if (index == -1) {
            file.add(playerStatsWWMObject);
        } else {
            PlayerStatsWWMObject oldStats = file.get(index);
            oldStats.add(playerStatsWWMObject);
        }

        Collections.sort(file);
        Collections.reverse(file);

        statsRepository.writeStats(file);
    }
}
