package de.dhbw.ase.play.games.singelplayer.wwm;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Startable;
import de.dhbw.ase.repository.question.Question;

import java.util.*;

public class FiftyFiftyJoker implements Startable {

    private List<Question.Answer> answerList;
    private boolean alreadyUsed;

    public void setAnswerList(Set<Question.Answer> answerList) {
        this.answerList = new ArrayList<>(answerList);
    }

    private void useFiftyFifty() {
        alreadyUsed = true;
        Set<Question.Answer> randomChoice = new HashSet<>();

        Question.Answer rightAnswer = answerList
                .stream()
                .filter(Question.Answer::isRight)
                .findFirst()
                .get();

        randomChoice.add(rightAnswer);

        while (randomChoice.size() < 2){
            Random random = new Random();
            int randomInt = random.nextInt(4);
            randomChoice.add(answerList.get(randomInt));
        }
        List<Question.Answer> outputList = new ArrayList<>(randomChoice);
        Collections.shuffle(outputList);
        System.out.println("Die übrigen Antworten lauten:");
        System.out.println(outputList.get(0));
        System.out.println(outputList.get(1));
        System.out.println();
        System.out.println("Bitte deine Antwort (a,b,c,d) oder <Joker> eingeben.");
    }

    @Override
    public SelectedMenu.MenuSelection start() {
        if (alreadyUsed) {
            System.out.println("50:50-Joker wurde schon benutzt.");
            System.out.println("Bitte deine Antwort (a,b,c,d) oder <Joker> eingeben.");
            return SelectedMenu.MenuSelection.BACK;
        }
        useFiftyFifty();
        return SelectedMenu.MenuSelection.BACK;
    }
}