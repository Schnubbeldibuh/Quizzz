package de.dhbw.ase.questionmanagement;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Startable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuestionManagerEdit implements Startable {

    private Scanner sc;
    private QuestionManagerEditingMenu questionManagerEditingMenu;
    private List<QuestionObject> file;
    private int questionIndex;
    public QuestionManagerEdit(Scanner sc, QuestionManagerEditingMenu questionManagerEditingMenu) {
        this.sc = sc;
        this.questionManagerEditingMenu = questionManagerEditingMenu;
    }

    @Override
    public SelectedMenu.MenuSelection start() {
        readFileContent();
        getQuestionIndex();
        editQuestion();
        editRightAnswer();
        for (int i = 2; i < 5; i++) {
            editWrongAnswer(i);
        }
        writeBackToFile();
        return SelectedMenu.MenuSelection.BACK;
    }

    private void writeBackToFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(questionManagerEditingMenu.getFilePath()))) {
            for (QuestionObject questionObject : file) {
                bufferedWriter.write(questionObject.getCompleteLine());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
            //TODO
        }
    }

    private void getQuestionIndex() {
        for (int i = 0; i < file.size(); i++) {
            if(file.get(i).getCompleteLine().startsWith(questionManagerEditingMenu.getSelectedLine())) {
                questionIndex = i;
                return;
            }
        }
    }

    private void editWrongAnswer(int i) {
        System.out.println("Falls du die falsche Antwort " + i + " beibehalten möchtest: Enter.");
        System.out.println("Falls du die falsche Antwort " + i + " ändern möchtest: Neue Frage eingeben.");
        String input = sc.nextLine();
        if (input.isEmpty()) {
            return;
        }
        if (i == 1) {
            file.get(questionIndex).setWrongAnswer1(input);
        } else if (i == 2) {
            file.get(questionIndex).setWrongAnswer2(input);
        } else {
            file.get(questionIndex).setWrongAnswer3(input);
        }
    }

    private void editRightAnswer() {
        System.out.println("Falls du die richtige Antwort beibehalten möchtest: Enter.");
        System.out.println("Falls du die richtige Antwort ändern möchtest: Neue Frage eingeben.");
        String input = sc.nextLine();
        if (input.isEmpty()) {
            return;
        }
        file.get(questionIndex).setRightAnswer(input);
    }

    private void editQuestion() {
        System.out.println("Falls du die Frage ändern möchtest: Neue Frage eingeben.");
        System.out.println("Falls du die Frage beibehalten möchtest: Enter.");
        String input = sc.nextLine();
        if (input.isEmpty()) {
            return;
        }
        file.get(questionIndex).setQuestion(input);
    }

    private void readFileContent() {
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader(questionManagerEditingMenu.getFilePath()))) {
            file = bufferedReader.lines().map(QuestionObject::new).toList();
        } catch (FileNotFoundException e) {
            //TODO
        } catch (IOException e) {
            System.out.println("Ein unerwarteter Fehler ist aufgetreten.");
            //TODO zurückspringen mit separater Exception
        }
    }
}
