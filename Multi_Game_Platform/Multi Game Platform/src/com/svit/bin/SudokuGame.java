package com.svit.bin;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class SudokuGame extends JFrame {
    private JTextField[][] cells;
    private int[][] board;
    private final int BOARD_SIZE = 9;
    private final int CELL_SIZE = 50;

    public SudokuGame() {
        setTitle("Sudoku");
        setSize(CELL_SIZE * BOARD_SIZE, CELL_SIZE * BOARD_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cells = new JTextField[BOARD_SIZE][BOARD_SIZE];
        board = new int[BOARD_SIZE][BOARD_SIZE];

        initializeBoard();
        addCellsToBoard();
        initializeButtons();

        setVisible(true);
    }

    private void initializeBoard() {
        // Initialize the board with empty cells
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = 0;
            }
        }
    }

    private void addCellsToBoard() {
        JPanel panel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));

        Font font = new Font("Roboto", Font.BOLD, 20);
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                cells[i][j] = new JTextField();
                cells[i][j].setFont(font);
                cells[i][j].setHorizontalAlignment(JTextField.CENTER);
                panel.add(cells[i][j]);
            }
        }

        add(panel, BorderLayout.CENTER);
    }

    private void initializeButtons() {
        JButton solveButton = new JButton("Solve");
        JButton clearButton = new JButton("Clear");
        JButton generateButton = new JButton("Generate Puzzle");

        solveButton.addActionListener(e -> solveSudoku());
        clearButton.addActionListener(e -> clearBoard());
        generateButton.addActionListener(e -> generateSudokuPuzzle());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(solveButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(generateButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void generateSudokuPuzzle() {
        String[] difficulties = {"Easy", "Medium", "Hard"};
        String selectedDifficulty = (String) JOptionPane.showInputDialog(
                this,
                "Select the difficulty level:",
                "Difficulty",
                JOptionPane.QUESTION_MESSAGE,
                null,
                difficulties,
                difficulties[0]
        );

        if (selectedDifficulty != null) {
            clearBoard();
            generateRandomPuzzle(selectedDifficulty);
        }
    }

    private void generateRandomPuzzle(String difficulty) {
        // Adjust the number of initial cells based on difficulty level
        int numberOfCellsToKeep;
        switch (difficulty) {
            case "Easy":
                numberOfCellsToKeep = 30;
                break;
            case "Medium":
                numberOfCellsToKeep = 25;
                break;
            case "Hard":
                numberOfCellsToKeep = 20;
                break;
            default:
                numberOfCellsToKeep = 30;
                break;
        }

        Random random = new Random();
        for (int i = 0; i < numberOfCellsToKeep; i++) {
            int row = random.nextInt(BOARD_SIZE);
            int col = random.nextInt(BOARD_SIZE);
            int num = random.nextInt(9) + 1;

            // Check if the cell is already filled or if the number is valid
            while (board[row][col] != 0 || !isValidPlacement(row, col, num)) {
                row = random.nextInt(BOARD_SIZE);
                col = random.nextInt(BOARD_SIZE);
                num = random.nextInt(9) + 1;
            }

            board[row][col] = num;
            cells[row][col].setText(String.valueOf(num));
        }
    }

    private boolean isValidPlacement(int row, int col, int num) {
        // Check row and column
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }

        // Check 3x3 sub-grid
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i + startRow][j + startCol] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    private void solveSudoku() {
        if (solve(0, 0)) {
            updateGUI();
        } else {
            JOptionPane.showMessageDialog(this, "No solution found!");
        }
    }

    private boolean solve(int row, int col) {
        if (row == BOARD_SIZE) {
            return true; // Successfully solved the entire board
        }

        if (col == BOARD_SIZE) {
            return solve(row + 1, 0); // Move to the next row
        }

        if (board[row][col] != 0) {
            return solve(row, col + 1); // Cell is already filled, move to the next cell
        }

        for (int num = 1; num <= 9; num++) {
            if (isValidPlacement(row, col, num)) {
                board[row][col] = num;

                if (solve(row, col + 1)) {
                    return true; // Found a solution for the current state
                }

                board[row][col] = 0; // Backtrack and try the next number
            }
        }

        return false; // No valid number found for this cell, backtrack
    }

    private void updateGUI() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                cells[i][j].setText(String.valueOf(board[i][j]));
            }
        }
    }

    private void clearBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = 0;
                cells[i][j].setText("");
            }
        }
    }

    public static void main(String[] args) {
    	SwingUtilities.invokeLater(() -> new SudokuGame().setVisible(true));
    }
}
