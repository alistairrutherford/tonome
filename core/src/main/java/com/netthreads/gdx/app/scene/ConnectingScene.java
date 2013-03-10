package com.netthreads.gdx.app.scene;

import com.netthreads.gdx.app.layer.ConnectingLayer;
import com.netthreads.gdx.app.layer.KeyLayer;
import com.netthreads.libgdx.scene.Layer;
import com.netthreads.libgdx.scene.Scene;

/**
 * Loading scene.
 * 
 */
public class ConnectingScene extends Scene
{
	private final Layer controlLayer;
	private final Layer loadingLayer;

	public ConnectingScene()
	{
		// ---------------------------------------------------------------
		// Control layer
		// ---------------------------------------------------------------
		controlLayer = new KeyLayer();
		getInputMultiplexer().addProcessor(controlLayer);
		addLayer(controlLayer);

		// ---------------------------------------------------------------
		// About layer
		// ---------------------------------------------------------------
		loadingLayer = new ConnectingLayer(getWidth(), getHeight());

		addLayer(loadingLayer);
	}
}
