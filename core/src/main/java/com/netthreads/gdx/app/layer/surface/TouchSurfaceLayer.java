package com.netthreads.gdx.app.layer.surface;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.netthreads.gdx.app.definition.AppEvents;
import com.netthreads.libgdx.director.AppInjector;
import com.netthreads.libgdx.director.Director;
import com.netthreads.libgdx.scene.Layer;

/**
 * Touch layer.
 * 
 */
public class TouchSurfaceLayer extends Layer
{
	// The one and only director.
	private Director director;

	private boolean sendTouchDown = false;
	private boolean sendTouchDragged = false;
	private boolean sendTouchUp = false;

	/**
	 * Dummy actor.
	 *
	 */
	private class TouchActor extends Actor
	{
		@Override
		public void draw(SpriteBatch arg0, float arg1)
		{
			// TODO Auto-generated method stub

		}
	}

	private TouchActor touchActor;

	/**
	 * Construct layer.
	 * 
	 * @param width
	 * @param height
	 */
	public TouchSurfaceLayer(float width, float height)
	{
		setWidth(width);
		setHeight(height);

		director = AppInjector.getInjector().getInstance(Director.class);

		touchActor = new TouchActor();
	}

	/**
	 * Handle touch down.
	 * 
	 * @param x
	 *            The x coordinate, origin is in the upper left corner
	 * @param y
	 *            The y coordinate, origin is in the upper left corner pointer the pointer for the event.
	 * @param newParam
	 *            the button
	 */
	@Override
	public boolean touchDown(int x, int y, int pointer, int button)
	{
		if (isSendTouchDown())
		{
			Actor actor = touchActor;

			touchActor.setX(x * director.getScaleFactorX());
			touchActor.setY(director.getHeight() - (y * director.getScaleFactorY()));

			director.sendEvent(AppEvents.EVENT_PAD_TOUCH_DOWN, actor);
		}

		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer)
	{
		if (isSendTouchDragged())
		{
			Actor actor = touchActor;

			touchActor.setX(x * director.getScaleFactorX());
			touchActor.setY(director.getHeight() - (y * director.getScaleFactorY()));
			
			director.sendEvent(AppEvents.EVENT_PAD_TOUCH_DRAGGED, actor);
		}

		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button)
	{
		if (isSendTouchUp())
		{
			Actor actor = touchActor;

			touchActor.setX(x * director.getScaleFactorX());;
			touchActor.setY(director.getHeight() - (y * director.getScaleFactorY()));
			
			director.sendEvent(AppEvents.EVENT_PAD_TOUCH_UP, actor);
		}

		return false;
	}

	/**
	 * @return
	 */
	public boolean isSendTouchDown()
	{
		return sendTouchDown;
	}

	/**
	 * @param sendTouchDown
	 */
	public void setSendTouchDown(boolean sendTouchDown)
	{
		this.sendTouchDown = sendTouchDown;
	}

	/**
	 * @return
	 */
	public boolean isSendTouchDragged()
	{
		return sendTouchDragged;
	}

	/**
	 * @param sendTouchDragged
	 */
	public void setSendTouchDragged(boolean sendTouchDragged)
	{
		this.sendTouchDragged = sendTouchDragged;
	}

	/**
	 * @return
	 */
	public boolean isSendTouchUp()
	{
		return sendTouchUp;
	}

	/**
	 * @param sendTouchUp
	 */
	public void setSendTouchUp(boolean sendTouchUp)
	{
		this.sendTouchUp = sendTouchUp;
	}

}
