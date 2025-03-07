package com.mycompany.fiveinarow;

/**
 * The Game class manages the core mechanics of the Five-in-a-Row game.
 * It handles the board state, player turns, and determines if the game has ended.
 * The game alternates turns between two players, and the first to align five of their
 * symbols in a row (vertically, horizontally, or diagonally) wins.
 * 
 * <p>This class is responsible for:</p>
 * <ul>
 *   <li>Placing a symbol on the board</li>
 *   <li>Checking for game over conditions (win or draw)</li>
 *   <li>Switching turns between the players</li>
 *   <li>Printing the current board state</li>
 * </ul>
 */
public class Game {
    private Board board;
    private Player player1, player2;
    private Player currentPlayer;
    private boolean gameOver = false;

    /**
     * Constructs a new game instance with the specified board size and player names.
     * 
     * @param boardSize the size of the board (board will be a square with this size)
     * @param player1Name the name of Player 1
     * @param player2Name the name of Player 2
     */
    public Game(int boardSize, String player1Name, String player2Name) {
        this.board = new Board(boardSize);
        this.player1 = new Player(player1Name, "X");
        this.player2 = new Player(player2Name, "O");
        this.currentPlayer = player1;
    }

    /**
     * Attempts to make a move by placing the current player's symbol at the given position
     * on the board.
     * 
     * <p>If the move is valid (the cell is empty), it updates the board and checks for win/draw.</p>
     * 
     * @param row the row index where the symbol should be placed
     * @param col the column index where the symbol should be placed
     * @return true if the move was successfully made, false if the cell is already occupied
     */
    public boolean makeMove(int row, int col) {
        Player current = getCurrentPlayer();
        if (board.placeSign(row, col, current.getSymbol())) {
            board.handleAdjacentCount(row, col, current.getSymbol());

            if (board.checkFiveInARow(current.getSymbol())) {
                System.out.println("Player " + current.getSymbol() + " wins!");
                gameOver = true;
                return true;
            } else if (board.isFull()) {
                System.out.println("It's a draw!");
                gameOver = true;
                return true;
            }
            return true;
        }
        return false;
    }

    /**
     * Switches the turn to the other player.
     * 
     * <p>This method alternates the current player between Player 1 and Player 2.</p>
     */
    public void nextTurn() {
        currentPlayer = (currentPlayer.getSymbol().equals("X")) ? player2 : player1;
    }

    /**
     * Returns the current player whose turn it is.
     * 
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Returns the player who is not the current player.
     * 
     * @return the other player
     */
    public Player getOtherPlayer() {
        return currentPlayer == player1 ? player2 : player1;
    }

    /**
     * Checks if the game is over (either due to a win or draw).
     * 
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Prints the current state of the game board to the console.
     */
    public void printBoard() {
        board.printBoard();
    }

    /**
     * Returns the board object representing the current game board.
     * 
     * @return the current board object
     */
    public Board getBoard() {
        return this.board;
    }
}
