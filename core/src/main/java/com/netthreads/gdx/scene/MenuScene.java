package com.netthreads.gdx.scene;

import com.netthreads.gdx.layer.MenuLayer;
import com.netthreads.libgdx.scene.Layer;
import com.netthreads.libgdx.scene.Scene;

/**
 * Menu scene.
 * 
 */
public class MenuScene extends Scene
{
	private Layer menuLayer;

	public MenuScene()
	{
		// ---------------------------------------------------------------
		// Main menu layer.
		// ---------------------------------------------------------------
		menuLayer = new MenuLayer(getWidth(), getHeight());

		addLayer(menuLayer);
	}

}
