package de.dhbw.ase.questionmanagement;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Startable;

import java.util.Scanner;

public class QuestionManagerAdd extends QuetionEditor {

    private Scanner sc;
    private QuestionManager questionManager;
    public QuestionManagerAdd(Scanner sc, QuestionManager questionManager) {
        this.sc = sc;
        this.questionManager = questionManager;
    }

    @Override
    public SelectedMenu.MenuSelection start() {
        readFileContent(questionManager.getFilePath());
        QuestionObject questionObject = new QuestionObject();
        System.out.println("Bitte gib die neue Frage ein: ");
        sc.nextLine();
        String input = sc.nextLine();
        questionObject.setQuestion(input);
        System.out.println();
        System.out.println("Bitte gib die richtige Antwort ein:");
        input = sc.nextLine();
        questionObject.setRightAnswer(input);
        System.out.println();
        System.out.println("Bitte gib eine falsche Antwort ein:");
        input = sc.nextLine();
        questionObject.setWrongAnswer1(input);
        System.out.println();
        System.out.println("Bitte gib eine andere falsche Antwort ein:");
        input = sc.nextLine();
        questionObject.setWrongAnswer2(input);
        System.out.println();
        System.out.println("Bitte gib eine andere falsche Antwort ein:");
        input = sc.next();
        questionObject.setWrongAnswer3(input);
        System.out.println();
        questionObject.setQuestionIndex(file.size() + "");
        file.add(0, questionObject);
        writeBackToFile(questionManager.getFilePath());
        return SelectedMenu.MenuSelection.BACK;
    }
}
