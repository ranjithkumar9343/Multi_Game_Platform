package com.svit.bin;
import javax.swing.*;

public class GameDriver {

    public static void main(String[] args) {
        String[] options = {"Sudoku", "Tic Tac Toe"};
        int choice = JOptionPane.showOptionDialog(null,
                "Choose a game to play:",
                "MULTI-GAME PLATFORM",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == 0) {
            SwingUtilities.invokeLater(() -> {
                SudokuGame sudokuGame = new SudokuGame();
                sudokuGame.setVisible(true);
            });
        } else if (choice == 1) {
            SwingUtilities.invokeLater(() -> {
                TicTacToeGame ticTacToeGame = new TicTacToeGame();
                ticTacToeGame.setVisible(true);
            });
        } else {
            JOptionPane.showMessageDialog(null, "Invalid choice. Exiting the game.");
            System.exit(0);
        }
    }
}
