package com.netthreads.gdx.scene;

import com.netthreads.audio.note.service.NoteModelImpl;
import com.netthreads.gdx.layer.FpsLayer;
import com.netthreads.gdx.layer.KeyLayer;
import com.netthreads.gdx.layer.surface.BarLayer;
import com.netthreads.gdx.layer.surface.ControlLayer;
import com.netthreads.gdx.layer.surface.NavigationLayer;
import com.netthreads.gdx.layer.surface.NoteLayer;
import com.netthreads.gdx.layer.surface.TouchSurfaceLayer;
import com.netthreads.libgdx.scene.Layer;
import com.netthreads.libgdx.scene.Scene;

/**
 * Main Application scene.
 * 
 */
public class PanelScene extends Scene
{
	private Layer keyLayer;
	private Layer fpsLayer;
	private TouchSurfaceLayer touchLayer;
	private Layer noteLayer;
	private Layer displayBarLayer;
	private Layer controlLayer;
	private Layer navigationLayer;

	/**
	 * Panel scene.
	 * 
	 */
	public PanelScene(int panel)
	{
		// ---------------------------------------------------------------
		// Control layer
		// ---------------------------------------------------------------
		keyLayer = new KeyLayer();

		getInputMultiplexer().addProcessor(keyLayer);

		addLayer(keyLayer);

		// ---------------------------------------------------------------
		// Create touch layer
		// ---------------------------------------------------------------
		touchLayer = new TouchSurfaceLayer(getWidth(), getHeight());

		// Route input events to layer.
		getInputMultiplexer().addProcessor(touchLayer);

		// Activate broadcast of touch events.
		touchLayer.setSendTouchDown(true);
		touchLayer.setSendTouchDragged(true);
		touchLayer.setSendTouchUp(true);

		addLayer(touchLayer);

		// ---------------------------------------------------------------
		// FPS layer.
		// ---------------------------------------------------------------
		fpsLayer = new FpsLayer(getWidth(), getHeight());

		addLayer(fpsLayer);

		// ---------------------------------------------------------------
		// Create bar layer
		// ---------------------------------------------------------------
		displayBarLayer = new BarLayer(getWidth(), getHeight(), NoteModelImpl.COUNT_NOTES, NoteModelImpl.COUNT_BARS);

		addLayer(displayBarLayer);

		// ---------------------------------------------------------------
		// Create control touch layer
		// ---------------------------------------------------------------
		controlLayer = new ControlLayer(getWidth(), getHeight(), NoteModelImpl.COUNT_NOTES, NoteModelImpl.COUNT_BARS, panel);

		addLayer(controlLayer);

		// ---------------------------------------------------------------
		// Note model.
		// ---------------------------------------------------------------
		noteLayer = new NoteLayer(getWidth(), getHeight());

		addLayer(noteLayer);
		
		// ---------------------------------------------------------------
		// Create touch layer
		// ---------------------------------------------------------------
		navigationLayer = new NavigationLayer(getWidth(), getHeight(), panel);

		addLayer(navigationLayer);
	}

}
