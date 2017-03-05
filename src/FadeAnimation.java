
/**
 * Fade animation when an entity dies in place
 */
public class FadeAnimation extends Animation {

	/**
	 * Creates a fading animation of an Entity
	 * @param e the entity to animate
	 * @param l the length of the animation
	 */
	public FadeAnimation(Entity e, int l) {
		super(e, l);
		entity.startAnimation(Animation.FADE_ANIMATION, length);
	}

}

