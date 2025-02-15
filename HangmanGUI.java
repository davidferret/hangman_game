import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HangmanGUI {
    private final Hangman game;
    private final JFrame frame;
    private final JLabel wordLabel, attemptsLabel;
    private final JTextField inputField;
    private final JButton guessButton;
    private final HangmanPanel hangmanPanel;

    public HangmanGUI() {
        game = new Hangman();
        frame = new JFrame("Hangman Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        wordLabel = new JLabel("Word: " + game.getGuessedWord());
        attemptsLabel = new JLabel("Attempts left: " + (6 - game.getIncorrectGuesses()));
        topPanel.add(wordLabel);
        topPanel.add(attemptsLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        hangmanPanel = new HangmanPanel();
        hangmanPanel.setPreferredSize(new Dimension(350, 350));
        frame.add(hangmanPanel, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputField = new JTextField(5);
        guessButton = new JButton("Guess");
        guessButton.addActionListener(e -> processGuess());
        inputPanel.add(inputField);
        inputPanel.add(guessButton);
        frame.add(inputPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void processGuess() {
        String input = inputField.getText().toLowerCase();
        if (input.length() == 1 && Character.isLetter(input.charAt(0))) {
            game.guessLetter(input.charAt(0));
            wordLabel.setText("Word: " + game.getGuessedWord());
            attemptsLabel.setText("Attempts left: " + (6 - game.getIncorrectGuesses()));
            hangmanPanel.repaint();
        }
        inputField.setText("");
        if (game.isGameOver()) endGame();
    }

    private void endGame() {
        JOptionPane.showMessageDialog(frame, game.isGameWon() ? "Congratulations! You guessed the word: " + game.getWord() : "Game Over! The word was: " + game.getWord());
        frame.dispose();
    }

    private class HangmanPanel extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            g.drawLine(50, 250, 150, 250);
            g.drawLine(100, 250, 100, 50);
            g.drawLine(100, 50, 180, 50);
            g.drawLine(180, 50, 180, 80);
            int incorrect = game.getIncorrectGuesses();
            if (incorrect >= 1) g.drawOval(165, 80, 30, 30);
            if (incorrect >= 2) g.drawLine(180, 110, 180, 170);
            if (incorrect >= 3) g.drawLine(180, 120, 160, 150);
            if (incorrect >= 4) g.drawLine(180, 120, 200, 150);
            if (incorrect >= 5) g.drawLine(180, 170, 160, 210);
            if (incorrect >= 6) g.drawLine(180, 170, 200, 210);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HangmanGUI::new);
    }
}
