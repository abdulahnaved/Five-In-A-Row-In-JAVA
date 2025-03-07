package com.mycompany.fiveinarow;

import java.util.ArrayList;
import java.util.Random;

/**
 * The Board class represents the game board in a Five-in-a-Row game.
 * It is responsible for handling the placement of player's symbols, checking for winning conditions,
 * and managing adjacent symbol counts for special game rules.
 */
public class Board {
    private int size;
    private String[][] board;
    private Random random;

    /**
     * Constructs a new game board with the specified size.
     * 
     * @param size the size of the board (it will be a size x size grid)
     */
    public Board(int size) {
        this.size = size;
        this.board = new String[size][size];
        this.random = new Random();
    }

    /**
     * Places a player's symbol on the board at the specified row and column.
     * 
     * @param row the row index where the symbol will be placed
     * @param col the column index where the symbol will be placed
     * @param symbol the symbol of the player ("X" or "O")
     * @return true if the symbol was successfully placed, false if the cell is already occupied
     */
    public boolean placeSign(int row, int col, String symbol) {
        if (board[row][col] == null) { // Check if cell is empty
            board[row][col] = symbol;
            return true;
        }
        return false; // Cell is occupied
    }

    /**
     * Checks if a player has five consecutive symbols in any direction (horizontal, vertical, or diagonal).
     * 
     * @param symbol the player's symbol to check for a five-in-a-row
     * @return true if the player has five consecutive symbols in a row, false otherwise
     */
    public boolean checkFiveInARow(String symbol) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (checkDirection(i, j, 1, 0, symbol) || 
                    checkDirection(i, j, 0, 1, symbol) || 
                    checkDirection(i, j, 1, 1, symbol) || 
                    checkDirection(i, j, 1, -1, symbol)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if there are five consecutive symbols in a specific direction from a starting position.
     * 
     * @param row the starting row
     * @param col the starting column
     * @param dRow the row direction (1 for down, -1 for up)
     * @param dCol the column direction (1 for right, -1 for left)
     * @param symbol the player's symbol
     * @return true if five consecutive symbols are found in the direction, false otherwise
     */
    private boolean checkDirection(int row, int col, int dRow, int dCol, String symbol) {
        int count = 0;
        for (int k = 0; k < 5; k++) {
            int r = row + k * dRow, c = col + k * dCol;
            if (r >= 0 && r < size && c >= 0 && c < size && board[r][c] != null && board[r][c].equals(symbol)) {
                count++;
            } else {
                break;
            }
        }
        return count == 5;
    }

    /**
     * Handles the adjacent count rule where specific conditions trigger the removal of random symbols.
     * 
     * @param row the row of the recently placed symbol
     * @param col the column of the recently placed symbol
     * @param symbol the player's symbol ("X" or "O")
     */
    public void handleAdjacentCount(int row, int col, String symbol) {
        int adjacentCount = countAdjacent(row, col, symbol);
        System.out.println(adjacentCount);
        if (adjacentCount == 3) {
            removeRandomSigns(symbol, 1); // Remove 1 symbol if 3 adjacent symbols
        } else if (adjacentCount == 4) {
            removeRandomSigns(symbol, 2); // Remove 2 symbols if 4 adjacent symbols
        }
    }

    /**
     * Counts the maximum number of adjacent symbols in any direction (horizontal, vertical, or diagonal).
     * 
     * @param row the row of the symbol to start counting from
     * @param col the column of the symbol to start counting from
     * @param symbol the player's symbol ("X" or "O")
     * @return the maximum number of adjacent symbols in any direction
     */
    private int countAdjacent(int row, int col, String symbol) {
        int maxAdjacentCount = 0;
        int[][] directions = {{1, 0}, {0, 1}, {1, 1}, {1, -1}}; // Down, Right, Diagonal Right-Down, Diagonal Left-Down

        for (int[] direction : directions) {
            int dRow = direction[0];
            int dCol = direction[1];

            // Count in the positive direction (forward)
            int forwardCount = countInDirection(row, col, dRow, dCol, symbol);

            // Count in the negative direction (backward)
            int backwardCount = countInDirection(row, col, -dRow, -dCol, symbol);

            // Total count is the sum of both directions minus one (to avoid double-counting the initial cell)
            int totalCount = forwardCount + backwardCount - 1;

            // Update maxAdjacentCount if this direction has a higher count
            maxAdjacentCount = Math.max(maxAdjacentCount, totalCount);
        }

        return maxAdjacentCount;
    }

    /**
     * Counts the number of consecutive symbols in a specific direction starting from a given position.
     * 
     * @param row the starting row
     * @param col the starting column
     * @param dRow the row direction (positive for down, negative for up)
     * @param dCol the column direction (positive for right, negative for left)
     * @param symbol the player's symbol to count
     * @return the number of consecutive symbols in the specified direction
     */
    private int countInDirection(int row, int col, int dRow, int dCol, String symbol) {
        int count = 0;
        int r = row;
        int c = col;

        // Traverse in the specified direction while within bounds and matching the symbol
        while (r >= 0 && r < size && c >= 0 && c < size && board[r][c] != null && board[r][c].equals(symbol)) {
            count++;
            r += dRow;
            c += dCol;
        }

        return count;
    }

    /**
     * Removes a specified number of random symbols from the board.
     * 
     * @param symbol the symbol to remove (either "X" or "O")
     * @param removeCount the number of symbols to remove
     */
    private void removeRandomSigns(String symbol, int removeCount) {
        ArrayList<int[]> positions = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (symbol.equals(board[i][j])) {
                    positions.add(new int[]{i, j});
                }
            }
        }

        // Ensure we only remove a valid number of signs
        for (int i = 0; i < removeCount && !positions.isEmpty(); i++) {
            int[] pos = positions.remove(random.nextInt(positions.size()));
            board[pos[0]][pos[1]] = null;
        }
    }

    /**
     * Checks if the game board is full (no empty cells).
     * 
     * @return true if the board is full, false otherwise
     */
    public boolean isFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == null) return false;
            }
        }
        return true;
    }

    /**
     * Prints the current state of the board to the console.
     * Empty cells are represented by a dot ('.').
     */
    public void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print((board[i][j] != null ? board[i][j] : ".") + " ");
            }
            System.out.println();
        }
    }

    /**
     * Returns the symbol at the specified position on the board.
     * 
     * @param i the row index
     * @param j the column index
     * @return the symbol at the given position, or null if the cell is empty
     */
    public String getSymbolAt(int i, int j) {
        return this.board[i][j];
    }
}
