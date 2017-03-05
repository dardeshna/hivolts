                                hivolts README

Introduction
--------------------

This program recreates the PLATO game hivolts using a more modern programming
language, Java.  It was written with the intent of replicating the original game
with a few minor modifications and enhancements.

Specification
--------------------

This program meets the requirements presented by the assignment.  Broadly
speaking, it creates a game that is fully functional and meets all of the
gameplay requirements.  Without listing everything, some of the notable
requirements that are met include having a functioning interface, gameboard,
player, mhos and fence, as well as working logic to drive the game.  Also, the
game displays the players score and stats, stopping and asking if the user would
like to play again when they lose, very similar to the original game.  Lastly,
while still maintaining the feel of the original game and graphics, animations
have been added so entities move smoothly and fade out appropriately.  This,
along with cleaner graphics and more responsive feedback thanks to newer
technology, have helped put a modern spin on this 1970's computer game.

Errors
--------------------

There are no notable errors in the program that have been found thus far.  One
may consider it hard to win, but that is not an error in the program itself!

Overview of Code
--------------------

Oh boy.  Broadly speaking, this code tries to make use of classes to organize
the program logically and make the structure of the program easy to make out.
The major classes include the main Hivolts class, Board class, HivoltsPanel
class, and Updater class.  The Hivolts class is the main class and deals with
operations such as starting the game, winning and losing, restarting the game,
and keeping track of score.  It also contains the KeyListeners for the game and
determines when each KeyListener is active.  The Hivolts class has a Board
class, which contains a 2-Dimensional array of entities, including blank
squares, mhos, fences and the player.  This class also has the logic for moving
the player, moving the mhos, and removing objects from the board if necessary.
It will also toggle gameOver if the player dies, and gameWon if the player wins.
The Hivolts class also contains a HivoltsPanel, which is a subclass of JPanel.
The HivoltsPanel manages all the graphics in the program, and paints various
objects when appropriate.  Lastly, the Hivolts class contains the Updater class.
This class updates the HivoltsPanel fifty times a second allowing for animations
of the entities on the board.

Major classes aside, there some more minor classes as well.  For one, there is
the Entity class and its respective subclasses, including the Player, Mho and
Fence classes.  These classes contain the data for a single Entity, such as its
x and y coordinates and its numerical ID in the case of mhos.  They also contain
a method called paint() to paint the entity when appropriate.  Additionally,
there is the Animation class and its respective subclasses.  These classes
represent different types of animations and contain the data needed for each
type of animation, as well as the Entity to animation.  These are held in the
Updater class, which updates each animation and in turn, that animation's
Entity.

This class based structure of the program tries to follow OOP principles and
make the code easier to understand as well as more efficient to execute.

Major Challenges
--------------------

There were a lot of challenges along the way for this program, but there were
three notable ones that stood out.  The first was how to go about starting the
program.  I was not really sure how to start out, so I just started coding
everything in one class.  It actually worked a lot better than I expected that
approach would work, and I had a 75% functional game after two days.  However,
it made finding bugs a nightmare, and the code was terrible.  I decided to
basically take all my methods and sort them out into different classes, and this
is how I ended up with the basic structure of the program as it is now.

The second major challenge was getting the mho logic to work.  After some time,
okay actually a lot of time, it turned out I had read the assignment's
description of the mho logic wrong, and mhos were not allowed to eat each other.
Once this was fixed, the mho logic worked very well and I did not have any
further issues.

The last major challenge was getting animations to work. My approach required
the use of another thread to run in the background and update the graphics
continually.  Once making a second thread was sorted out, it was a lot of going
back into the old code, cleaning it up, and figuring out where the animations
needed to happen.  Once that was done, the actual animation code was pretty easy
to write, and the program was essentially done.

Acknowledgements
--------------------

I worked on this project alone, so there wasn't really anyone else working on
the project.

For this project especially, I would really like to thank the great resource
know as stackoverflow.com, which is an online community of programmers who have
asked and answered so many questions that something has almost always been asked
already.  This site saved me a lot of time when trying to understand new
concepts, such as threads and the Java swing library.  As always, I would also
like to thank my family for supporting me while I work on time-consuming
projects such as this.