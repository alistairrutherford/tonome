package com.netthreads.gdx.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * Composite state sprite.
 * 
 * Only Groups are scalable.
 * 
 */
public class ScalableStateSprite extends Group
{
	private StateSprite stateSprite;

	/**
	 * Construct sprite.
	 * 
	 * @param texture
	 * @param rows
	 * @param cols
	 */
	public ScalableStateSprite(TextureRegion texture, int rows, int cols)
	{
		this.setStateSprite(texture, rows, cols);
	}

	/**
	 * Set state sprite.
	 * 
	 * @param texture
	 * @param rows
	 * @param cols
	 */
	public void setStateSprite(TextureRegion texture, int rows, int cols)
	{
		stateSprite = new StateSprite(texture, rows, cols);

		setWidth(stateSprite.getWidth());
		setHeight(stateSprite.getHeight());

		addActor(stateSprite);

	}

	/**
	 * Select frame.
	 * 
	 * @param frame
	 */
	public void setFrame(int frame)
	{
		stateSprite.setFrame(frame);
	}

	/**
	 * Return state sprite.
	 * 
	 * @return The state sprite.
	 */
	public StateSprite getStateSprite()
	{
		return stateSprite;
	}

}
