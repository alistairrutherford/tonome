package com.netthreads.gdx.app.layer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.netthreads.gdx.app.definition.AppEvents;
import com.netthreads.libgdx.director.AppInjector;
import com.netthreads.libgdx.director.Director;
import com.netthreads.libgdx.scene.Layer;

/**
 * Process controls layer.
 * 
 */
public class KeyLayer extends Layer
{
	// Director of the action.
	private Director director;

	/**
	 * Construct layer.
	 * 
	 * @param stage
	 */
	public KeyLayer()
	{
		Gdx.input.setCatchBackKey(true);

		director = AppInjector.getInjector().getInstance(Director.class);
	}

	/**
	 * Catch escape key.
	 * 
	 */
	@Override
	public boolean keyDown(int keycode)
	{
		boolean handled = false;

		if (keycode == Keys.BACK || keycode == Keys.ESCAPE)
		{
			director.sendEvent(AppEvents.EVENT_TRANSITION_TO_MENU_SCENE, this);

			handled = true;
		}

		return handled;
	}

}
