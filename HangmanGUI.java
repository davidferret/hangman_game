import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class HangmanGUI {
    private static final String[] WORDS = {"java", "programming", "developer", "keyboard", "computer", "algorithm"};
    private static final int MAX_TRIES = 6;
    private String wordToGuess;
    private char[] guessedWord;
    private Set<Character> guessedLetters = new HashSet<>();
    private int attemptsLeft;

    private JFrame frame;
    private JLabel wordLabel, attemptsLabel;
    private JTextField inputField;
    private JButton guessButton;
    private JTextArea messageArea;

    public HangmanGUI() {
        wordToGuess = WORDS[new Random().nextInt(WORDS.length)];
        guessedWord = new char[wordToGuess.length()];
        Arrays.fill(guessedWord, '_');
        attemptsLeft = MAX_TRIES;

        frame = new JFrame("Hangman Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());

        wordLabel = new JLabel("Word: " + String.valueOf(guessedWord));
        frame.add(wordLabel);
        
        attemptsLabel = new JLabel("Attempts left: " + attemptsLeft);
        frame.add(attemptsLabel);

        inputField = new JTextField(5);
        frame.add(inputField);

        guessButton = new JButton("Guess");
        guessButton.addActionListener(new GuessHandler());
        frame.add(guessButton);

        messageArea = new JTextArea(5, 30);
        messageArea.setEditable(false);
        frame.add(new JScrollPane(messageArea));

        frame.setVisible(true);
    }

    private class GuessHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String input = inputField.getText().toLowerCase();
            if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                messageArea.append("Invalid input. Enter a single letter.\n");
                return;
            }
            
            char guess = input.charAt(0);
            if (guessedLetters.contains(guess)) {
                messageArea.append("You already guessed that letter. Try another.\n");
                return;
            }
            
            guessedLetters.add(guess);
            boolean correctGuess = false;
            
            for (int i = 0; i < wordToGuess.length(); i++) {
                if (wordToGuess.charAt(i) == guess) {
                    guessedWord[i] = guess;
                    correctGuess = true;
                }
            }
            
            if (!correctGuess) {
                attemptsLeft--;
                messageArea.append("Wrong guess!\n");
            }
            
            wordLabel.setText("Word: " + String.valueOf(guessedWord));
            attemptsLabel.setText("Attempts left: " + attemptsLeft);
            inputField.setText("");
            
            if (String.valueOf(guessedWord).equals(wordToGuess)) {
                JOptionPane.showMessageDialog(frame, "Congratulations! You guessed the word: " + wordToGuess);
                frame.dispose();
            } else if (attemptsLeft == 0) {
                JOptionPane.showMessageDialog(frame, "Game Over! The word was: " + wordToGuess);
                frame.dispose();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HangmanGUI::new);
    }
}
