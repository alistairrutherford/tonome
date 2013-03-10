package com.netthreads.gdx.app.layer.surface;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.netthreads.audio.note.service.NoteModel;
import com.netthreads.gdx.app.definition.AppEvents;
import com.netthreads.gdx.app.definition.AppSize;
import com.netthreads.gdx.app.definition.AppTextures;
import com.netthreads.gdx.app.sprite.ControlButtonSprite;
import com.netthreads.libgdx.director.AppInjector;
import com.netthreads.libgdx.director.Director;
import com.netthreads.libgdx.event.ActorEvent;
import com.netthreads.libgdx.event.ActorEventObserver;
import com.netthreads.libgdx.scene.Layer;
import com.netthreads.libgdx.scene.SceneHelper;
import com.netthreads.libgdx.sprite.AnimatedSprite;
import com.netthreads.libgdx.texture.TextureCache;
import com.netthreads.libgdx.texture.TextureDefinition;

/**
 * Touch control surface.
 * 
 * Note: When dragging the point of touch we are either 'setting' or 'clearing'. Which one depend on the state of the
 * first control we touch.
 */
public class ControlLayer extends Layer implements ActorEventObserver
{
	private static final float POINTER_ANIMATION_FRAME_DURATION = 0.25f;

	private int panel;
	private int rowSize;
	private int columnSize;

	// Director of the action.
	private Director director;

	// Textures.
	private TextureCache textureCache;

	// Note and bar model.
	private NoteModel noteModel;

	// Animated pointer sprite
	private AnimatedSprite pointer;

	// Currently selected item on layer.
	private Actor selected;

	// Are we setting or clearing controls.
	private boolean toggleDirection;

	// Controls matrix.
	private ControlButtonSprite[][] control;

	/**
	 * Create main layer which composes all the main application layers.
	 * 
	 * @param width
	 *            View width.
	 * @param height
	 *            View height.
	 * @param rowSize
	 *            The control row size.
	 * @param columnSize
	 *            The control column size.
	 */
	public ControlLayer(float width, float height, int rowSize, int columnSize, int panel)
	{
		setWidth(width);
		setHeight(height);

		this.panel = panel;

		this.rowSize = rowSize;
		this.columnSize = columnSize;

		director = AppInjector.getInjector().getInstance(Director.class);

		noteModel = AppInjector.getInjector().getInstance(NoteModel.class);

		textureCache = AppInjector.getInjector().getInstance(TextureCache.class);

		control = new ControlButtonSprite[columnSize][rowSize];

		selected = null;

		// ---------------------------------------------------------------
		// Build view elements.
		// ---------------------------------------------------------------
		buildView();

		// ---------------------------------------------------------------
		// Animated pointer sprite.
		// ---------------------------------------------------------------
		TextureDefinition definition = textureCache.getDefinition(AppTextures.TEXTURE_POINTER);
		TextureRegion textureRegion = textureCache.getTexture(definition);
		pointer = new AnimatedSprite(textureRegion, 1, 4, POINTER_ANIMATION_FRAME_DURATION);
		pointer.setScaleX(AppSize.BUTTON_SPRITE_WIDTH / this.pointer.getWidth());
		pointer.setScaleY(AppSize.BUTTON_SPRITE_HEIGHT / this.pointer.getHeight());

		// Initially not visible.
		hidePointer();

		addActor(pointer);
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
	 * Build view elements.
	 * 
	 */
	private void buildView()
	{
		int startX = LayoutHelper.getStartX(rowSize, columnSize, getWidth(), getHeight());
		int startY = LayoutHelper.getStartY(rowSize, columnSize, getWidth(), getHeight());

		int xIncrement = LayoutHelper.getXIncrement(rowSize, columnSize);
		int yIncrement = LayoutHelper.getYIncrement(rowSize, columnSize);

		for (int column = 0; column < columnSize; column++)
		{
			for (int row = 0; row < rowSize; row++)
			{
				ControlButtonSprite controlButtonSprite = newControl(row, column);

				controlButtonSprite.setX(startX + (column * xIncrement));
				controlButtonSprite.setY(startY + (row * yIncrement));

				addActor(controlButtonSprite);
			}
		}
	}

	/**
	 * Create control.
	 * 
	 * @return new control.
	 */
	private ControlButtonSprite newControl(int row, int column)
	{
		TextureDefinition definition = textureCache.getDefinition(AppTextures.TEXTURE_BUTTON);
		TextureRegion textureRegion = textureCache.getTexture(definition);
		ControlButtonSprite sprite = new ControlButtonSprite(textureRegion);
		sprite.setScaleX(AppSize.BUTTON_SPRITE_WIDTH / sprite.getWidth());
		sprite.setScaleY(AppSize.BUTTON_SPRITE_HEIGHT / sprite.getHeight());

		sprite.setPanel(this.panel);
		sprite.setRow(row);
		sprite.setCol(column);

		// Make a note of the control.
		control[column][row] = sprite;

		return sprite;
	}

	/**
	 * Show pointer at selection.
	 * 
	 * @param pointer
	 *            The pointer.
	 * @param selected
	 *            The selected item.
	 */
	private void showPointer(Actor pointer, Actor target)
	{
		pointer.setVisible(true);
		pointer.setX(target.getX());
		pointer.setY(target.getY());
		pointer.setRotation(target.getRotation());
	}

	/**
	 * Hide pointer.
	 * 
	 */
	private void hidePointer()
	{
		pointer.setVisible(false);
	}

	/**
	 * Handle toggle control button.
	 * 
	 * @param controlButtonSprite
	 * 
	 * @return The new setting of control.
	 */
	private boolean toggleControl(ControlButtonSprite controlButtonSprite)
	{
		boolean setting = controlButtonSprite.toggle();

		sendControlEvent(controlButtonSprite, setting);

		return setting;
	}

	/**
	 * Handle toggle control button.
	 * 
	 * @param controlButtonSprite
	 * 
	 * @return The new setting of control.
	 */
	private void setControl(ControlButtonSprite controlButtonSprite, boolean setting)
	{
		controlButtonSprite.setSelected(setting);

		sendControlEvent(controlButtonSprite, setting);
	}

	/**
	 * Send appropriate control event.
	 * 
	 * @param controlButtonSprite
	 *            The control.
	 * @param setting
	 *            The setting.
	 */
	private void sendControlEvent(ControlButtonSprite controlButtonSprite, boolean setting)
	{
		if (setting)
		{
			director.sendEvent(AppEvents.EVENT_NOTE_ON, controlButtonSprite);
		}
		else
		{
			director.sendEvent(AppEvents.EVENT_NOTE_OFF, controlButtonSprite);
		}
	}

	/**
	 * Override the draw to position to place our element pointer at the appropriate spot on the view.
	 */
	@Override
	public void draw(SpriteBatch batch, float parentAlpha)
	{
		super.draw(batch, parentAlpha);

		if (pointer.isVisible() && selected != null)
		{
			showPointer(pointer, selected);
		}

	}

	/**
	 * Return panel number.
	 * 
	 * @return The panel number.
	 */
	public int getPanel()
	{
		return panel;
	}

	/**
	 * Handle value touch hit.
	 * 
	 * @param target
	 */
	private void initialiseHit(Actor target)
	{
		toggleDirection = toggleControl((ControlButtonSprite) target);

		setControl((ControlButtonSprite) target, toggleDirection);

		showPointer(pointer, target);
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
		case AppEvents.EVENT_PAD_TOUCH_UP:
			handleTouchUp(actor);
			handled = false;
			break;
		case AppEvents.EVENT_PAD_TOUCH_DOWN:
			handleTouchDown(actor);
			handled = false;
			break;
		case AppEvents.EVENT_PAD_TOUCH_DRAGGED:
			handleTouchDragged(actor);
			handled = true;
			break;
		case AppEvents.EVENT_TICK:
			handleTick();
			handled = false;
			break;
		default:
			break;
		}

		return handled;
	}

	/**
	 * Handle bpm 'tick'.
	 * 
	 */
	private void handleTick()
	{
		int bar = noteModel.getPlayed();

		for (int note = 0; note < rowSize; note++)
		{
			if (noteModel.getState(panel, bar, note))
			{
				control[bar][note].animate();
			}
		}

	}

	/**
	 * Handle touch down event from the TouchLayer.
	 * 
	 * @param actor
	 */
	private void handleTouchDown(Actor actor)
	{
		Actor target = SceneHelper.hit(actor.getX(), actor.getY(), this, ControlButtonSprite.class);

		if (target != null)
		{
			selected = target;

			initialiseHit(target);
		}

	}

	/**
	 * Handle touch down event from the TouchLayer.
	 * 
	 * @param actor
	 */
	private void handleTouchDragged(Actor actor)
	{
		Actor target = SceneHelper.hit(actor.getX(), actor.getY(), this, ControlButtonSprite.class);

		if (target != null)
		{
			// If dragging onto a button from outside button area.
			if (selected == null)
			{
				selected = target;

				initialiseHit(target);
			}

			if (target != selected)
			{
				selected = target;

				setControl((ControlButtonSprite) target, toggleDirection);
			}

			showPointer(pointer, target);
		}

	}

	/**
	 * Handle touch up event from the TouchLayer.
	 * 
	 * @param actor
	 */
	private void handleTouchUp(Actor actor)
	{
		hidePointer();

		selected = null;
	}

}
