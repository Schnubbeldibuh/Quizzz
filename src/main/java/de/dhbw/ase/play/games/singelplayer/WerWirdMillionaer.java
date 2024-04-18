package de.dhbw.ase.play.games.singelplayer;

import de.dhbw.ase.play.games.reader.Question;
import de.dhbw.ase.play.games.reader.WWMReader;
import de.dhbw.ase.stats.PlayerStatsWWMObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class WerWirdMillionaer extends SingeplayerGame {

    private PlayerStatsWWMObject playerStatsWWMObject;
    private final String statsFilesPath;

    public WerWirdMillionaer(Scanner sc, String statsFilesPath) {
        super(sc);
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
            showQuestionLevel(i);
            boolean answerEvaluation = playQuestion(questionList.get(i));
            if (answerEvaluation) {
                System.out.println("Richtige Antwort!");
                continue;
            }
            System.out.println("Diese Antwort war leider Falsch.");
            if (i >= 5) {
                System.out.println("Herzlichen Glückwunsch zu 500€!");
            }
            // Sicherheitsstufe bei 16.000 € noch möglich

            Question.Answer rightAnswer = questionList.get(i).getAnswerList()
                    .stream()
                    .filter(Question.Answer::isRight)
                    .findFirst()
                    .get();
            System.out.println("Die richtige Antwort wäre \"" + rightAnswer + "\" gewesen.");

            playerStatsWWMObject = new PlayerStatsWWMObject(getUsername(),
                    WWMLevels.values()[i].getPoints(),
                    WWMLevels.values()[i].getMoneyWonIfLost());
            return;
        }

        //Wenn die for-Schleife erfolgreich durchlaufen wurde, wurde die Millionfrage richtig beantworte.
        playerStatsWWMObject = new PlayerStatsWWMObject(getUsername(),
                WWMLevels.WON.getPoints(),
                WWMLevels.WON.getMoneyWon());
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
        ONE ("50€", 0, 0, 0),
        TWO ("100€", 1, 0, 50),
        THREE ("200€", 1, 0, 100),
        FOUR ("300€", 1, 0, 200),
        FIVE ("500€", 1, 0, 300),
        SIX ("1.000€", 1, 500, 500),
        SEVEN ("2.000€",5, 500, 1000),
        EIGHT ("4.000€", 5, 500, 2000),
        NINE ("8.000€", 5, 500, 4000),
        TEN ("16.000€", 5, 500, 8000),
        ELEVEN ("32.000€", 10, 500, 16000),
        TWELVE ("64.000€", 10, 500, 32000),
        THIRTEEN ("125.000€", 10, 500, 64000),
        FOURTEEN ("500.000€", 20, 500, 125000),
        FIFTEEN ("1.000.000€", 20,500, 500000),
        WON("WON", 50, 1000000, 1000000);

        private final String level;
        private final int points;
        private final int rightAnswers;
        private final int moneyWonIfLost;
        private final int moneyWon;


        WWMLevels(String level, int points, int moneyWonIfLost, int moneyWon) {
            this.level = level;
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
    }

    protected void writeStats() {
        List<PlayerStatsWWMObject> file;
        List<PlayerStatsWWMObject> tempList = new ArrayList<>();

        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader(getStatsFilesPath()))) {
            tempList = bufferedReader.lines()
                    .map(PlayerStatsWWMObject::new)
                    .toList();
        } catch (FileNotFoundException e) {
            //TODO
        } catch (IOException e) {
            System.out.println("Ein unerwarteter Fehler ist aufgetreten.");
            //TODO zurückspringen mit separater Exception
        }
        file = new ArrayList<>(tempList);

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
}
