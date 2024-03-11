package de.dhbw.ase.play.games.singelplayer;

import de.dhbw.ase.play.games.reader.Question;
import de.dhbw.ase.play.games.reader.WWMReader;
import de.dhbw.ase.play.games.singelplayer.SingeplayerGame;

import java.util.List;
import java.util.Scanner;

public class WerWirdMillionaer extends SingeplayerGame {

    public WerWirdMillionaer(Scanner sc) {
        super(sc);
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
            // bei falscher Antwort noch die Richtige anzeigen
            return;
        }
    }

    private void showQuestionLevel(int level) {
        System.out.println();
        System.out.println("Level: " + level + ", Gewinnstufe: " + WWMLevels.values()[level].getLevel());
    }

    private enum WWMLevels {
        ONE ("50€"),
        TWO ("100€"),
        THREE ("200€"),
        FOUR ("300€"),
        FIVE ("500€"),
        SIX ("1.000€"),
        SEVEN ("2.000€"),
        EIGHT ("4.000€"),
        NINE ("8.000€"),
        TEN ("16.000€"),
        ELEVEN ("32.000€"),
        TWELVE ("64.000€"),
        THIRTEEN ("125.000€"),
        FOURTEEN ("500.000€"),
        FIFTEEN ("1.000.000€");

        private final String level;

        WWMLevels(String level) {
            this.level = level;
        }

        public String getLevel() {
            return level;
        }
    }
}
