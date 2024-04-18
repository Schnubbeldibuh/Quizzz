package de.dhbw.ase.play.games.singelplayer;

import de.dhbw.ase.play.games.reader.Question;
import de.dhbw.ase.play.games.reader.WWMReader;
import de.dhbw.ase.stats.persistance.PlayerStatsWWMObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class WerWirdMillionaer extends SingeplayerGame {

    private PlayerStatsWWMObject playerStatsWWMObject;
    private final String statsFilesPath;

    private final Scanner sc;

    public WerWirdMillionaer(Scanner sc, String statsFilesPath) {
        super(sc);
        this.sc = sc;
        this.statsFilesPath = statsFilesPath;
    }

    @Override
    protected void startGame() {
        /* Man könnte auch noch eine Version machen bei der die Antworten nicht mit A, B, C, D angegebenen werden
           sondern mit W, A, S, D. Für einfacherer Bedienbarkeit */
        WWMReader wwmReader = new WWMReader();
        List<Question> questionList = wwmReader.getQuestionList();

        System.out.println();
        System.out.println("------------- Neue Runde WWM -------------");

        for (int i = 0; i < questionList.size(); i++) {
            WWMLevels currentLevel = WWMLevels.values()[i];
            showQuestionLevel(i+1);
            boolean answerEvaluation = playQuestion(questionList.get(i));
            if (answerEvaluation) {
                System.out.println("Richtige Antwort!");
                System.out.println();
                System.out.println("1 - Weiterspielen");
                System.out.println("2 - Geld nehmen um die Anzeigenhauptmeisterstrafe bezahlen");

                String input;
                do {
                    input = sc.next();
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
                writeStats();
                return;
            }

            System.out.println("Diese Antwort war leider Falsch.");
            Question.Answer rightAnswer = questionList.get(i).getAnswerList()
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
    protected String getStatsFilesPath() {
        return statsFilesPath;
    }

    private void showQuestionLevel(int level) {
        System.out.println();
        System.out.println("Level: " + level + ", Gewinnstufe: " + WWMLevels.values()[level].getLevel());
    }

    private enum WWMLevels {
        ONE ("50€", 0, 1, 0, 0),
        TWO ("100€", 1, 2, 0, 50),
        THREE ("200€", 2, 3, 0, 100),
        FOUR ("300€", 3, 4, 0, 200),
        FIVE ("500€", 4, 5, 0, 300),
        SIX ("1.000€", 5, 10, 500, 500),
        SEVEN ("2.000€",10, 15, 500, 1000),
        EIGHT ("4.000€", 15, 20, 500, 2000),
        NINE ("8.000€", 20, 25, 500, 4000),
        TEN ("16.000€", 25, 35, 500, 8000),
        ELEVEN ("32.000€", 35, 45, 500, 16000),
        TWELVE ("64.000€", 45, 55, 500, 32000),
        THIRTEEN ("125.000€", 55, 75, 500, 64000),
        FOURTEEN ("500.000€", 75, 95, 500, 125000),
        FIFTEEN ("1.000.000€", 95, 200, 500, 500000),
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
    protected void writeStats() {
        List<PlayerStatsWWMObject> file = new ArrayList<>(readFile());

        int index = file.indexOf(playerStatsWWMObject);
        if (index == -1) {
            file.add(playerStatsWWMObject);
        } else {
            PlayerStatsWWMObject oldStats = file.get(index);
            oldStats.add(playerStatsWWMObject);
        }

        Collections.sort(file);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(getStatsFilesPath()))) {
            for (PlayerStatsWWMObject playerStatsWWMObject1 : file) {
                bufferedWriter.write(playerStatsWWMObject1.getCompleteLine());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
            //TODO
        }
    }

    private List<PlayerStatsWWMObject> readFile() {
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader(getStatsFilesPath()))) {
            return bufferedReader.lines()
                    .map(PlayerStatsWWMObject::new)
                    .toList();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Ein unerwarteter Fehler ist aufgetreten.");
            //TODO zurückspringen mit separater Exception
        }
        return new ArrayList<>();
    }
}
