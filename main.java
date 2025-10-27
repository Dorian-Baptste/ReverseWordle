import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class Main {
    public static void main(String[] args) {
        printInstructions();
        makeGuesses();
    }

    public static List<String> getWordsFromFile() {
        List<String> words = new ArrayList<>();
        try (Scanner scanner = new Scanner (new File("words.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.length() == 5) {
                    words.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }    
        return words;
    }

    public static void printInstructions() {
        System.out.println("Welcome to Reverse Wordle! I will try to guess your word. \nFor each guess, you need to enter an evaluation string.\nThe evaluation string consists of one character for each letter in the guess.\nFor each correct letter in the correct place, that letter capitalized.\nFor each correct letter in the incorrect place, that letter lowercase.\nFor each incorrect letter, an underscore.");
    }

    public static void makeGuesses() {
        List<String> possibleWords = getWordsFromFile();
        int randIndex = (int)(Math.random() * possibleWords.size());
        System.out.println("My guess is: " + possibleWords.get(randIndex));
        Scanner scanner = new Scanner(System.in);
        String evaluation = scanner.nextLine();
        for (int i = 0; i < evaluation.length(); i++) {
            final int idx = i;
            char evalChar = evaluation.charAt(idx);
            char guessChar = possibleWords.get(randIndex).charAt(idx);
            if (evalChar == '_') {
                possibleWords.removeIf(word -> word.indexOf(guessChar) != -1);
            } else if (Character.isLowerCase(evalChar)) {
                possibleWords.removeIf(word -> word.indexOf(evalChar) == -1 || word.charAt(idx) == evalChar);
            } else if (Character.isUpperCase(evalChar)) {
                possibleWords.removeIf(word -> word.charAt(idx) != evalChar);
            }
        }

        scanner.close();
    }
}
