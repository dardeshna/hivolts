
/**
 * Motion and fade animation when an entity moves and then dies
 */
public class MovingFadeAnimation extends Animation {

	/**
	 * Creates a moving and fading animation of an Entity
	 * @param e the entity to animate
	 * @param l the length of the animation
	 */
	public MovingFadeAnimation(Entity e, int l, int x1, int y1, int x2, int y2) {
		super(e, l);
		entity.startAnimation(Animation.MOVING_FADE_ANIMATION, length, x1, y1, x2, y2);
	}

}

