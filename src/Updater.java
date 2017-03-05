import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * This class updates animations and repaints the HivoltsPanel every 20 milliseconds 
 */
public class Updater extends Thread {

	private Thread t;
	
	private HivoltsPanel panel;
	private Board board;
	private KeyListener listener;
	
	private ArrayList<Animation> animations = new ArrayList<Animation>();
	private boolean shouldStop;

	/**
	 * Constructs the updater with a HivoltsPanel and Board
	 * @param p the HivoltsPanel
	 * @param b the Board
	 */
	public Updater(HivoltsPanel p, Board b) {
		panel = p;
		board = b;
	}
	
	/**
	 * Runs the updater and updates the animations
	 */
	public void run() {
		
		boolean added = true;
		while (true) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			//Turns listener off when animating
			if (animations.size() > 0) {
				panel.removeKeyListener(listener);
				added = false;
			}
			else if (!added && !shouldStop) {
				panel.addKeyListener(listener);
				added = true;
			}

			//Update each animation and remove those that are done.
			for (int i = 0; i < animations.size(); i++) {
				animations.get(i).update();
				if (animations.get(i).isDone()) {
					if (animations.get(i) instanceof FadeAnimation || animations.get(i) instanceof MovingFadeAnimation) {
						board.delete(animations.get(i).getEntity());  //Delete entity if it has faded
					}
					animations.get(i).stop();
					animations.remove(i);
				}
			}

			panel.repaint();
		}

	}

	/**
	 * Starts the thread
	 */
	public void start() {

		if (t == null) {
			t = new Thread(this, "updater");
			t.start();
		}
		
	}
	
	/**
	 * Pauses updating the HivoltsPanel
	 */
	public void stopUpdater() {

		shouldStop = true;
		
	}
	
	/**
	 * Resumes updating the HivoltsPanel
	 */
	public void restartUpdater() {
		shouldStop = false;
		animations.clear();
		
	}
	
	/**
	 * Adds an animation to update every frame
	 * @param animation the animation to be updated
	 */
	public void addAnimation(Animation animation) {
		animations.add(animation);
	}

	/**
	 * Changes the board currently being used
	 * @param b the board
	 */
	public void setBoard(Board b) {
		board = b;
	}
	
	/**
	 * Sets the listener to be paused while animation (the listener currently in use)
	 * @param l the listener
	 */
	public void setListener(KeyListener l) {
		listener = l;
	}
	
}