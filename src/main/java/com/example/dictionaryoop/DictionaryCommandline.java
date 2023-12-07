package com.example.dictionaryoop;

import java.io.*;
import java.util.*;

import static com.example.dictionaryoop.DictionaryManagement.*;
import static com.example.dictionaryoop.Game.playGame;

public class DictionaryCommandline extends Dictionary {
    public static String showAllWords() {
        String temp = "";
        System.out.printf("%-4s%c %-12s%c %-15s%n","No", '|' ,"English", '|', "Vietnamese");
        for (int i = 0; i < words.size(); i++) {
            System.out.printf("%-4d%c %-12s%c %-15s%n", i + 1,'|'
                    , words.get(i).getWord_target(), '|',words.get(i).getWord_explain());
        }
        return temp;
    }

    public static void dictionaryAdvanced() throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to My Dictionary!");

        while (true) {
            System.out.println("[0] Exit");
            System.out.println("[1] Add");
            System.out.println("[2] Remove");
            System.out.println("[3] Update");
            System.out.println("[4] Display");
            System.out.println("[5] Lookup");
            System.out.println("[6] Search");
            System.out.println("[7] Game");
            System.out.println("[8] Import from file");
            System.out.println("[9] Export to file");

            System.out.print("Please choose your action: ");
            int choice;

            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Action not supported");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 0:
                    System.out.println("Exiting the application.");
                    return;
                case 1:
                    addWordFromUserInput();
                    break;
                case 2:
                    removeWordFromUserInput();
                    break;
                case 3:
                    modifyWordFromUserInput();
                    break;
                case 4:
                    showAllWords();
                    break;
                case 5:
                    dictionaryLookup();
                    break;
                case 6:
                    dictionarySearcher();
                    break;
                case 7:
                    playGame();
                    break;
                case 8:
                    insertFromFile();
                    break;
                case 9:
                    exportToFile();
                    break;
                default:
                    System.out.println("Action not supported");
            }
        }
    }


    private static void modifyWordFromUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the English word to update: ");
        String target = scanner.nextLine();
        System.out.print("Enter the new Vietnamese meaning: ");
        String explain = scanner.nextLine();
        modifyWord(target, explain);
    }

    private static void addWordFromUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the English word: ");
        String target = scanner.nextLine();
        System.out.print("Enter the Vietnamese meaning: ");
        String explain = scanner.nextLine();
        addWord(target, explain);
    }

    private static void removeWordFromUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the English word to remove: ");
        String target = scanner.nextLine();
        removeWord(target);
    }
}
