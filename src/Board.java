import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Contains the gameboard, entities, and logic to move the entities
 */
public class Board {

	public boolean gameOver = false;
	public boolean gameWon = false;

	private Entity[][] board;
	private ArrayList<Mho> mhos;

	private Player player;
	
	private Updater updater;

	/**
	 * Creates and fills a new Board
	 */
	public Board() {

		board = new Entity[Hivolts.BOARD_SIZE][Hivolts.BOARD_SIZE];

		//Set all squares to blank squares
		for (int i = 0; i < Hivolts.BOARD_SIZE; i++) {
			for (int j = 0; j < Hivolts.BOARD_SIZE; j++) {
				board[i][j] = new Entity(i, j);
			}
		}

		//Add fences on the border
		for (int i = 0; i < Hivolts.BOARD_SIZE; i++) {
			board[0][i] = new Fence(0, i);
			board[Hivolts.BOARD_SIZE-1][i] = new Fence(Hivolts.BOARD_SIZE-1, i);
			if (i != 0 && i != Hivolts.BOARD_SIZE-1) {
				board[i][0] = new Fence(i, 0);
				board[i][Hivolts.BOARD_SIZE-1] = new Fence(i, Hivolts.BOARD_SIZE-1);
			}	
		}

		//Add twenty random fences
		for (int i = 0; i < 20; i++) {
			int x = (int)(Math.random()*Hivolts.BOARD_SIZE-2)+1;
			int y = (int)(Math.random()*Hivolts.BOARD_SIZE-2)+1;
			board[x][y] = new Fence(x, y);
		}

		//Add twelve random mhos
		mhos = new ArrayList<Mho>();
		for (int i = 0; i < Hivolts.NUMBER_OF_MHOS; i++) {
			int x = (int)(Math.random()*Hivolts.BOARD_SIZE);
			int y = (int)(Math.random()*Hivolts.BOARD_SIZE);

			if (board[x][y].getValue() == Hivolts.BLANK_SQUARE) {
				mhos.add(new Mho(x, y, i)); //x, y, id
				board[x][y] = mhos.get(i);
			}
			else {
				i--;
			}
		}

		//Add one random player
		for (int i = 0; i < 1; i++) {
			int x = (int)(Math.random()*Hivolts.BOARD_SIZE);
			int y = (int)(Math.random()*Hivolts.BOARD_SIZE);

			if (board[x][y].getValue() == Hivolts.BLANK_SQUARE) {
				player = new Player(x, y);
				board[x][y] = player;
			}
			else {
				i--;
			}
		}
	}

	/**
	 * Sets the updater in use
	 * @param u the updater
	 */
	public void setUpdater(Updater u) {
		updater = u;
	}
	
	/**
	 * Moves an entity to a location, calls a MotionAnimation
	 * @param e the entity
	 * @param x the x coordinate of the location
	 * @param y the y coordinate of the location
	 */
	private void moveEntity(Entity e, int x, int y) {
				
		updater.addAnimation(new MotionAnimation(e, 7*isqrt((e.getX()-x)*(e.getX()-x)+(e.getY()-y)*(e.getY()-y)), e.getX(), e.getY(), x, y));		
		
		board[x][y] = e;
		e.setX(x);
		e.setY(y);
	}
	
	/**
	 * Moves the player a specified direction and number of spaces
	 * @param deltaX the number of spaces to move in the x direction
	 * @param deltaY the number of spaces to move in the y direction
	 */
	public void movePlayer(int deltaX, int deltaY) {
		int x1 = player.getX()+deltaX;
		int y1 = player.getY()+deltaY;
		
		//If position is empty or where the player currently is
		if (board[x1][y1].getValue() == Hivolts.BLANK_SQUARE || board[x1][y1].getValue() == Hivolts.PLAYER_SQUARE) {

			moveEntity(board[x1][y1], player.getX(), player.getY());
			moveEntity(player, x1, y1);

			moveMhos();

		}
		
		//Otherwise, the player is killed by moving into a fence or mho
		else {
			setBlankSquare(player, true, x1, y1);
			setBlankSquare(board[x1][y1], false, 0, 0);
			gameOver = true;
		}

	}
	
	/**
	 * Jumps the player to a random spot on the board that is not an electric fence
	 */
	public void jumpPlayer() {

		boolean done = false;
		while (!done) {
			int x1 = (int) (Math.random()*Hivolts.BOARD_SIZE);
			int y1 = (int) (Math.random()*Hivolts.BOARD_SIZE);
			if (board[x1][y1].getValue() == Hivolts.BLANK_SQUARE) {

				moveEntity(board[x1][y1], player.getX(), player.getY());
				moveEntity(player, x1, y1);

				done = true;

			}
			else if (board[x1][y1].getValue() == Hivolts.MHO_SQUARE){

				//Fade away player while moving player towards the mho
				setBlankSquare(player, true, x1, y1);

				gameOver = true;

				done = true;
			}
		}
	}

	/**
	 * Moves a mho a specified direction and number of spaces
	 * @param deltaX the number of spaces to move in the x direction
	 * @param deltaY the number of spaces to move in the y direction
	 * @param mho
	 */
	private void moveMho(int deltaX, int deltaY, Mho mho) {

		int x1 = mho.getX()+deltaX;
		int y1 = mho.getY()+deltaY;
		
		switch(board[x1][y1].getValue()) {
		
		case Hivolts.MHO_SQUARE:
			//Remove mho from mhos
			killMho(mho, x1, y1);
			break;
		case Hivolts.FENCE_SQUARE:
			//Remove mho from mhos and remove fence from board
			killMho(mho, x1, y1);
			setBlankSquare(board[x1][y1], false, 0, 0);
			break;
		case Hivolts.PLAYER_SQUARE:
			//Move mho to player and gameOver
			setBlankSquare(player, false, 0, 0);
			moveEntity(mho, x1, y1);
			gameOver = true;
			break;
		default:
			//Switch mho and blank square (effectively move mho to the square)
			moveEntity(board[x1][y1], mho.getX(), mho.getY());			
			moveEntity(mho, x1, y1);
			break;
			
		}
		
	}
	
	/**
	 * Moves all the remaining mhos
	 */
	private void moveMhos() {

		ArrayList<Mho> unmovedMhos = new ArrayList<Mho>(mhos);

		//Start at position 0
		int i = 0;
		int lastLength = unmovedMhos.size();

		while (unmovedMhos.size() > 0) {

			Mho mho = unmovedMhos.get(i); //Get the mho at position i

			boolean hasMoved = false;

			int x = distance(mho, player)[0], y = distance(mho, player)[1];
			int directionX = getSign(x), directionY = getSign(y);

			int diagonalValue = board[mho.getX()+directionX][mho.getY()+directionY].getValue();
			int horizontalValue = board[mho.getX()+directionX][mho.getY()].getValue();
			int verticalValue = board[mho.getX()][mho.getY()+directionY].getValue();

			//Directly horizontal
			if (y == 0 && horizontalValue != Hivolts.MHO_SQUARE) {
				moveMho(directionX, 0, mho);
				hasMoved = true;

			}
			//Directly vertical
			else if (x == 0 && verticalValue != Hivolts.MHO_SQUARE) {
				moveMho(0, directionY, mho);
				hasMoved = true;
			}

			//Diagonally towards you, empty square or player
			else if (diagonalValue == Hivolts.BLANK_SQUARE || diagonalValue == Hivolts.PLAYER_SQUARE) {
				moveMho(directionX, directionY, mho);
				hasMoved = true;
			}
			// Horizontally towards you, empty square or player
			else if ((horizontalValue == Hivolts.BLANK_SQUARE || horizontalValue == Hivolts.PLAYER_SQUARE) && Math.abs(x) >= Math.abs(y)) {
				moveMho(directionX, 0, mho);
				hasMoved = true;
			}
			// Vertically towards you, empty square or player
			else if ((verticalValue == Hivolts.BLANK_SQUARE || verticalValue == Hivolts.PLAYER_SQUARE) && Math.abs(y) >= Math.abs(x)) {
				moveMho(0, directionY, mho);
				hasMoved = true;
			}

			//Diagonally towards you, try fences
			else if (diagonalValue == Hivolts.FENCE_SQUARE) {
				moveMho(directionX, directionY, mho);
				hasMoved = true;
			}
			// Horizontally towards you, try fences
			else if (horizontalValue == Hivolts.FENCE_SQUARE && Math.abs(x) >= Math.abs(y)) {
				moveMho(directionX, 0, mho);
				hasMoved = true;
			}
			// Vertically towards you, try fences
			else if (verticalValue == Hivolts.FENCE_SQUARE && Math.abs(x) <= Math.abs(y)) {
				moveMho(0, directionY, mho);
				hasMoved = true;
			}  

			//Otherwise, another mho has to be in the way
			else {
				//Do nothing!
			}
			
			if (hasMoved) {
				unmovedMhos.remove(i); //If the mho at position i is moved, remove it from unmovedMhos
			}
			else {
				i++; //Make position i one more and ignore the mho if it is not moved
			}

			//If at the end of the ArrayList, meaning there are still unmoved mhos
			if (i == unmovedMhos.size()) {
				//If something has been removed in the last pass, restart at beginning and try to move mhos that are still left
				if (unmovedMhos.size() < lastLength) {
					lastLength = unmovedMhos.size();
					i = 0;
				}
				//Otherwise it is useless to try again, terminate loop
				else  {
					break;
				}
			}

		}

		if (mhos.size() == 0) {
			gameWon = true;
		}

	}
	
	/**
	 * Remove a mho from the mhos ArrayList and set its square as blank
	 * @param mho the mho
	 * @param x the x coordinate the mho moves to that kills it
	 * @param y the y coordinate the mho moves to that kills it
	 */
	private void killMho(Mho mho, int x, int y) {

		int id = mho.getID();

		for (int i = 0; i < mhos.size(); i++) {
			if (mhos.get(i).getID() == id) {
				mhos.remove(i); //Search in mhos by ID and remove
			}
		}
		
		//Mhos should move and fade
		setBlankSquare(mho, true, x, y);

	}

	/**
	 * Sets a square in the board to empty and fade out whatever is on it
	 * @param e the entity to remove
	 * @param shouldMove whether the entity is moving anywhere
	 * @param x if the entity if moving, the x coordinate the entity moves to that kills it
	 * @param y if the entity if moving, the y coordinate the entity moves to that kills it
	 */
	private void setBlankSquare(Entity e, boolean shouldMove, int x, int y) {
		//Don't waste time animating blank squares
		if (e.getValue() != Hivolts.BLANK_SQUARE) {
			if (shouldMove) {
				updater.addAnimation(new MovingFadeAnimation(e, 25, e.getX(), e.getY(), x, y));
			}
			else {
				updater.addAnimation(new FadeAnimation(e, 25));
			}
		}
		else {
			delete(e);
		}

	}
	
	/**
	 * Deletes an entity from the board by setting its location to a blank square once the entity has faded
	 * @param e the Entity
	 */
	public void delete(Entity e) {
		board[e.getX()][e.getY()] = new Entity(e.getX(), e.getY());
	}
	
	/**
	 * Paints an entity at a location
	 * @param i the x coordinate of the entity
	 * @param j the y coordinate of the entity
	 * @param g the Graphics object to paint on
	 */
	public void paintEntity(int i, int j, Graphics g) {
		board[i][j].paint(g);
	}
	
	/**
	 * Returns the distance between entities
	 * @param mho the mho
	 * @param player the player
	 * @return an array of the x and y distances
	 */
	private int[] distance(Entity mho, Entity player) {
		int x = player.getX()-mho.getX();
		int y = player.getY()-mho.getY();

		return new int[]{x, y};
	}

	/**
	 * Gets the sign of an integer
	 * @param x
	 * @return 1 if positive, -1 if negative, 0 if 0
	 */
	private int getSign(int x) {

		if (x == 0) {
			return 0;
		}
		else {
			return Math.abs(x) / x;
		}
	}
	
	/**
	 * Method to calculate the square root of an integer, without overhead of using doubles
	 * 
	 * I did not write this method, nor do I take credit for it.  I read about it on StackOverflow a while
	 * ago and was very excited that I had found a use for it while making this project.
	 * 
	 * @param num the integer to square root
	 * @return the integer representation of the square root
	 */
	private int isqrt(int num) {
	    int res = 0;
	    int bit = 1 << 30;
	 
	    // "bit" starts at the highest power of four <= the argument.
	    while (bit > num)
	        bit >>= 2;
	        
	    while (bit != 0) {
	        if (num >= res + bit) {
	            num -= res + bit;
	            res = (res >> 1) + bit;
	        }
	        else
	            res >>= 1;
	        bit >>= 2;
	    }
	    return res;
	}

}
