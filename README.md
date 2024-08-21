=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Game Overview and Instructions :=
===================

Instructions: This game follows the basic structure of the classic Galaga arcade game.

Avoid the bugs at the topic of the screen that will fall in groups of three. If you get hit by a bug or one of its bullets you lose and the game is over.

Press the left and right arrow keys to move your fighter. Press the up arrow key to shoot and hit the bugs. Note that the green bugs must be hit twice to die. To win you have to kill all of the bugs.

Press the space bar to begin playing or pause/unpause at any time. Press the reset button or r key to restart at any time.

=========================
=: Classes Overview :=
=========================

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
(All classes can be found in the src folder)

========================
=: External Resources :=
========================

Original Galaga arcade game
