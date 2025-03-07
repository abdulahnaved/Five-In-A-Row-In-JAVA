package com.mycompany.fiveinarow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * The MainGUI class represents the graphical user interface (GUI) for the Five-in-a-Row game.
 * It handles the game setup, player input, board display, and game status updates.
 */
public class MainGUI extends JFrame {
    
    /**
     * An array of available board sizes for the game.
     */
    private static final Integer[] BOARD_SIZES = {6, 10, 14};

    /**
     * The label that displays the current player's name.
     */
    private JLabel currentPlayerLabel;

    /**
     * The label that displays the game status (e.g., In Progress, Game Over).
     */
    private JLabel gameStatusLabel;

    /**
     * The label that displays the number of turns taken so far.
     */
    private JLabel turnCountLabel;

    /**
     * A 2D array of buttons representing the game board.
     */
    private JButton[][] buttons;

    /**
     * The game object that holds the logic for the Five-in-a-Row game.
     */
    private Game game;

    /**
     * The size of the game board (number of rows/columns).
     */
    private int boardSize;

    /**
     * The count of turns taken in the game.
     */
    private int turnCount;

    /**
     * Constructs a new MainGUI for the Five-in-a-Row game.
     * Sets the window title, size, layout, and initializes the game setup.
     */
    public MainGUI() {
        setTitle("Five-in-a-Row Game");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new CardLayout());

        // Start the game setup by showing the dialogs
        startGameSetup();
    }

    /**
     * Prompts the user to select a board size and enter player names.
     * Initializes the game with the selected options.
     */
    private void startGameSetup() {
        // Prompt user for board size using JOptionPane
        Integer selectedSize = (Integer) JOptionPane.showInputDialog(
                this,
                "Select Board Size:",
                "Board Size",
                JOptionPane.PLAIN_MESSAGE,
                null,
                BOARD_SIZES,
                BOARD_SIZES[0]
        );

        // If the user cancels, exit the program
        if (selectedSize == null) {
            System.exit(0);
        }

        boardSize = selectedSize;

        // Prompt for player names
        String player1Name = JOptionPane.showInputDialog(this, "Enter Player 1's name:", "Player 1");
        if (player1Name == null || player1Name.trim().isEmpty()) {
            player1Name = "Player 1";
        }

        String player2Name = JOptionPane.showInputDialog(this, "Enter Player 2's name:", "Player 2");
        if (player2Name == null || player2Name.trim().isEmpty()) {
            player2Name = "Player 2";
        }

        // Set up the game screen with the selected options
        setupGameScreen(boardSize, player1Name, player2Name);
    }

    /**
     * Sets up the game screen with the given board size and player names.
     * Initializes the board, buttons, labels, and layout for the game.
     * 
     * @param boardSize the size of the game board (number of rows/columns)
     * @param player1Name the name of player 1
     * @param player2Name the name of player 2
     */
    private void setupGameScreen(int boardSize, String player1Name, String player2Name) {
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());

        // Initialize the game with selected board size and player names
        game = new Game(boardSize, player1Name, player2Name);
        turnCount = 1;

        // Create game board panel with buttons
        JPanel boardPanel = new JPanel(new GridLayout(boardSize, boardSize));
        buttons = new JButton[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                JButton button = new JButton();
                button.setFont(new Font("Arial", Font.PLAIN, 20));
                button.setFocusPainted(false);
                button.addActionListener(new ButtonClickListener(i, j));
                buttons[i][j] = button;
                boardPanel.add(button);
            }
        }

        // Initialize the info panel with player's names for turn information
        currentPlayerLabel = new JLabel("Current Turn: " + game.getCurrentPlayer().getName());
        turnCountLabel = new JLabel("Turn Count: " + turnCount);
        gameStatusLabel = new JLabel("Game Status: In Progress");

        // Add components to the main game screen
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(currentPlayerLabel);
        infoPanel.add(turnCountLabel);
        infoPanel.add(gameStatusLabel);

        gamePanel.add(boardPanel, BorderLayout.CENTER);
        gamePanel.add(infoPanel, BorderLayout.SOUTH);

        // Set up the game screen as the main content
        setContentPane(gamePanel);
        revalidate();
        repaint();
    }

    /**
     * The ButtonClickListener class listens for clicks on the game board buttons.
     * It processes the move and updates the game state and GUI accordingly.
     */
    private class ButtonClickListener implements ActionListener {
        private final int row;
        private final int col;

        /**
         * Constructs a new ButtonClickListener for the specified button.
         * 
         * @param row the row of the clicked button
         * @param col the column of the clicked button
         */
        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        /**
         * Handles the button click event. Attempts to make a move, updates the board, 
         * and checks for a game over condition.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Attempt to make a move on the board at the clicked button's position
            if (game.makeMove(row, col)) {
                // Update the button text and disable it after a successful move
                updateBoard();

                // Check if the game is over after this move
                if (game.isGameOver()) {
                    String message;
                    if (game.getBoard().isFull()) {
                        message = "It's a draw!";
                        gameStatusLabel.setText("Game Status: Draw");
                    } else {
                        message = "Player " + game.getCurrentPlayer().getName() + " (" + game.getCurrentPlayer().getSymbol() + ") wins!";
                        gameStatusLabel.setText("Game Status: Finsihed");
                    }
                    showEndGameDialog(message);
                    return; // Exit since game is over
                }
                game.printBoard();
                // Switch to the next player and update UI elements
                game.nextTurn();
                turnCount++;
                currentPlayerLabel.setText("Current Turn: " + game.getCurrentPlayer().getName());
                turnCountLabel.setText("Turn Count: " + turnCount);
                gameStatusLabel.setText("Game Status: In Progress");
            } else {
                // If move was invalid (e.g., cell is already occupied), show a warning
                JOptionPane.showMessageDialog(MainGUI.this, "Invalid move! Try a different cell.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /**
     * Updates the game board display by setting the text and enabling/disabling buttons.
     */
    private void updateBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                String symbol = game.getBoard().getSymbolAt(i, j);
                buttons[i][j].setText(symbol != null ? symbol : "");
                buttons[i][j].setEnabled(symbol == null); // Disable button if occupied
            }
        }
    }

    /**
     * Displays a dialog box with the game result (win or draw) and asks if the user wants to start a new game.
     * 
     * @param message the message to display in the dialog (game result)
     */
    private void showEndGameDialog(String message) {
        int option = JOptionPane.showOptionDialog(
                this,
                message + "\nWould you like to start a new game?",
                "Game Over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{"Yes", "No"},
                "Yes"
        );
        if (option == JOptionPane.YES_OPTION) {
            startGameSetup();
        } else {
            System.exit(0);
        }
    }
}
