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

package com.netthreads.gdx.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Control button.
 * 
 */
public class BarSprite extends ScalableStateSprite
{
	public static final int STATE_OFF = 0;
	public static final int STATE_ON = 1;

	private boolean state;

	/**
	 * Create sprite.
	 * 
	 * @param texture
	 *            The animation texture.
	 */
	public BarSprite(TextureRegion textureRegion)
	{
		super(textureRegion, 1, 2);

		state = false;

		setFrame(STATE_OFF);
	}

	public boolean isState()
	{
		return state;
	}

	public void setState(boolean state)
	{
		this.state = state;

		if (state)
		{
			setFrame(STATE_ON);
		}
		else
		{
			setFrame(STATE_OFF);
		}
	}

}
