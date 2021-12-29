=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: akashj
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Arrays- I store the state and visibility of each tile in the MineBoard in their respective 2D arrays,
  which are called stateArray and visibilityArray. Both are internal fields in my model, or Minesweeper.java.

  2. Recursion- When the user clicks on a blank tile, the rules of Minesweeper dictate that the program should keep
  expanding until it reaches a boundary of all numbers. In order to do this, I have a recursive call within the
  emptyExpansion() function on all of the adjacent tiles to the empty tile in question.

  3. JUnit Testable Component- I wrote test cases for my model class, Minesweeper.java, in GameTest.java. This tests the
  functionality of the model, and that it operates independently of the view and controller.

  4. File I/O- I write and read from two text files, gameState.java and gameVisibility.java. These files store the
  aforementioned stateArray and visibilityArray, respectively. Users can save a version of a game and load the most
  previously saved version using the JButtons provided.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

  RunMinesweeper.java- sets up the JFrame and the various JPanels and JButtons. Implements action listeners for
  JButtons.

  MineBoard.java- provides functionality for saving and loading the board(File I/O). Paints the view
  using paintComponent(). Updates the status of the player, and implements functionality for the reset button as well.
  Sets the model state with 10 mines in the constructor.

  Minesweeper.java- This is the model state for my game. Stores all of the internal fields, including the state and
  visibility of each tile within the game. Functionality for setting the board and initial mine counts of all the tiles.
  Takes in the row and column of the tile that the user clicked on using playTurn(). From there, it calls the
  appropriate methods, including emptyExpansion() to expand beyond empty tiles and win() to check if the user has won.

  GameTest.java- tests all of the functionality within Minesweeper.java here.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

  I initially begin implementing the MineBoard as a 2D array of JButtons for aesthetic purposes.
  In this way, I envisioned being able to set the icons of different buttons to various images that I had
  found. However, I ran into a java.awt.AWTEventMulticaster.componentMoved() error and was never able to resolve the
  issue. Instead, I had to change my approach to drawing horizontal and vertical lines to create the appearance of tiles.


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  Yes, I clearly have separated my classes using the MVC framework described in class. Private state is encapsulated
  well within Minesweeper.java. If given the chance, I would try to combine my stateArray and visibilityArray into one
  object from the Collections library, or possibility even create a Tile object that I could just directly add to my
  mineBoard.



========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.
