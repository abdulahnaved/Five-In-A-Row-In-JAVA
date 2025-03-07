package com.mycompany.fiveinarow;

/**
 * The Player class represents a player in the Five-in-a-Row game.
 * Each player has a name and a symbol (either "X" or "O") which is used to mark their moves on the game board.
 * 
 * <p>This class provides the functionality to get and set a player's name and symbol.</p>
 */
public class Player {
    private String name;
    private String symbol;

    /**
     * Constructs a new Player with the specified name and symbol.
     * 
     * @param name the name of the player
     * @param symbol the symbol assigned to the player (e.g., "X" or "O")
     */
    public Player(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    /**
     * Returns the symbol associated with this player.
     * 
     * @return the player's symbol (e.g., "X" or "O")
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Returns the name of this player.
     * 
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this player.
     * 
     * @param name the new name for this player
     */
    public void setName(String name) {
        this.name = name;
    }
}
