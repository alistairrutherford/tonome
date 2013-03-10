package com.netthreads.gdx.scene;

import com.netthreads.gdx.layer.AboutLayer;
import com.netthreads.gdx.layer.KeyLayer;
import com.netthreads.libgdx.scene.Layer;
import com.netthreads.libgdx.scene.Scene;

/**
 * About scene.
 * 
 */
public class AboutScene extends Scene
{
	private Layer controlLayer;
	private Layer aboutLayer;
	
	public AboutScene()
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
		aboutLayer = new AboutLayer(getWidth(), getHeight());
		
		addLayer(aboutLayer);
	}
}
