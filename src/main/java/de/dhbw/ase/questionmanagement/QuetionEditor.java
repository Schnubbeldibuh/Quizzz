package de.dhbw.ase.questionmanagement;

import de.dhbw.ase.Startable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class QuetionEditor implements Startable {

    protected List<QuestionObject> file;
    protected int questionIndex;

    protected void writeBackToFile(String filePath) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (QuestionObject questionObject : file) {
                bufferedWriter.write(questionObject.getCompleteLine());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
            //TODO
        }
    }

    protected void getQuestionIndex(String selectedLine) {
        for (int i = 0; i < file.size(); i++) {
            if(file.get(i).getCompleteLine().startsWith(selectedLine)) {
                questionIndex = i;
                return;
            }
        }
    }

    protected void readFileContent(String filePath) {
        List<QuestionObject> tempList = null;
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader(filePath))) {
            tempList = bufferedReader.lines().map(QuestionObject::new).toList();
        } catch (FileNotFoundException e) {
            //TODO
        } catch (IOException e) {
            System.out.println("Ein unerwarteter Fehler ist aufgetreten.");
            //TODO zurückspringen mit separater Exception
        }
        file = new ArrayList<>(tempList);
    }
}
