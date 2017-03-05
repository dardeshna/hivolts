import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

/**
 * Main class for Hivolts, extends JFrame and contains KeyListeners. Manages
 * the title screen and when the game is over.  Contains some constants.
 */
public class Hivolts extends JFrame {

	private static final long serialVersionUID = 1L;

	public static final int BOARD_SIZE = 12;
	public static final int NUMBER_OF_MHOS = 12;

	public static final int BLANK_SQUARE = 0;
	public static final int PLAYER_SQUARE = 1;
	public static final int MHO_SQUARE = 2;
	public static final int FENCE_SQUARE = 3;

	public static final char[] validKeys = {'q', 'w', 'e', 'a', 's', 'd', 'z', 'x', 'c'};

	public static int scale = 600;
		
	public HivoltsPanel panel;
	private Container c;

	private KeyListener titleScreenListener;
	private KeyListener gameListener;
	private KeyListener gameOverListener;
	
	private Board board;
	private Updater updater;
	
	private int score;
	private int gamesPlayed;

	/**
	 * Constructor for Hivolts JFrame
	 */
	public Hivolts() {
		super("hivolts");
		
		board = new Board();

		panel = new HivoltsPanel(board);
		
		updater = new Updater(panel, board);
		
		board.setUpdater(updater);
		
		titleScreenListener = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {

				char c = e.getKeyChar();

				//If Space is pressed, start the game
				if (c == ' ') {
					
					startGame();
				}

			}
		};
		
		gameListener = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {

				char c = e.getKeyChar();

				//Move player based on key
				switch (c) {
				case 'q': board.movePlayer(-1, -1); break;
				case 'w': board.movePlayer(0, -1); break;
				case 'e': board.movePlayer(1, -1); break;
				case 'a': board.movePlayer(-1, 0); break;
				case 's': board.movePlayer(0, 0); break;
				case 'd': board.movePlayer(1, 0); break;
				case 'z': board.movePlayer(-1, 1); break;
				case 'x': board.movePlayer(0, 1); break;
				case 'c': board.movePlayer(1, 1); break;
				case 'j': board.jumpPlayer(); break;
				case ' ': gamesPlayed++; restartGame();
				}
				
				if (board.gameOver) {
					gameOver();
					gamesPlayed++;
					
					panel.setMessage(score + " Wins out of " + gamesPlayed + " Games, for "
							+ (int)(((double)score/gamesPlayed)*10)/10.0 + "% and a Rating of "
							+ (int)(100*(double)score/gamesPlayed) + "\nPress Space to play again");
					
				}
				else if (board.gameWon) {
					gameOver();
					gamesPlayed++;
					score++;
					
					panel.setMessage(score + " Wins out of " + gamesPlayed + " Games, for "
							+ (int)(((double)score/gamesPlayed)*10)/10.0 + "% and a Rating of "
							+ (int)(100*(double)score/gamesPlayed) + "\nPress Space to play again");
				}

			}
		};
		
		gameOverListener = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {

				char c = e.getKeyChar();
				
				//If Space is pressed, restart the game
				if (c == ' ') {
					
					restartGame();
				}

			}
		};
		
		
		panel.addKeyListener(titleScreenListener);
		
		panel.toggleTitleScreen(true);
		
		panel.setFocusable(true);
		panel.requestFocusInWindow();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		c = getContentPane();
		c.setLayout(new BorderLayout());

		setBackground(Color.WHITE);
		c.add(panel, BorderLayout.CENTER);
		pack();

		setResizable(false);
		setVisible(true);
		
	}
	
	/**
	 * Starts the game after the TitleScreen, sets the score message and starts the Updater
	 */
	private void startGame() {
		panel.toggleTitleScreen(false);
		panel.setMessage(score + " Wins out of " + gamesPlayed + " Games, for "
				+ (int)(((double)score/gamesPlayed)*10)/10.0 + "% and a Rating of "
				+ (int)(100*(double)score/gamesPlayed) + "\nPress Space to replot");
		panel.removeKeyListener(titleScreenListener);
		panel.addKeyListener(gameListener);
		
		updater.setListener(gameListener);
		updater.start();
	}
	
	/**
	 * Ends the game when it is over, sets up KeyListener for restart
	 */
	private void gameOver() {
		updater.stopUpdater();
		
		panel.removeKeyListener(gameListener);
		panel.addKeyListener(gameOverListener);
		
		panel.setMessage("Press space to restart");
	}

	/**
	 * Restarts the game with a new Board, updates the score message
	 */
	private void restartGame() {
		board = new Board();
		panel.setBoard(board);
		board.setUpdater(updater);
		
		updater.restartUpdater();
		updater.setBoard(board);
		
		panel.removeKeyListener(gameOverListener);
		
		panel.setMessage(score + " Wins out of " + gamesPlayed + " Games, for "
				+ (int)(((double)score/gamesPlayed)*10)/10.0 + "% and a Rating of "
				+ (int)(100*(double)score/gamesPlayed) + "\nPress Space to replot");
	}
	
	/**
	 * Runs the program and creates the window
	 * @param args
	 */
	public static void main(String[] args) {
		new Hivolts();
	}
	
}
