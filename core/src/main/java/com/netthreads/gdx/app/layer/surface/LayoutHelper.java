package com.netthreads.gdx.app.layer.surface;

import com.netthreads.gdx.app.definition.AppSize;

/**
 * Helper methods to layout grid and bar display.
 * 
 */
public class LayoutHelper
{
	/**
	 * Return x padding for layout.
	 * 
	 * @param rowSize
	 * @param columnSize
	 * @return return padding.
	 */
	public static int getXPadding(int rowSize, int columnSize)
	{
		int xPadding = (int) (AppSize.BUTTON_SPRITE_WIDTH * (AppSize.BUTTON_SPRITE_PADDING_X_PERCENTAGE / 100.0f));
		
		return xPadding;
	}
	
	/**
	 * Return y padding for layout.
	 * 
	 * @param rowSize
	 * @param columnSize
	 * @return padding.
	 */
	public static int getYPadding(int rowSize, int columnSize)
	{
		int yPadding = (int) (AppSize.BUTTON_SPRITE_HEIGHT * (AppSize.BUTTON_SPRITE_PADDING_Y_PERCENTAGE / 100.0f));
		
		return yPadding;
	}
	
	/**
	 * Get start x position for layout.
	 * 
	 * @param rowSize
	 * @param columnSize
	 * @param width
	 * @param height
	 * @return x starting position.
	 */
	public static int getStartX(int rowSize, int columnSize, float width, float height)
	{
		int xPadding = getXPadding(rowSize, columnSize);
		
		int startX = (int) ((width - (columnSize * (xPadding + AppSize.BUTTON_SPRITE_WIDTH))) / 2);
		
		return startX;
	}
	
	/**
	 * Get y position for layout.
	 * 
	 * @param rowSize
	 * @param columnSize
	 * @param width
	 * @param height
	 * @return y starting position.
	 */
	public static int getStartY(int rowSize, int columnSize, float width, float height)
	{
		int yPadding = getYPadding(rowSize, columnSize);
		
		int startY = (int) ((height - (rowSize * (yPadding + AppSize.BUTTON_SPRITE_HEIGHT))) / 2);
		
		return startY;
	}
	
	/**
	 * Get x increment for layout.
	 * 
	 * @param rowSize
	 * @param columnSize
	 * 
	 * @return The x increment for layout.
	 */
	public static int getXIncrement(int rowSize, int columnSize)
	{
		int xPadding = getXPadding(rowSize, columnSize);
		
		int xIncrement = (int) (xPadding + AppSize.BUTTON_SPRITE_WIDTH);
		
		return xIncrement;
	}
	
	/**
	 * Get y increment for layout.
	 * 
	 * @param rowSize
	 * @param columnSize
	 * 
	 * @return The y increment for layout.
	 */
	public static int getYIncrement(int rowSize, int columnSize)
	{
		int yPadding = getYPadding(rowSize, columnSize);
		
		int yIncrement = (int) (yPadding + AppSize.BUTTON_SPRITE_HEIGHT);
		
		return yIncrement;
	}
	
	/**
	 * Get y position for layout.
	 * 
	 * @param rowSize
	 * @param columnSize
	 * @param width
	 * @param height
	 * @return y starting position.
	 */
	public static int getBarStartY(int rowSize, int columnSize, float width, float height)
	{
		int yPadding = getYPadding(rowSize, columnSize);
		
		int startY = (int) ((height - (rowSize * (yPadding + AppSize.BUTTON_SPRITE_HEIGHT))) / 2);
		
		startY = startY + (rowSize * (yPadding + AppSize.BUTTON_SPRITE_HEIGHT));
		
		return startY;
	}
}
