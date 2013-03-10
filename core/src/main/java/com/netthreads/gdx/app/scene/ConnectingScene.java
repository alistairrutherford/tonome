package com.netthreads.gdx.app.scene;

import com.netthreads.gdx.app.layer.ConnectingLayer;
import com.netthreads.libgdx.scene.Layer;
import com.netthreads.libgdx.scene.Scene;

/**
 * Loading scene.
 * 
 */
public class ConnectingScene extends Scene
{
	private Layer loadingLayer;

	public ConnectingScene()
	{
		// ---------------------------------------------------------------
		// About layer
		// ---------------------------------------------------------------
		loadingLayer = new ConnectingLayer(getWidth(), getHeight());

		addLayer(loadingLayer);
	}
}
