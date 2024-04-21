package de.dhbw.ase.play.games.multiplayer.quiz;

import de.dhbw.ase.play.games.ExitException;
import de.dhbw.ase.play.games.multiplayer.CommunicationPrefixes;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerClient;
import de.dhbw.ase.stats.persistance.PlayerStatsMPObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MultiplayerQuizClient extends MultiplayerClient {

    private final String filepath;

    private List<PlayerStatsMPObject> stats = new ArrayList<>();

    public MultiplayerQuizClient(Scanner sc, String username, String filepath) {
        super(sc, username);
        this.filepath = filepath;

        validServerMessages.add(CommunicationPrefixes.ANSWER_EVALUATION);
        validServerMessages.add(CommunicationPrefixes.NEXT_QUESTION);
        validServerMessages.add(CommunicationPrefixes.ROUND_FINISHED);
        validServerMessages.add(CommunicationPrefixes.START_GAME);
        validServerMessages.add(CommunicationPrefixes.STATS_TRANSFER_FINISHED);
        validServerMessages.add(CommunicationPrefixes.STATS_TRANSFER);
    }


    protected void writeStats() {
        List<PlayerStatsMPObject> file = new ArrayList<>(readFile());

        for (PlayerStatsMPObject p: stats) {

            int index = file.indexOf(p);
            if (index == -1) {
                file.add(p);
            } else {
                PlayerStatsMPObject oldStats = file.get(index);
                oldStats.add(p);
            }
        }

        Collections.sort(file);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath))) {
            for (PlayerStatsMPObject playerStatsMPObject : file) {
                bufferedWriter.write(playerStatsMPObject.getCompleteLine());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
            //TODO
        }
    }

    private List<PlayerStatsMPObject> readFile() {
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader(filepath))) {
            return bufferedReader.lines()
                    .map(PlayerStatsMPObject::new)
                    .toList();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Ein unerwarteter Fehler ist aufgetreten.");
            //TODO zurückspringen mit separater Exception
        }

        return new ArrayList<>();
    }

    @Override
    protected boolean checkUserInput(String input) throws ExitException {
        if (input.equalsIgnoreCase("exit")) {
            throw new ExitException();
        }

        return input.equalsIgnoreCase("a")
                || input.equalsIgnoreCase("b")
                || input.equalsIgnoreCase("c")
                || input.equalsIgnoreCase("d");
    }


    @Override
    protected List<MultiplayerClient.Source> processServerInput(String input) {
        List<MultiplayerClient.Source> sourceList = new ArrayList<>();

        switch (CommunicationPrefixes.evaluateCase(input)) {
            case ROUND_FINISHED:
                return new ArrayList<>();

            case START_GAME:
                System.out.println("Das Spiel startet.");
                sourceList.add(Source.SERVER);
                break;

            case ANSWER_EVALUATION:
                boolean evaluation =
                        Boolean.parseBoolean(input.substring(CommunicationPrefixes.ANSWER_EVALUATION.getLength()));

                if (evaluation) {
                    System.out.println("Die Antwort ist richtig :)");
                } else {
                    System.out.println("DU DUMME SAU");
                }

                sourceList.add(Source.SERVER);
                break;

            case NEXT_QUESTION:
                showQuestion(input);
                sourceList.add(Source.USER);
                break;

            case STATS_TRANSFER:
                stats.add(
                        new PlayerStatsMPObject(input.substring(CommunicationPrefixes.STATS_TRANSFER.getLength())));
                sourceList.add(Source.SERVER);
                break;

            case STATS_TRANSFER_FINISHED:
                writeStats();
                stats = new ArrayList<>();
                sourceList.add(Source.SERVER);
                break;
        }

        return sourceList;
    }

    @Override
    protected List<Source> processUserInput(String input) {
        Integer selection = switch (input) {
            case "a" -> 0;
            case "b" -> 1;
            case "c" -> 2;
            case "d" -> 3;
            default -> throw new IllegalStateException("Unexpected value: " + input);
        };
        sendMessageToServer(CommunicationPrefixes.ANSWER.toString() + selection);
        List<MultiplayerClient.Source> sourceList = new ArrayList<>();
        sourceList.add(Source.SERVER);

        return sourceList;
    }
}
