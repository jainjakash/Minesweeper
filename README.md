## Minesweeper

# Description

Replicated the conventional Minesweeper game in Java with Java Swing. Features instruction panel, a reset option, as well as the ability to save your current game state and load previous game states.

<ins>Functionality of all the classes:</ins>

RunMinesweeper.java- sets up the JFrame and the various JPanels and JButtons. Implements action listeners for JButtons.

MineBoard.java- provides functionality for saving and loading the board(File I/O). Paints the view using paintComponent(). Updates the status of the player, and implements functionality for the reset button as well. Sets the model state with 10 mines in the constructor.

Minesweeper.java- This is the model state for my game. Stores all of the internal fields, including the state and visibility of each tile within the game. Functionality for setting the board and initial mine counts of all the tiles. Takes in the row and column of the tile that the user clicked on using playTurn(). From there, it calls the appropriate methods, including emptyExpansion() to expand beyond empty tiles and win() to check if the user has won.

GameTest.java- tests all of the functionality within Minesweeper.java here.

# Images

<img src="https://github.com/jainjakash/Minesweeper/blob/master/Images/GamePlay.png" width="400">





