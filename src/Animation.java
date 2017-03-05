
/**
 * Base class for all animations
 */
public class Animation {

	public static final int MOTION_ANIMATION = 0;
	public static final int FADE_ANIMATION = 1;
	public static final int MOVING_FADE_ANIMATION = 2;

	protected int length;
	protected int frame;
	protected Entity entity;

	/**
	 * Creates an animation of an Entity
	 * @param e the entity to animate
	 * @param l the length of the animation
	 */
	protected Animation(Entity e, int l) {
		length = l;
		entity = e;
	}

	/**
	 * Updates the frame count and entity
	 */
	public void update() {
		entity.update(frame);
		frame++;
	}
	
	/**
	 * Stops the animation
	 */
	public void stop() {
		entity.stopAnimation();
	}

	/**
	 * Returns whether the animation is done
	 * @return whether the animation is done
	 */
	public boolean isDone() {
		return frame >= length;
	}
	
	/**
	 * Returns the Entity that being animated
	 * @return the Entity that being animated
	 */
	public Entity getEntity() {
		return entity;
	}

}
