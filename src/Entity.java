import java.awt.Graphics;

/**
 * Base class for all Entities, also used for blank squares
 */
public class Entity {

	protected boolean isAnimating = false;
	protected int animationType;
	protected int animationLength;
	protected int[] animationData;
	protected int frame;
	protected double progress;
	
	public int value;
	public int x;
	public int y;
	
	/**
	 * Constructs an Entity with a specific type
	 * @param x the x coordinate of the entity
	 * @param y the y coordinate of the entity
	 * @param value the type of Entity
	 */
	public Entity(int x, int y, int value) {
		this.x = x;
		this.y = y;
		this.value = value;
	}
	
	/**
	 * Constructs an Entity representing a blank square
	 * @param x the x coordinate of the entity
	 * @param y the y coordinate of the entity
	 */
	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
		this.value = 0;
	}
	
	/**
	 * Returns the x coordinate of the Entity
	 * @return the x coordinate
	 */
	public int getX() {
		return this.x;
	}
	/**
	 * Returns the y coordinate of the Entity
	 * @return the y coordinate
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * Sets the x coordinate of the Entity
	 * @param x the x coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * Sets the y coordinate of the Entity
	 * @param y the y coordinate
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Returns the type of the Entity
	 * @return the type
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Paints the Entity, overridden in subclasses
	 * @param g the specified Graphics window
	 */
	public void paint(Graphics g) {
		//Blank square, do nothing
	}
	
	/**
	 * Scales a value from the local coordinate system value to pixels
	 * @param a the value to scale
	 */
	protected int scale(double a) {
		return (int)Math.round(a*Hivolts.scale);
	}
	
	/**
	 * Starts animating the Entity
	 * @param type the type of animation
	 * @param length the length in frames of the animation
	 * @param type_args the arguments for the specific type of animation
	 */
	public void startAnimation(int type, int length, int... type_args) {
		isAnimating = true;
		animationType = type;
		animationLength = length;
		animationData = type_args;
	}
	
	/**
	 * Stops animating the entity
	 */
	public void stopAnimation() {
		isAnimating = false;
		animationType = 0;
		animationData = null;
	}
	
	/**
	 * Updates the frame count when animation
	 * @param f the current frame
	 */
	public void update(int f) {
		frame = f;
		progress = (double)frame/animationLength;
	}
	
}
