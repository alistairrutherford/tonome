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

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.netthreads.gdx.app.action.StateCycleAction;

/**
 * Control button.
 * 
 */
public class NavigationButtonSprite extends ScalableStateSprite
{
	private static final float FLIP_ANIMATION_DURATION = 0.1f;
	
	public enum Direction
	{
		LEFT, RIGHT, UP, DOWN, TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
	};
	
	private int targetId;
	
	private Direction direction;
	
	/**
	 * Create sprite.
	 * 
	 * @param texture
	 *            The animation texture.
	 */
	public NavigationButtonSprite(TextureRegion textureRegion, int rows, int cols, int targetId, Direction direction)
	{
		super(textureRegion, 1, 2);
		
		this.targetId = targetId;
		
		this.direction = direction;
	}
	
	/**
	 * Flash button.
	 * 
	 */
	public void animate()
	{
		// Do animation.
		StateCycleAction cycleAction = StateCycleAction.$(FLIP_ANIMATION_DURATION);
		
		cycleAction.setTarget(this.getStateSprite());
		
		getStateSprite().addAction(cycleAction);
	}
	
	public int getTargetId()
	{
		return targetId;
	}
	
	public Direction getDirection()
	{
		return direction;
	}
	
}
