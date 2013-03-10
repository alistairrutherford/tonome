package com.netthreads.gdx.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;
import com.netthreads.gdx.app.sprite.StateSprite;

/**
 * Flip through states.
 * 
 */
public class StateCycleAction extends Action
{
	private StateSprite target;

	private float elapsed;
	private float frameDuration;
	private int endFrame;

	private static final Pool<StateCycleAction> pool = new Pool<StateCycleAction>(10, 100)
	{
		@Override
		protected StateCycleAction newObject()
		{
			StateCycleAction action = new StateCycleAction();
			
			action.setPool(this);
			
			return action;
		}
	};

	/**
	 * Get instance from pool.
	 * 
	 * @param delay
	 *            Initial delay.
	 * @param tick
	 *            After delay elapsed time for tick.
	 * 
	 * @return The action.
	 */
	public static StateCycleAction $(float frameDuration)
	{
		StateCycleAction action = pool.obtain();

		action.setElapsed(0);
		action.setFrameDuration(frameDuration);
		action.setEndFrame(0);

		return action;
	}

	/**
	 * Action.
	 * 
	 */
	@Override
	public boolean act(float delta)
	{
		boolean done = isDone();
		
		if (!done)
		{
			elapsed += delta;

			if (elapsed >= frameDuration)
			{
				elapsed = 0;

				target.incFrame();
			}
		}
		
		return done;
	}

	public boolean isDone()
	{
		boolean done = target.getFrame() == endFrame;

		return done;
	}


	public void setTarget(Actor actor)
	{
		target = (StateSprite) actor;

		// Set end frame to current frame.
		setEndFrame(target.getFrame());

		// Immediately go to next frame in animation.
		target.incFrame();
	}

	public Actor getTarget()
	{
		return target;
	}

	public float getFrameDuration()
	{
		return frameDuration;
	}

	public void setFrameDuration(float frameDuration)
	{
		this.frameDuration = frameDuration;
	}

	public int getEndFrame()
	{
		return endFrame;
	}

	public void setEndFrame(int endFrame)
	{
		this.endFrame = endFrame;
	}

	public float getElapsed()
	{
		return elapsed;
	}

	public void setElapsed(float elapsed)
	{
		this.elapsed = elapsed;
	}

}
