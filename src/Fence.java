import java.awt.Color;
import java.awt.Graphics;

/**
 * Subclass of Entity for fences
 */
public class Fence extends Entity {
	
	/**
	 * Creates a Fence
	 * @param x the x coordinate of the player
	 * @param y the y coordinate of the mho
	 */
	public Fence(int x, int y) {
		super(x, y, Hivolts.FENCE_SQUARE);
	}
	
	/**
	 * Paints the fence, overrides </code>paint</code> method in Entity
	 * @param g the specified Graphics window
	 */
	@Override
	public void paint(Graphics g) {
		double x = 0.125+getX()*HivoltsPanel.CELL_SIZE+getX()*(0.0181818);
		double y = 0.125+getY()*HivoltsPanel.CELL_SIZE+getY()*(0.0181818);
		
		if (isAnimating && animationType == Animation.FADE_ANIMATION) {
			
			double f = 1.0-progress;			
			g.setColor(new Color((int)(f*0xff)*0x10000 + (int)(f*0x99)*0x100));  //Fade (more like rainbow) between orange and black
			
		}
		else {
			g.setColor(HivoltsPanel.PLATO_ORANGE);
		}

		g.fillRect(scale(x-HivoltsPanel.CELL_SIZE*0.45), scale(y-HivoltsPanel.CELL_SIZE*0.45), scale(HivoltsPanel.CELL_SIZE*0.1), scale(HivoltsPanel.CELL_SIZE*0.9));
		g.fillRect(scale(x+HivoltsPanel.CELL_SIZE*0.35), scale(y-HivoltsPanel.CELL_SIZE*0.45), scale(HivoltsPanel.CELL_SIZE*0.1), scale(HivoltsPanel.CELL_SIZE*0.9));

		g.fillRect(scale(x-HivoltsPanel.CELL_SIZE*0.45), scale(y-HivoltsPanel.CELL_SIZE*0.3), scale(HivoltsPanel.CELL_SIZE*0.9), scale(HivoltsPanel.CELL_SIZE*0.065));
		g.fillRect(scale(x-HivoltsPanel.CELL_SIZE*0.45), scale(y+HivoltsPanel.CELL_SIZE*0), scale(HivoltsPanel.CELL_SIZE*0.9), scale(HivoltsPanel.CELL_SIZE*0.065));
		g.fillRect(scale(x-HivoltsPanel.CELL_SIZE*0.45), scale(y+HivoltsPanel.CELL_SIZE*0.3), scale(HivoltsPanel.CELL_SIZE*0.9), scale(HivoltsPanel.CELL_SIZE*0.065));

		g.setColor(Color.BLACK);
	}
	
}
