package com.mycompany.fiveinarow;

/**
 * The entry point for the Five-in-a-Row game application.
 * This class initializes and displays the main graphical user interface (GUI) 
 * where players can interact and play the game.
 * 
 * <p>The Five-in-a-Row game, involves players taking turns
 * to place their symbols on a grid. The objective is to be the first player to 
 * align five of their symbols consecutively in any direction (vertically, horizontally, or diagonally).</p>
 * 
 * <p>This class contains only the main method, which launches the game GUI.</p>
 */
public class FiveInARow {

    /**
     * The main method serves as the starting point of the Five-in-a-Row game application.
     * 
     * <p>It creates an instance of {@link MainGUI} and makes it visible, 
     * allowing players to start a new game. The game settings and board 
     * layout are initialized in the GUI constructor.</p>
     *
     * @param args command-line arguments (not used in this application)
     */
    public static void main(String[] args) {
        MainGUI game = new MainGUI();
        game.setVisible(true);
    }
}

