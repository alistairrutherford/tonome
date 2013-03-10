package com.netthreads.gdx.model;

import com.google.inject.ImplementedBy;
import com.netthreads.gdx.sprite.NavigationButtonSprite.Direction;

/**
 * Managed multiple panel scenes.
 * 
 */
@ImplementedBy(PanelModelImpl.class)
public interface PanelModel
{
	
	/**
	 * Return panel connection definition.
	 * 
	 * @param panel
	 *            The panel number.
	 * 
	 * @return The panel connection definition.
	 */
	public int[] getConfiguration(int panel);
	
	/**
	 * Get button definition.
	 * 
	 * @return The definitions.
	 */
	public String[] getDefinition();
	
	/**
	 * Get button directions.
	 * 
	 * @return The directions.
	 */
	public Direction[] getDirections();
	
	/**
	 * Run transition to target panel.
	 * 
	 * @param targetPanel
	 *            The target panel id.
	 * @param direction
	 *            The direction.
	 */
	public void transitionToPanel(int targetPanel, Direction direction);
}
