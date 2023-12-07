package com.example.dictionaryoop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    public static final String FILE_PATH = "src/main/java/files/resource.txt";
    public static final int MAX_LIVES = 5;

    public static void playGame() {
        try {
            // Đọc danh sách từ vựng từ file
            ArrayList<String> wordList = readWordListFromFile();

            // Chọn từ ngẫu nhiên từ danh sách
            String chosenWord = getRandomWord(wordList);

            // Khởi tạo trạng thái đoán của từ
            char[] guessedWord = new char[chosenWord.length()];
            for (int i = 0; i < chosenWord.length(); i++) {
                if (Character.isLetter(chosenWord.charAt(i))) {
                    guessedWord[i] = '_';
                } else {
                    guessedWord[i] = chosenWord.charAt(i);
                }
            }

            int lives = MAX_LIVES;
            Scanner scanner = new Scanner(System.in);

            System.out.println("Welcome to the game \"GuessWord\":");
            System.out.println("The chosen word has this format: \"" + new String(guessedWord) + "\"");

            // Bắt đầu vòng lặp
            while (true) {
                System.out.print("Take your guess: ");
                char guess = scanner.next().toLowerCase().charAt(0);

                // Kiểm tra xem chữ cái đoán có xuất hiện trong từ hay không
                boolean found = false;
                for (int i = 0; i < chosenWord.length(); i++) {
                    if (Character.toLowerCase(chosenWord.charAt(i)) == guess) {
                        guessedWord[i] = chosenWord.charAt(i);
                        found = true;
                    }
                }

                // In ra kết quả
                if (found) {
                    System.out.println("There is a \"" + guess + "\" in the word: \"" + new String(guessedWord) + "\"");
                } else {
                    lives--;
                    System.out.println("Oops! You are wrong. You have " + lives + " lives left.");
                }

                // Kiểm tra xem thua hay thắng
                if (lives == 0) {
                    System.out.println("Better luck next time!");
                    break;
                } else if (!new String(guessedWord).contains("_")) {
                    System.out.println("Congratulations! You made it !");
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<String> readWordListFromFile() throws Exception {
        ArrayList<String> wordList = new ArrayList<>();
        File file = new File(FILE_PATH);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] wordsInLine = line.split(",");
            wordList.add(wordsInLine[0].trim());
        }
        bufferedReader.close();
        return wordList;
    }

    private static String getRandomWord(ArrayList<String> wordList) {
        Random random = new Random();
        int randomIndex = random.nextInt(wordList.size());
        return wordList.get(randomIndex);
    }
}

