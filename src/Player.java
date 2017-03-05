import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

/**
 * Subclass of Entity for player
 */
public class Player extends Entity {
	
	/**
	 * Creates a Player
	 * @param x the x coordinate of the player
	 * @param y the y coordinate of the mho
	 */
	public Player(int x, int y) {
		super(x, y, Hivolts.PLAYER_SQUARE);
	}
	
	/**
	 * Paints the player, overrides </code>paint</code> method in Entity
	 * @param g the specified Graphics window
	 */
	@Override
	public void paint(Graphics g) {
		
		double x, y;
		
		if (isAnimating && (animationType == Animation.MOTION_ANIMATION || animationType == Animation.MOVING_FADE_ANIMATION)) {
			x = 0.125+(animationData[0]+(animationData[2]-animationData[0])*(progress))*HivoltsPanel.CELL_SIZE+(animationData[0]+(animationData[2]-animationData[0])*(progress))*(0.0181818);
			y = 0.125+(animationData[1]+(animationData[3]-animationData[1])*(progress))*HivoltsPanel.CELL_SIZE+(animationData[1]+(animationData[3]-animationData[1])*(progress))*(0.0181818);		
		}
		else {
			x = 0.125+getX()*HivoltsPanel.CELL_SIZE+getX()*(0.0181818);
			y = 0.125+getY()*HivoltsPanel.CELL_SIZE+getY()*(0.0181818);
		}
		
		if (isAnimating && (animationType == Animation.FADE_ANIMATION || animationType == Animation.MOVING_FADE_ANIMATION)) {
			
			double f = 1.0-progress;			
			g.setColor(new Color((int)(f*0xff)*0x10000 + (int)(f*0x99)*0x100));  //Fade (more like rainbow) between orange and black
			
		}
		else {
			g.setColor(HivoltsPanel.PLATO_ORANGE);
		}
		
		
		g.drawOval(scale(x-HivoltsPanel.CELL_SIZE*0.4), scale(y-HivoltsPanel.CELL_SIZE*0.4), scale(HivoltsPanel.CELL_SIZE*0.8), scale(HivoltsPanel.CELL_SIZE*0.8));

		g.fillRect(scale(x-HivoltsPanel.CELL_SIZE*0.0875+HivoltsPanel.CELL_SIZE*0.15), scale(y-HivoltsPanel.CELL_SIZE*0.2), scale(HivoltsPanel.CELL_SIZE*0.175), scale(HivoltsPanel.CELL_SIZE*0.175));
		g.fillRect(scale(x-HivoltsPanel.CELL_SIZE*0.0875-HivoltsPanel.CELL_SIZE*0.15), scale(y-HivoltsPanel.CELL_SIZE*0.2), scale(HivoltsPanel.CELL_SIZE*0.175), scale(HivoltsPanel.CELL_SIZE*0.175));

		Stroke stroke = ((Graphics2D)g).getStroke();
		
		((Graphics2D)g).setStroke(new BasicStroke(scale(HivoltsPanel.CELL_SIZE*1.25/20.0)));
		g.drawArc(scale(x-HivoltsPanel.CELL_SIZE*0.275), scale(y-HivoltsPanel.CELL_SIZE*0.175), scale(HivoltsPanel.CELL_SIZE*0.55), scale(HivoltsPanel.CELL_SIZE*0.45), 220, 100);
		
		((Graphics2D)g).setStroke(stroke);
		g.setColor(Color.BLACK);
	}
	
}
