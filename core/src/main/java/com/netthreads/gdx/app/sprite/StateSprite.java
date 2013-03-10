/*
 * -----------------------------------------------------------------------
 * Copyright 2012 - Alistair Rutherford - www.netthreads.co.uk
 * -----------------------------------------------------------------------
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.netthreads.gdx.app.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.netthreads.libgdx.scene.ActorEx;

/**
 * State Frame Sprite.
 * 
 * Allows us to switch between sprite frames.
 * 
 */
public class StateSprite extends ActorEx
{
	private TextureRegion[] frames;

	private TextureRegion currentFrame;

	private int frame;

	/**
	 * Construct sprite.
	 * 
	 * @param texture
	 *            Sprite-sheet texture.
	 * @param rows
	 *            Sprite-sheet rows.
	 * @param cols
	 *            Sprite-sheet columns.
	 */
	public StateSprite(TextureRegion texture, int rows, int cols)
	{
		int tileWidth = texture.getRegionWidth() / cols;
		int tileHeight = texture.getRegionHeight() / rows;
		TextureRegion[][] tmp = texture.split(tileWidth, tileHeight);
		frames = new TextureRegion[cols * rows];

		int index = 0;
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				frames[index++] = tmp[i][j];
			}
		}

		// Set the sprite width and height.
		setWidth(tileWidth);
		setHeight(tileHeight);

		// Initialise the current frame.
		setFrame(0);
	}

	/**
	 * Draw sprite.
	 * 
	 */
	@Override
	public void draw(SpriteBatch batch, float parentAlpha)
	{
		batch.draw(currentFrame, getX(), getY());
	}

	/**
	 * Set current frame to display.
	 * 
	 * @param frame
	 */
	public void setFrame(int frame)
	{
		this.frame = frame;

		if (this.frame > frames.length)
		{
			this.frame = 0;
		}

		currentFrame = frames[frame];
	}

	/**
	 * Increment frame.
	 * 
	 */
	public void incFrame()
	{
		int newFrame = (this.frame + 1) % frames.length;

		setFrame(newFrame);
	}

	/**
	 * Return frame index.
	 * 
	 * @return The frame index.
	 */
	public int getFrame()
	{
		return frame;
	}

}
