=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: elize
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Arrays:
  - The bugs are stored in a 2D array given their grid-like display on the screen.
  - The array is of type Bug, and stores either a red, blue, or green bug.
  - The array is iterated through when the bugs are being added, drawn, and during each 'tick' to check
  if they should be in motion or have been hit
  - The array is private to the GameCourt class (encapsulation) and only one is used.

  2. Collections
  - Two linked lists are used which are both significant to the game
    - one stores a list of random numbers that is translated to bugs in the grid.
    These are the bugs that are chosen to fall. While the order/location does not matter,
    the code constantly adds and removes values from anywhere in the list which means that
    linked lists are faster.
    - the other contains 35 of the fighter's "bullets." It is used to create a
    list iterator that goes through each of the bullets and is re-created each time it reaches
    its end to keep going through the list (position matters which means linked lists are generally
    better to optimize code). This ensures that the list isn't infinite which would be a problem
    as the code iterates through this list with each tick to check if a bug has been hit by any of the bullets.
  - Both are private to the GameCourt class.
  - For-each loops are used to iterate through during each tick
  - Arrays, etc. can not be used as the size of one list is constantly changing and the other uses an iterator

  3. Inheritance and Subtyping
  - The game's contains two abstract classes, GalagaObj and Bug.
  - Almost all game components extend galaga object. All override the draw method. Other overriden methods
  include hit() (for fire objects which are not treated as squares as with the rest of the game objects)
  and restart (for Bugs as their motion at the top of the screen changes their starting point).
  - GreenBug, BlueBug, and RedBug all extend Bug. Methods that are overriden include mainly fall() and beenHit().
  Each bug moves in a different matter (red and blue move side to side by different amounts and green just falls straight
  down at a faster velocity). Unlike the others, the green bugs are not killed the first time they are hit, but instead
  change colors.
  - Dynamic dispatch is present amongst the bug objects. The bug gird contains a bunch of objects with
  static type Bug and dynamic type either red, green, or blue bug (which determines what 'version' of each
  method is called as this varies from bug to bug).

  4. JUnit Testable Component
  - Includes a total of 6 main tests (which each test multiple aspects of the game
  and specifically GameCourt which is a model of the game).
    - gameTestFirst, gameTest second: comprehensive tests of the game's main functionality
    - lastBugTest: tests for when a single bug remains
    - shootBugsTest: test for when bugs get shot on their way down
    - testMove: tests bug motion at top of screen/position is properly updated for returning bugs
  - GameCourt has various helped functions to separate code and make it testable
  (including within tick function).
  - Tests mainly use calls such as assertEquals(), and assertTrue/False();
  - Multiple edge cases were tested, including:
    - The bug being right next to but not touching the fighter
    - The fighter's fire being right next to but not touching the bug
    - New bugs being selected when there's only one bug left
    - new bugs being set when all three bugs are killed on their way down
      (simultaneously and one by one).

===============================
=: File Structure Screenshot :=
===============================
- Include a screenshot of your project's file structure. This should include
  all of the files in your project, and the folders they are in. You can
  upload this screenshot in your homework submission to gradescope, named 
  "file_structure.png".

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  - Game: code to run the game.
  - RunGalaga: Sets up frame to run the Game.
  - GameCourt: Primary game logic that allows for all the game objects to interact with each other
  in a time-based manner.
  - GalagaObj (abstract): Abstract class containing shared functionality of most game objects
  - Fire: Extends GalagaObj. Draws a bullet-like element that can be shot by the fighter or a random bug.
  - Fighter: Extends GalagaObj. Displays player's game piece that moves horizontally at the bottom of the
  screen and can shoot at bugs.
  - Bug (abstract): Extends GalagaObj. Contains the basic functionality of bug objects.
  - RedBug: Extends Bug. In charge of the appearance and specific features of the red bugs.
  - GreenBug: Extends Bug. In charge of the appearance and specific features of the green bugs.
  - BlueBug: Extends Bug. In charge of the appearance and specific features of the blue bugs.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  One of the biggest challenges was dealing with choosing random falling bugs when there were three or fewer
  bugs remaining. The code used for most of the game uses a while loop that checks for bugs that are alive
  and not falling which risked falling into an infinite loop for some cases with fewer bugs. To work around this,
  for three or fewer bugs, the while loop only checked to pick an existing bug and if that bug was already in motion,
  it just moved on rather that falling into an infinite loop. I also added an if statement to make sure that at least
  one bug is always being chosen when nearing the end game.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
  One thing I would change is I would make individual height and widths for each piece. While they are
  all roughly the same size, there is some variance which I would have liked to account for in the hit function.
  - Something else I would change is how I reset the bugs at the top of the screen. Because they all fall at different
  rates, the range in which I check for their position would be more accurate if they were different. At the moment,
  this makes it difficult for me to easily rearrange the starting height of the bugs (sometimes hit their original
  position at the wrong interval.
  - I also rewrite my fighter shooting code to shoot less frequently, to make the game slightly more challenging.


========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.
  I followed many of the features of the original galaga game and designed the
  game pieces after the original ones.
