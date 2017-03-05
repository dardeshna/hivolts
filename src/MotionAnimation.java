
/**
 * Motion animation when an entity moves on the board
 */
public class MotionAnimation extends Animation {

	/**
	 * Creates a moving animation of an Entity
	 * @param e the entity to animate
	 * @param l the length of the animation
	 */
	public MotionAnimation(Entity e, int l, int x1, int y1, int x2, int y2) {
		super(e, l);
		entity.startAnimation(Animation.MOTION_ANIMATION, length, x1, y1, x2, y2);
	}
	
}
