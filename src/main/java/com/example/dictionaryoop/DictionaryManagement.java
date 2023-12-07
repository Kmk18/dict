package com.example.dictionaryoop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.*;

public class DictionaryManagement extends Dictionary {
    public static final String INPUT_PATH = "src/main/java/files/dictionaries.txt";
    public static final String OUTPUT_PATH = "src/main/java/files/dictionaries_out.txt";
    public static final String FILE_PATH = "src/main/java/files/resource.txt";

    public void insertFromCommandLine() {
        Scanner strInput = new Scanner(System.in);
        Scanner intInput = new Scanner(System.in);
        int count = intInput.nextInt();
        int i = 1;
        while (i <= count) {
            String target = strInput.nextLine();
            String explain = strInput.nextLine();
            Word temp = new Word(target, explain);
            words.add(temp);
            i++;
        }
    }

    public static void insertFromFile() {
        try {
            File input = new File(INPUT_PATH);
            FileReader fileReader = new FileReader(input);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] wordsInLine = line.split(",");
                Word temp = new Word(wordsInLine[0], wordsInLine[1]);
                words.add(temp);
            }
            Collections.sort(words);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertData() {
        try {
            File input = new File(FILE_PATH);
            FileReader fileReader = new FileReader(input);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] wordsInLine = line.split(",");
                Word temp = new Word(wordsInLine[0], wordsInLine[1]);
                words.add(temp);
            }
            Collections.sort(words);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exportToFile() {
        try {
            File file = new File(OUTPUT_PATH);
            OutputStream outputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            String format = "%-12s %-12s%n";
            for (Word word : words) {
                bufferedWriter.write(String.format(format, word.getWord_target(), word.getWord_explain()));
            }
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void modifyWord(String target, String explain) {
        target = target.toLowerCase();
        explain = explain.toLowerCase();
        int pos = -1;
        pos = Collections.binarySearch(words, new Word(target, null));
        if (pos >= 0) {
            words.get(pos).setWord_explain(explain);
        } else {
            System.out.println("Word not exist!");
        }
        updateWordToFile();
    }


    public static void addWord(String target, String explain) {
        target = target.toLowerCase();
        explain = explain.toLowerCase();
        int posAddWord = binaryCheck(0, words.size(), target);
        if (posAddWord == -1) {
            System.out.println("Word exist!");
            return;
        }
        words.add(new Word());
        for (int i = words.size() - 2; i >= posAddWord; i--) {
            words.get(i + 1).setWord_target(words.get(i).getWord_target());
            words.get(i + 1).setWord_explain(words.get(i).getWord_explain());
        }
        words.get(posAddWord).setWord_target(target);
        words.get(posAddWord).setWord_explain(explain);
        updateWordToFile();

        System.out.println("New word added successfully!");
    }

    public static void removeWord(String target) {
        target = target.toLowerCase();
        int index = Collections.binarySearch(words, new Word(target, null));
        if (index >= 0) {
            words.remove(words.get(index));
        } else {
            System.out.println("Word not exist!");
        }
        updateWordToFile();
    }

    public static int binaryCheck(int start, int end, String word) {
        if (end < start) {
            return -1;
        }
        int mid = start + (end - start) / 2;
        int compareNext = word.compareTo(words.get(mid).getWord_target());
        if (mid == 0) {
            if (compareNext < 0) {
                return 0;
            } else if (compareNext > 0) {
                return binaryCheck(mid + 1, end, word);
            } else {
                return -1;
            }
        } else {
            int comparePrevious = word.compareTo(words.get(mid - 1).getWord_target());
            if (comparePrevious > 0 && compareNext < 0) {
                return mid;
            } else if (comparePrevious < 0) {
                return binaryCheck(start, mid - 1, word);
            } else if (compareNext > 0) {
                if (mid == words.size() - 1) {
                    return words.size();
                }
                return binaryCheck(mid + 1, end, word);
            } else {
                return -1;
            }
        }
    }

    public static void updateWordToFile() {
        try {
            FileWriter fileWriter = new FileWriter(FILE_PATH);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Word word : words) {
                bufferedWriter.write(word.getWord_target() + "," + word.getWord_explain() + "\n");
            }
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dictionaryLookup() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Type the word to lookup: ");
        String wordToLookup = scanner.nextLine().toLowerCase();

        int index = Collections.binarySearch(words, new Word(wordToLookup, null));
        if (index >= 0) {
            Word word = words.get(index);
            System.out.printf("Found word: English: %s - Vietnamese: %s%n", word.getWord_target(), word.getWord_explain());
        } else {
            System.out.println("Word can not be found in the dictionary.");
        }
    }

    public static void dictionarySearcher() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter keyword: ");
        String keyword = scanner.nextLine().toLowerCase();

        ArrayList<String> matchingWords = new ArrayList<>();
        for (Word word : words) {
            String target = word.getWord_target().toLowerCase();
            if (target.contains(keyword)) {
                matchingWords.add(word.getWord_target());
            }
        }

        if (matchingWords.isEmpty()) {
            System.out.println("Couldn't find any related words.");
        } else {
            System.out.println("Related words:");
            for (String matchingWord : matchingWords) {
                System.out.println(matchingWord);
            }
        }
    }

    @FXML
    private TextField lookUp;

    @FXML
    public void FXML_dictionaryLookup(ActionEvent actionEvent) {
        String wordToLookup = lookUp.getText().toLowerCase();
        int index = Collections.binarySearch(words, new Word(wordToLookup, null));
        if (index >= 0) {
            Word word = words.get(index);
            System.out.printf("Found word: English: %s - Vietnamese: %s%n", word.getWord_target(), word.getWord_explain());
        } else {
            System.out.println("Word can not be found in the dictionary.");
        }
    }
}

