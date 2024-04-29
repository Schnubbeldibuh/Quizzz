package de.dhbw.ase.play.games.singelplayer.wwm;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Startable;
import de.dhbw.ase.repository.question.Question;

import java.util.List;
import java.util.Set;

public class AudienceJoker implements Startable {

    private Set<Question.Answer> answerList;

    public void setAnswerList(Set<Question.Answer> answerList) {
        this.answerList = answerList;
    }
    @Override
    public SelectedMenu.MenuSelection start() {
        return null;
    }
}
