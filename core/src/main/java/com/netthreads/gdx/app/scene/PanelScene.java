package com.netthreads.gdx.app.scene;

import com.netthreads.audio.note.service.NoteModelImpl;
import com.netthreads.gdx.app.layer.FpsLayer;
import com.netthreads.gdx.app.layer.KeyLayer;
import com.netthreads.gdx.app.layer.surface.BarLayer;
import com.netthreads.gdx.app.layer.surface.ControlLayer;
import com.netthreads.gdx.app.layer.surface.NavigationLayer;
import com.netthreads.gdx.app.layer.surface.NoteLayer;
import com.netthreads.gdx.app.layer.surface.TouchSurfaceLayer;
import com.netthreads.libgdx.scene.Layer;
import com.netthreads.libgdx.scene.Scene;

/**
 * Main Application scene.
 * 
 */
public class PanelScene extends Scene
{
	private final Layer keyLayer;
	private final Layer fpsLayer;
	private final TouchSurfaceLayer touchLayer;
	private final Layer noteLayer;
	private final Layer displayBarLayer;
	private final Layer controlLayer;
	private final Layer navigationLayer;

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
