import java.util.*;

public class Hangman {
    private static final String[] WORDS = {"java", "programming", "developer", "keyboard", "computer", "algorithm"};
    private static final int MAX_TRIES = 6;
    private final String wordToGuess;
    private final char[] guessedWord;
    private final Set<Character> guessedLetters = new HashSet<>();
    private int incorrectGuesses;

    public Hangman() {
        wordToGuess = WORDS[new Random().nextInt(WORDS.length)];
        guessedWord = new char[wordToGuess.length()];
        Arrays.fill(guessedWord, '_');
    }

    public boolean guessLetter(char letter) {
        if (!guessedLetters.add(letter)) return false;
        boolean correctGuess = false;
        for (int i = 0; i < wordToGuess.length(); i++) {
            if (wordToGuess.charAt(i) == letter) {
                guessedWord[i] = letter;
                correctGuess = true;
            }
        }
        if (!correctGuess) incorrectGuesses++;
        return correctGuess;
    }

    public boolean isGameOver() {
        return incorrectGuesses >= MAX_TRIES || isGameWon();
    }

    public boolean isGameWon() {
        return String.valueOf(guessedWord).equals(wordToGuess);
    }

    public int getIncorrectGuesses() {
        return incorrectGuesses;
    }

    public String getGuessedWord() {
        return new String(guessedWord);
    }

    public String getWord() {
        return wordToGuess;
    }
}
