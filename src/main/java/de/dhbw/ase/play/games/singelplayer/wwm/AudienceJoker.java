package de.dhbw.ase.play.games.singelplayer.wwm;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Startable;
import de.dhbw.ase.repository.question.Question;

import java.util.*;

public class AudienceJoker implements Startable {

    private Set<Question.Answer> answerList;
    private boolean alreadyUsed;

    public void setAnswerList(Set<Question.Answer> answerList) {
        this.answerList = answerList;
    }

    private void useAudience() {
        alreadyUsed = true;
        Set<Question.Answer> answerListCopy = new HashSet<>(answerList);

        Question.Answer rightAnswer = answerList
                .stream()
                .filter(Question.Answer::isRight)
                .findFirst()
                .get();

        answerListCopy.remove(rightAnswer);

        Random random = new Random();
        List<Integer> randomIntsList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            randomIntsList.add(random.nextInt(
                    101 - randomIntsList
                    .stream()
                    .mapToInt(a -> a)
                    .sum()
            ));
        }
        Collections.sort(randomIntsList);
        System.out.println(rightAnswer + ": " + randomIntsList.get(3));
        Iterator<Question.Answer> iterator = answerListCopy.iterator();
        System.out.println(iterator.next() + ": " + randomIntsList.get(2));
        System.out.println(iterator.next() + ": " + randomIntsList.get(1));
        System.out.println(iterator.next() + ": " + randomIntsList.get(0));
    }
    @Override
    public SelectedMenu.MenuSelection start() {
        if (alreadyUsed) {
            System.out.println("Publikums-Joker wurde schon benutzt.");
            System.out.println("Bitte deine Antwort (a,b,c,d) oder <Joker> eingeben.");
            return SelectedMenu.MenuSelection.BACK;
        }
        useAudience();
        return SelectedMenu.MenuSelection.BACK;
    }
}
