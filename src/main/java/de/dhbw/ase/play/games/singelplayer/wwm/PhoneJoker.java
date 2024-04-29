package de.dhbw.ase.play.games.singelplayer.wwm;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Startable;
import de.dhbw.ase.repository.question.Question;

import java.util.List;
import java.util.Set;

public class PhoneJoker implements Startable {

    private Set<Question.Answer> answerList;
    private boolean alreadyUsed;

    public void setAnswerList(Set<Question.Answer> answerList) {
        this.answerList = answerList;
    }

    private void usePhone() {
        alreadyUsed = true;
        Question.Answer rightAnswer = answerList
                .stream()
                .filter(Question.Answer::isRight)
                .findFirst()
                .get();

        System.out.println("Hallo! Ich vermute die richtige Antwort lautet " + rightAnswer.answer());
    }
    @Override
    public SelectedMenu.MenuSelection start() {
        if (alreadyUsed) {
            System.out.println("Telefon-Joker wurde schon benutzt.");
            System.out.println("Bitte deine Antwort (a,b,c,d) oder <Joker> eingeben.");
            return SelectedMenu.MenuSelection.BACK;
        }
        usePhone();
        return SelectedMenu.MenuSelection.BACK;
    }
}
