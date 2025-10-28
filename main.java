import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.io.File;
import java.io.FileNotFoundException;
public class Main {
    /**
     * Main method that starts program and calls printInstructions and makeGuesses methods.
     * @param args - allows to pass command line arguments
     */
    public static void main(String[] args) {
        printInstructions();
        makeGuesses();
    }
    /**
     * Reads words from word file and saves all five letter words to a list while making them lowercase.
     * @return List of five letter words.
     */
    public static List<String> getWordsFromFile() {
        List<String> words = new ArrayList<>();
        try (Scanner scanner = new Scanner (new File("words.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.length() == 5) {
                    words.add(line.toLowerCase());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }    
        return words;
    }
    /**
     * Filters words based on the evaluation of the guess. if the eval is '_' the letter is not in the word, if it is lowercase the letter is in the word but not in that position, if it is uppercase the letter is in that position.
     * @param word - word to be tested
     * @param guess - the guessed word
     * @param evaluation - the evaluation string
     * @return true if the word passes the filter, false if the word does not
     */
    public static boolean filteredWords(String word, String guess, String evaluation) {
        for (int i = 0; i < 5; i++) {
            final int idx = i;
            char evalChar = evaluation.charAt(idx);
            char guessChar = guess.charAt(idx);
            if (evalChar == '_') {
                if (word.contains("" + guessChar)) return false;
            } else if (Character.isLowerCase(evalChar)) {
                if (word.charAt(idx) == evalChar || !word.contains("" + evalChar)) return false;
            } else if (Character.isUpperCase(evalChar)) {
                if (word.charAt(idx) != Character.toLowerCase(evalChar)) return false;
            }
        }

        return true;
    }
    /**
     * Makes guesses and filters the list until the correct word is found or the list is empty.
     */
    public static void makeGuesses() {
        List<String> possibleWords = getWordsFromFile();
    
        Scanner scanner = new Scanner(System.in);

        while (!possibleWords.isEmpty()) {
            String randomWord = possibleWords.get((int)(Math.random() * possibleWords.size()));
            System.out.println("My guess is: " + randomWord);

            String evaluation = scanner.nextLine();
    
            if (evaluation.equals(randomWord.toUpperCase())) {
                System.out.println("Yay! I guessed your word!");
                break;
            }

            final String currentGuess = randomWord;
            final String currentEval = evaluation;
        
            possibleWords = possibleWords.stream()
                .filter(word -> filteredWords(word, currentGuess, currentEval))
                .collect(Collectors.toList());

            if (possibleWords.size() == 1) {
                System.out.println("I have guessed your word! Is it: " + possibleWords.get(0) + "?");
                String confirmation = scanner.nextLine();
                if (confirmation.equalsIgnoreCase("yes")) {
                    System.out.println("Yay! I guessed your word!");
                } else {
                    System.out.println("Oh no! I couldn't guess your word.");
                }
                break;
            } else if (possibleWords.isEmpty()) {
                System.out.println("This word is not in my dictionary. Please try again.");
            } 
        }
    
    scanner.close();
    }
    /**
     * Prints instructions for the game.
     */
    public static void printInstructions() {
        System.out.println("Welcome to Reverse Wordle! I will try to guess your word. \nFor each guess, you need to enter an evaluation string.\nThe evaluation string consists of one character for each letter in the guess.\nFor each correct letter in the correct place, that letter capitalized.\nFor each correct letter in the incorrect place, that letter lowercase.\nFor each incorrect letter, an underscore.");
    }
}