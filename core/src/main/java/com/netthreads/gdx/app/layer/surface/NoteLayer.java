package com.netthreads.gdx.app.layer.surface;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;
import com.netthreads.audio.note.service.NoteModel;
import com.netthreads.gdx.app.definition.AppEvents;
import com.netthreads.gdx.app.sprite.ControlButtonSprite;
import com.netthreads.gdx.app.sprite.FadeLabelSprite;
import com.netthreads.libgdx.director.AppInjector;
import com.netthreads.libgdx.director.Director;
import com.netthreads.libgdx.event.ActorEvent;
import com.netthreads.libgdx.event.ActorEventObserver;
import com.netthreads.libgdx.scene.Layer;

/**
 * Note
 * 
 */
public class NoteLayer extends Layer implements ActorEventObserver
{
	private static final int ITEM_POOL = 5;

	private NoteModel noteModel;

	// -------------------------------------------------------------------
	// Sprite pool.
	// ------------------------------------------------------ -------------
	private Pool<FadeLabelSprite> pool = new Pool<FadeLabelSprite>(ITEM_POOL)
	{
		@Override
		protected FadeLabelSprite newObject()
		{
			FadeLabelSprite sprite = new FadeLabelSprite();

			return sprite;
		}

		@Override
		public void free(FadeLabelSprite object)
		{
			super.free(object);

			// TODO: Check we need this, not sure we do anymore.
			object.clearActions();
		}
	};

	// Director of the action.
	private Director director;

	/**
	 * Play notes layer
	 * 
	 * @param width
	 * @param height
	 */
	public NoteLayer(float width, float height)
	{
		setWidth(width);
		setHeight(height);

		director = AppInjector.getInjector().getInstance(Director.class);

		noteModel = AppInjector.getInjector().getInstance(NoteModel.class);
	}

	/**
	 * Enter scene handler.
	 * 
	 */
	@Override
	public void enter()
	{
		super.enter();

		// Add this as an event observer.
		director.registerEventHandler(this);
	}

	/**
	 * Enter scene handler.
	 * 
	 */
	@Override
	public void exit()
	{
		super.exit();

		// Remove this as an event observer.
		director.deregisterEventHandler(this);
	}

	/**
	 * Handle events.
	 * 
	 */
	@Override
	public boolean handleEvent(ActorEvent event)
	{
		boolean handled = false;

		Actor actor = event.getActor();

		switch (event.getId())
		{
		case AppEvents.EVENT_NOTE_ON:
			handleNoteOn(actor);
			handled = true;
			break;
		case AppEvents.EVENT_NOTE_OFF:
			handleNoteOff(actor);
			handled = true;
			break;
		default:
			break;
		}

		return handled;
	}

	/**
	 * Handle note on.
	 * 
	 * @param actor
	 */
	private void handleNoteOn(Actor actor)
	{
		ControlButtonSprite sprite = (ControlButtonSprite) actor;

		noteModel.setState(sprite.getPanel(), sprite.getCol(), sprite.getRow(), true);

		runLabel(actor);
	}

	/**
	 * Handle note off.
	 * 
	 * @param actor
	 */
	private void handleNoteOff(Actor actor)
	{
		ControlButtonSprite sprite = (ControlButtonSprite) actor;

		noteModel.setState(sprite.getPanel(), sprite.getCol(), sprite.getRow(), false);

		runLabel(actor);
	}

	/**
	 * Add fading dot. TODO: Fix this.
	 * 
	 * @param actor
	 */
	private void runLabel(Actor actor)
	{
		FadeLabelSprite fadeLabelSprite = pool.obtain();
		fadeLabelSprite.setText(".");
		fadeLabelSprite.setX(actor.getX() + actor.getWidth() / 2);
		fadeLabelSprite.setY(actor.getY() + actor.getHeight() / 2);

		addActor(fadeLabelSprite);

		fadeLabelSprite.run();

	}
}
