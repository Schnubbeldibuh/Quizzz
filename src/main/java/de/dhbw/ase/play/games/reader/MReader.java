package de.dhbw.ase.play.games.reader;

import de.dhbw.ase.Quizzz;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MReader extends Reader{
    @Override
    public List<Question> getQuestionList() {
        List<Question> questionsForOneRound = new ArrayList<>(readFile(new File(Quizzz.FILE_WWM_EASY), 5));
        //TODO neuen Datensatz mit Fragen für Mulitplayer erstellen und hier anpassen
        return questionsForOneRound;
    }
}
