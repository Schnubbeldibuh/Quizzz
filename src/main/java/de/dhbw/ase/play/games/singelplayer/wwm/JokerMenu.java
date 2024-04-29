package de.dhbw.ase.play.games.singelplayer.wwm;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Submenu;
import de.dhbw.ase.repository.question.Question;
import de.dhbw.ase.user.in.UserIn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JokerMenu extends Submenu {

    private FiftyFiftyJoker fiftyFiftyJoker;
    private AudienceJoker audienceJoker;
    private PhoneJoker phoneJoker;

    public JokerMenu(UserIn sc) {
        super(sc);
        fiftyFiftyJoker = new FiftyFiftyJoker();
        audienceJoker = new AudienceJoker();
        phoneJoker = new PhoneJoker();
    }

    public void setAnswerLists(Set<Question.Answer> answerList) {
        fiftyFiftyJoker.setAnswerList(answerList);
        audienceJoker.setAnswerList(answerList);
        phoneJoker.setAnswerList(answerList);
    }
    @Override
    protected Map<String, SelectedMenu> generateSelectionMap() {
        Map<String, SelectedMenu> map = new HashMap<>();
        map.put("1", new SelectedMenu(fiftyFiftyJoker));
        map.put("2", new SelectedMenu(audienceJoker));
        map.put("3", new SelectedMenu(phoneJoker));
        map.put("4", new SelectedMenu(SelectedMenu.MenuSelection.BACK));
        map.put("5", new SelectedMenu(SelectedMenu.MenuSelection.EXIT));
        return map;
    }

    @Override
    protected void showOptions() {
        System.out.println("Welchen Joken möchtest du benutzen?");
        if (fiftyFiftyJoker != null) {
            System.out.println("1 - 50:50");
        }
        if (audienceJoker != null) {
            System.out.println("2 - Publikums-Joker");
        }
        if (phoneJoker != null) {
            System.out.println("3 - Telefon-Joker");
        }
        System.out.println("4 - Zurück");
        System.out.println("5 - Exit");
    }
}
