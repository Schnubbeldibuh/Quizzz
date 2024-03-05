package de.dhbw.ase.play.games.reader;

import de.dhbw.ase.Quizzz;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FQReader extends Reader{

    @Override
    public List<Question> getQuestionList() {
        List<Question> questionsForOneRound = new ArrayList<>();
        questionsForOneRound.addAll(readFile(new File(Quizzz.FILE_FQ), 15));
        return questionsForOneRound;
    }
}
