import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Displays a title screen
 */
public class TitleScreen {
	
	/**
	 * Paints the title screen
	 * @param g the specified Graphics window
	 */
	public void paint(Graphics g) {
		
		BufferedImage img = null;
		try {
			img = ImageIO.read(this.getClass().getResource("hivolts.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.drawImage(img, (Hivolts.scale-img.getWidth())/2, scale(0.05), null);
		
		Font font = g.getFont();

		g.setColor(HivoltsPanel.PLATO_ORANGE);
		g.setFont(new Font(font.getFontName(), font.getStyle(), font.getSize()+4));

		g.drawString("The Game of ELECTRIC FENCES", (int) (scale(0.5) - 200), scale(0.05)+330);
		g.drawString("(Ported to Java)", scale(0.5)-g.getFontMetrics().stringWidth(("(Ported to Java)"))/2, scale(0.7));

		g.setFont(new Font(font.getFontName(), font.getStyle(), font.getSize()+12));
		g.drawString("Press space to play", scale(0.5)-g.getFontMetrics().stringWidth(("Press Space to play"))/2, scale(0.8));
		
		g.setFont(font);
		g.setColor(Color.BLACK);
		
	}
	
	/**
	 * Scales a value from the local coordinate system value to pixels
	 * @param a the value to scale
	 */
	private int scale(double a) {
		return (int)Math.round(a*Hivolts.scale);
	}
	
}
