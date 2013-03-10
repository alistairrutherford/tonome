package com.netthreads.gdx.layer.surface;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.netthreads.gdx.app.definition.AppEvents;
import com.netthreads.gdx.model.PanelModel;
import com.netthreads.gdx.sprite.NavigationButtonSprite;
import com.netthreads.gdx.sprite.NavigationButtonSprite.Direction;
import com.netthreads.libgdx.director.AppInjector;
import com.netthreads.libgdx.director.Director;
import com.netthreads.libgdx.event.ActorEvent;
import com.netthreads.libgdx.event.ActorEventObserver;
import com.netthreads.libgdx.scene.Layer;
import com.netthreads.libgdx.scene.SceneHelper;
import com.netthreads.libgdx.texture.TextureCache;
import com.netthreads.libgdx.texture.TextureDefinition;

/**
 * Creates touch areas which lets us navigate between panels.
 * 
 */
public class NavigationLayer extends Layer implements ActorEventObserver
{
	private static int BUTTON_COUNT = 8;

	private PanelModel panelModel;

	// Director of the action.
	private Director director;

	private TextureCache textureCache;

	private NavigationButtonSprite[] buttons;

	/**
	 * Create navigation layer.
	 * 
	 * @param width
	 *            View width.
	 * @param height
	 *            View height.
	 */
	public NavigationLayer(float width, float height, int panel)
	{
		setWidth(width);
		setHeight(height);

		// Director of the action.
		director = AppInjector.getInjector().getInstance(Director.class);

		textureCache = AppInjector.getInjector().getInstance(TextureCache.class);

		panelModel = AppInjector.getInjector().getInstance(PanelModel.class);

		// ---------------------------------------------------------------
		// Build view elements.
		// ---------------------------------------------------------------
		buildView(panel);

		// ---------------------------------------------------------------
		// Arrange navigation elements.
		// ---------------------------------------------------------------
		activateView(panel);
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
	private void buildView(int panel)
	{
		buttons = buildButtons(panel);

		initialiseButtons(buttons);
	}

	/**
	 * Build button structure.
	 * 
	 * @return The buttons structure.
	 */
	private NavigationButtonSprite[] buildButtons(int panel)
	{
		// Buttons representing each corner of the panel.
		NavigationButtonSprite[] button = new NavigationButtonSprite[BUTTON_COUNT];

		int[] configuration = panelModel.getConfiguration(panel);
		String[] definition = panelModel.getDefinition();
		Direction[] directions = panelModel.getDirections();

		// ---------------------------------------------------------------
		// Only make those required for panel.
		// ---------------------------------------------------------------

		for (int i = 0; i < BUTTON_COUNT; i++)
		{
			// Only make button if defined for panel.
			if (configuration[i] != -1)
			{
				NavigationButtonSprite navigationButtonSprite = makeButton(configuration[i], definition[i], directions[i]);
				button[i] = navigationButtonSprite;

				// Position button accordingly.
				switch (i)
				{
				case 0:
					navigationButtonSprite.setX(0);
					navigationButtonSprite.setY(this.getHeight() - navigationButtonSprite.getHeight());
					break;
				case 1:
					navigationButtonSprite.setX((this.getWidth() - navigationButtonSprite.getWidth()) / 2);
					navigationButtonSprite.setY(this.getHeight() - navigationButtonSprite.getHeight());
					break;
				case 2:
					navigationButtonSprite.setX(this.getWidth() - navigationButtonSprite.getWidth());
					navigationButtonSprite.setY(this.getHeight() - navigationButtonSprite.getHeight());
					break;
				case 3:
					navigationButtonSprite.setX(this.getWidth() - navigationButtonSprite.getWidth());
					navigationButtonSprite.setY((this.getHeight() - navigationButtonSprite.getHeight()) / 2);
					break;
				case 4:
					navigationButtonSprite.setX(this.getWidth() - navigationButtonSprite.getWidth());
					navigationButtonSprite.setY(0);
					break;
				case 5:
					navigationButtonSprite.setX((this.getWidth() - navigationButtonSprite.getWidth()) / 2);
					navigationButtonSprite.setY(0);
					break;
				case 6:
					navigationButtonSprite.setX(0);
					navigationButtonSprite.setY(0);
					break;
				case 7:
					navigationButtonSprite.setX(0);
					navigationButtonSprite.setY((this.getHeight() - navigationButtonSprite.getHeight()) / 2);
					break;
				default:
					break;
				}
			}
		}

		return button;
	}

	/**
	 * Make a navigation button.
	 * 
	 * @param targetId
	 * @param definition
	 * 
	 * @return The button.
	 */
	public NavigationButtonSprite makeButton(int targetId, String definition, Direction direction)
	{
		TextureDefinition textureDefinition = textureCache.getDefinition(definition);
		TextureRegion textureRegion = textureCache.getTexture(textureDefinition);
		NavigationButtonSprite scalableStateSprite = new NavigationButtonSprite(textureRegion, 1, 2, targetId, direction);

		return scalableStateSprite;
	}

	/**
	 * Initialise button list.
	 * 
	 * @param buttons
	 */
	private void initialiseButtons(NavigationButtonSprite[] buttons)
	{
		// ---------------------------------------------------------------
		// Add buttons.
		// ---------------------------------------------------------------
		for (int i = 0; i < BUTTON_COUNT; i++)
		{
			NavigationButtonSprite scalableStateSprite = buttons[i];

			if (scalableStateSprite != null)
			{
				scalableStateSprite.setVisible(false);

				addActor(buttons[i]);
			}
		}
	}

	/**
	 * Depending on panel activate/deactivate buttons for navigation.
	 * 
	 */
	private void activateView(int panel)
	{
		int[] configuration = panelModel.getConfiguration(panel);

		for (int i = 0; i < BUTTON_COUNT; i++)
		{
			if (configuration[i] != -1)
			{
				buttons[i].setVisible(true);
			}
		}
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
		case AppEvents.EVENT_PAD_TOUCH_DOWN:
			handleTouchDown(actor);
			handled = true;
			break;
		case AppEvents.EVENT_TICK:
			handleTick();
			handled = true;
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
		for (int i = 0; i < BUTTON_COUNT; i++)
		{
			NavigationButtonSprite scalableStateSprite = buttons[i];

			if (scalableStateSprite != null)
			{
				if (scalableStateSprite.isVisible())
				{
					scalableStateSprite.animate();
				}
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
		Actor target = SceneHelper.hit(actor.getX(), actor.getY(), this, NavigationButtonSprite.class);

		if (target != null)
		{
			NavigationButtonSprite navigationButtonSprite = (NavigationButtonSprite) target;

			panelModel.transitionToPanel(navigationButtonSprite.getTargetId(), navigationButtonSprite.getDirection());
		}

	}

}
