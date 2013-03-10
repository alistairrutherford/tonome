package com.netthreads.gdx.layer.surface;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.netthreads.audio.note.service.NoteModel;
import com.netthreads.gdx.app.definition.AppEvents;
import com.netthreads.gdx.app.definition.AppSize;
import com.netthreads.gdx.app.definition.AppTextures;
import com.netthreads.gdx.sprite.BarSprite;
import com.netthreads.libgdx.director.AppInjector;
import com.netthreads.libgdx.director.Director;
import com.netthreads.libgdx.event.ActorEvent;
import com.netthreads.libgdx.event.ActorEventObserver;
import com.netthreads.libgdx.scene.Layer;
import com.netthreads.libgdx.texture.TextureCache;
import com.netthreads.libgdx.texture.TextureDefinition;

/**
 * Bar display layer.
 * 
 */
public class BarLayer extends Layer implements ActorEventObserver
{
	// Director of the action.
	private Director director;

	private TextureCache textureCache;

	private int rowSize;
	private int columnSize;

	private BarSprite lastBar;

	private BarSprite[] bars;

	private NoteModel noteModel;

	/**
	 * Create main layer which composes all the main application layers.
	 * 
	 * @param width
	 * @param height
	 */
	public BarLayer(float width, float height, int rowSize, int columnSize)
	{
		setWidth(width);
		setHeight(height);

		this.rowSize = rowSize;
		this.columnSize = columnSize;

		director = AppInjector.getInjector().getInstance(Director.class);
		textureCache = AppInjector.getInjector().getInstance(TextureCache.class);

		noteModel = AppInjector.getInjector().getInstance(NoteModel.class);

		// ---------------------------------------------------------------
		// Build view elements.
		// ---------------------------------------------------------------
		buildView();
	}

	/**
	 * Enter scene handler.
	 * 
	 */
	@Override
	public void enter()
	{
		super.enter();

		setBar();

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
	 * Place 'bar' just above the note buttons.
	 * 
	 */
	private void buildView()
	{
		// Create bar structure
		this.bars = new BarSprite[columnSize];

		int startX = LayoutHelper.getStartX(rowSize, columnSize, getWidth(), getHeight());
		int startY = LayoutHelper.getBarStartY(rowSize, columnSize, getWidth(), getHeight());

		int xIncrement = LayoutHelper.getXIncrement(rowSize, columnSize);

		for (int column = 0; column < columnSize; column++)
		{
			BarSprite barSprite = newBar();

			this.bars[column] = barSprite;

			barSprite.setX(startX + (column * xIncrement));
			barSprite.setY(startY);

			addActor(barSprite);
		}

		// Initialise
		this.lastBar = bars[0];
	}

	/**
	 * Create bar.
	 * 
	 * @return A new bar.
	 */
	private BarSprite newBar()
	{
		TextureDefinition definition = textureCache.getDefinition(AppTextures.TEXTURE_BAR);
		TextureRegion textureRegion = textureCache.getTexture(definition);
		BarSprite sprite = new BarSprite(textureRegion);
		sprite.setScaleX(AppSize.BUTTON_SPRITE_WIDTH / sprite.getWidth());
		sprite.setScaleY(AppSize.BUTTON_SPRITE_HEIGHT / sprite.getHeight() / 2);
		sprite.setState(false);

		return sprite;
	}

	/**
	 * Light up appropriate bar.
	 * 
	 */
	private void setBar()
	{
		lastBar.setState(false);

		int played = noteModel.getPlayed();

		lastBar = this.bars[played];

		lastBar.setState(true);
	}

	/**
	 * Handle events.
	 * 
	 */
	@Override
	public boolean handleEvent(ActorEvent event)
	{
		boolean handled = false;

		switch (event.getId())
		{
		case AppEvents.EVENT_TICK:
			handleTick();
			break;
		default:
			break;
		}

		return handled;
	}

	/**
	 * Handle tick.
	 * 
	 * @param actor
	 */
	private void handleTick()
	{
		setBar();
	}

}
