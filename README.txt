=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: _______
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. File I/O:

  File I/O is used for two features. First, every time a game concludes the resulting score of
  the game, or how long the snake was at time of loss is written to a txt file. Every time a player loses
  the game displays the current highest score by reading the data stored in the txt. We also write the
  entirey of the game stateinto a separate text file, including the locations of all consumables and snake
  segments when the 1 key is pressed. The 2 key reads from the text file to recreate the game state described
  allowing for save and load functionality. The save and load feature especially, requires specialized logic
  for parsing the stored data, making it a suitably complex use of I/O

  2. JUnit Testing:

  Because both the gameboard class, which represents the game state, and the snake class,
  which represents the state of the controlled snake, are encapsulasted models that accurately represent
  the game state with no user input, I created tests in order to verify the functionality of critical methods
  in these classes. For example, I ensured that all my collision testing (w/ good apples, bad apples, walls
  and snake segments) was functioning using board test and ensured that my methods for snake behavior, such as
  getlonger and move were functioning using snaketest.

  3. 2-D Array:

  I use a 2-D array in order to store the positions of all objects in the grid at every tick. This makes sense,
  since the game itself can be represented as objects moving or existing on a 2-D array, with every opbject
  having a row and a column value that can be represented by its specialized location within the array
  The array is a boolean array where a false element means that no object exists in that section of the grid, and a
  true element means that an object does exist in that section of the grid. This grid is constantly updated and
  is used both for collision detection, and to ensure that fruits are not spawned on top of locations where
  fruit already exist, a critical functionality when multiple bad apples can be on-screen at the same time, and
  where a bad apple spawning on top of the snake can ruin a run. We use booleans because collision only requires
  thart we determine if an object exists there or not, meaning that storing an entire object would be redundant

  4. Subtyping/Inheritance

  The topmost layer of our inheritance hierarchy,  BaseObj contains fields common to all game objects.
  These include the x and y coordinates of the component, and a basic function for drawing which is implemented
  differently for each object. The abstract class Consumable is extended by the various classes of
  consumables in the game such as powerup (more length) and powerdown (kill you).The method wheneaten
  defined in the abstract class will define the result that occurs when the consumable is consumed
  and the method must be implemented in a distinct manner for each different consumable since each consumable
  as a different impact on the game state. We also have the class for snake extend the class for snake segment,
  as the snake itself is represented by a list of segments, and thus wants to have access to all segment methods.



=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

  Baseobj consists of a class that contains attributes common to each game object, ex: position x, position y, draw

  Game board is a class that models the game state using an array to represent the game grid and handles behaviors
  such as fruit spawning, collision detection, and save/load

  Consumable is a class that will be extended by all other power up varieties. contains wheneaten method

  Run Snake runs the game by extending runnable

  Segment represents one segment of the snake body and extends baseobj

  Snake represents the entire snake using an array list of segment objects

  Powerup and powerdown are consumables that grow the snake or kill you when eaten respectively

  BoardTest: tests gameboard methods and model reliability

  SnakeTest: tests Snake methods and model reliability


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

  none in particular, though I will admit that arriving on a method for efficiently tracking snake movement
  took a lot of though. I quickly realized that using velocities was inefficient, and the grid-based model
  I had in mind helped me devise the idea of adding elements to the front of the snake and subtracting from
  the tail to make it move.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  I think that my design is efficient and does a good job separating functionality among distinct classes. I think
  that if given the chance, I would probably merge the snake class and the gameboard clases into a single
  class for representing the game state for simplicity.


========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

  None
