package de.dhbw.ase.play.games.reader;

import de.dhbw.ase.Quizzz;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FQReader extends Reader{

    @Override
    public List<Question> getQuestionList() {
        return new ArrayList<>(readFile(new File(Quizzz.FILE_FQ2), 15));
    }
}
