package de.dhbw.ase.play.games.reader;

import de.dhbw.ase.Quizzz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WWMReader extends Reader{

    @Override
    public List<Question> getQuestionList() {
        List<Question> questionsForOneRound = new ArrayList<>();
        questionsForOneRound.addAll(readFile(new File(Quizzz.FILE_WWM_EASY), 5));
        questionsForOneRound.addAll(readFile(new File(Quizzz.FILE_WWM_MEDIUM), 4));
        questionsForOneRound.addAll(readFile(new File(Quizzz.FILE_WWM_HARD), 3));
        questionsForOneRound.addAll(readFile(new File(Quizzz.FILE_WWM_VERY_HARD), 2));
        questionsForOneRound.addAll(readFile(new File(Quizzz.FILE_WWM_EXPERT), 1));
        return questionsForOneRound;
    }
}
