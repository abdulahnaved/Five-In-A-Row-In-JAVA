# Five-in-a-Row Variant - Java Game

## Description

This is a Java-based variant of the classic five-in-a-row game. Two players take turns placing their symbols (**X** and **O**) on an **n × n** board. The goal is to get five adjacent symbols in a row, column, or diagonal. However, this variant includes a unique twist:

- If a player places **three adjacent** symbols, **one of their symbols is removed randomly**.
- If a player places **four adjacent** symbols, **two of their symbols are removed randomly**.

The game ends when:
- A player wins by placing five adjacent symbols.
- The board is full (resulting in a draw).

The board size is selectable, with options of **6×6, 10×10, and 14×14**.

## Features

- **Turn-based gameplay**: Players alternate placing their symbols.
- **Dynamic board size**: Play on a **6×6, 10×10, or 14×14** grid.
- **Random penalty rule**: Making 3 or 4 adjacent marks removes 1 or 2 symbols randomly.
- **Win/draw detection**: The game announces the winner or detects a draw.
- **Automatic restart**: A new game begins automatically after a match.

## How to Play

1. **Select Board Size**: Choose from 6×6, 10×10, or 14×14.
2. **Take Turns**: Players alternately place their **X** or **O** on an empty space.
3. **Watch for Penalties**: Making 3 or 4 adjacent symbols triggers a random removal.
4. **Win or Draw**:
   - Get 5 adjacent symbols to win.
   - If the board is full without a winner, the game ends in a draw.
5. **Play Again**: The game automatically restarts after finishing.

