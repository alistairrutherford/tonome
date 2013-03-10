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
import com.netthreads.gdx.action.StateCycleAction;

/**
 * Control button.
 * 
 */
public class ControlButtonSprite extends ScalableStateSprite
{
	private static final float FLIP_ANIMATION_DURATION = 0.1f;

	public static final int STATE_UNSELECTED = 0;
	public static final int STATE_SELECTED = 1;

	private boolean selected;
	private int panel;
	private int row;
	private int col;

	/**
	 * Create sprite.
	 * 
	 * @param texture
	 *            The animation texture.
	 */
	public ControlButtonSprite(TextureRegion textureRegion)
	{
		super(textureRegion, 1, 2);

		selected = false;

		setFrame(STATE_UNSELECTED);
	}

	/**
	 * Toggle state.
	 * 
	 * @return The new state.
	 */
	public boolean toggle()
	{
		this.selected = !this.selected;

		if (this.selected)
		{
			setFrame(STATE_SELECTED);
		}
		else
		{
			setFrame(STATE_UNSELECTED);
		}

		return this.selected;
	}

	/**
	 * Set selection state.
	 * 
	 * @param selected
	 *            The selection state.
	 */
	public void setSelected(boolean selected)
	{
		this.selected = selected;

		if (this.selected)
		{
			setFrame(STATE_SELECTED);
		}
		else
		{
			setFrame(STATE_UNSELECTED);
		}
	}

	/**
	 * Fetch selection state.
	 * 
	 * @return The selection state.
	 */
	public boolean isSelected()
	{
		return selected;
	}

	public int getRow()
	{
		return row;
	}

	public void setRow(int row)
	{
		this.row = row;
	}

	public int getCol()
	{
		return col;
	}

	public void setCol(int col)
	{
		this.col = col;
	}

	public int getPanel()
	{
		return panel;
	}

	public void setPanel(int panel)
	{
		this.panel = panel;
	}

	/**
	 * Animate control.
	 * 
	 */
	public void animate()
	{
		// Do animation.
		StateCycleAction cycleAction = StateCycleAction.$(FLIP_ANIMATION_DURATION);

		cycleAction.setTarget(getStateSprite());

		getStateSprite().addAction(cycleAction);
	}

}
