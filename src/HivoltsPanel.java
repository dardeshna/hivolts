import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Manages all graphics for the game
 */
public class HivoltsPanel extends JPanel {
	
	public static final double CELL_SIZE = 0.05*12.0/Hivolts.BOARD_SIZE;
	public static final Color PLATO_ORANGE = new Color(0xFF9900);
	
	private static final long serialVersionUID = 1L;

	private String message = "";
	private Board board;
	private TitleScreen titleScreen;
	
	/**
	 * Creates a HivoltsPanel with a Board
	 * @param b the Board
	 */
	public HivoltsPanel(Board b) {
		super();
		board = b;
	}
	
	/**
	 * Sets the Board of the HivoltsPanel
	 * @param b the Board
	 */
	public void setBoard(Board b) {
		board = b;
	}
	
	/**
	 * Overrides the <code>paintComponent</code> in JPanel to paint the HivoltsPanel
	 */
	@Override
	public void paintComponent(Graphics g) {

		g.setColor(Color.BLACK);
		
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		if (titleScreen != null) {
			titleScreen.paint(g);
		}
		else {
	
			for (int i = 0; i < Hivolts.BOARD_SIZE; i++) {
				for (int j = 0; j < Hivolts.BOARD_SIZE; j++) {
					board.paintEntity(i, j, g);
				}
			}
			
			g.setColor(HivoltsPanel.PLATO_ORANGE);
					
			int i = 0;
			for (String line : message.split("\n")) {
				g.drawString(line, scale(0.5)-g.getFontMetrics().stringWidth(line)/2, scale(0.95) + g.getFontMetrics().getHeight()*i);
				i++;
			}
			
			g.setColor(Color.BLACK);
			
		}

	}
	
	/**
	 * Scales a value from the local coordinate system value to pixels
	 * @param a the value to scale
	 */
	private int scale(double a) {
		return (int)Math.round(a*Hivolts.scale);
	}

	/**
	 * Overrides the <code>getPreferredSize</code> method to return the correct preferred size
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(Hivolts.scale, Hivolts.scale);
	}
	
	/**
	 * Sets the text message at the bottom of the HivoltsPanel
	 * @param m the message
	 */
	public void setMessage(String m) {
		message = m;		
	}
	
	/**
	 * Toggles the TitleScreen on and off
	 * @param state whether the TitleScreen should be shown
	 */
	public void toggleTitleScreen(boolean state) {
		
		if (state & titleScreen == null) {
			titleScreen = new TitleScreen();
		}
		
		else {
			titleScreen = null;
		}
		
	}
}
