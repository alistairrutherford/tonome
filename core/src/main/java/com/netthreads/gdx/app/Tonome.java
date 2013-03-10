package com.netthreads.gdx.app;

import aurelienribon.tweenengine.equations.Linear;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.InputAdapter;
import com.netthreads.audio.note.service.NoteModel;
import com.netthreads.audio.note.service.NoteService;
import com.netthreads.audio.note.service.NoteServiceImpl.TransportState;
import com.netthreads.gdx.app.definition.AppEvents;
import com.netthreads.gdx.app.definition.AppTextures;
import com.netthreads.gdx.app.model.PanelModel;
import com.netthreads.gdx.app.model.PanelModelImpl;
import com.netthreads.gdx.app.scene.AboutScene;
import com.netthreads.gdx.app.scene.ConnectingScene;
import com.netthreads.gdx.app.scene.MenuScene;
import com.netthreads.gdx.app.scene.SettingsScene;
import com.netthreads.gdx.app.sprite.NavigationButtonSprite.Direction;
import com.netthreads.libgdx.director.AppInjector;
import com.netthreads.libgdx.director.Director;
import com.netthreads.libgdx.event.ActorEvent;
import com.netthreads.libgdx.event.ActorEventObserver;
import com.netthreads.libgdx.scene.Scene;
import com.netthreads.libgdx.scene.transition.MoveInBTransitionScene;
import com.netthreads.libgdx.scene.transition.MoveInTTransitionScene;
import com.netthreads.libgdx.scene.transition.TransitionScene;
import com.netthreads.libgdx.texture.TextureCache;
import com.netthreads.network.osc.client.OSCClient;

/**
 * Application controller.
 * 
 */
public class Tonome extends InputAdapter implements ApplicationListener, ActorEventObserver
{
	public static final String APPLICATION_NAME = "Tonome";
	public static final String VERSION_TEXT = "1.0.1";

	public static final int DEFAULT_WIDTH = 480;
	public static final int DEFAULT_HEIGHT = 320;

	private static final int DURATION_TRANSITION_TO_MENU = 250;
	private static final int DURATION_TRANSITION_TO_CONNECTING = 250;
	private static final int DURATION_TRANSITION_TO_SETTINGS = 250;
	private static final int DURATION_TRANSITION_TO_ABOUT = 250;

	// Our 'scenes'.
	private MenuScene menuScene;
	private AboutScene aboutScene;
	private SettingsScene settingsScene;
	private ConnectingScene connectScene;

	// Singletons
	private Director director;
	private NoteService noteService;
	private NoteModel noteModel;
	private PanelModel panelModel;
	private OSCClient oscClient;
	private TextureCache textureCache;

	/**
	 * Create components.
	 * 
	 */
	@Override
	public void create()
	{
		// Instantiate our singletons through Guice.
		director = AppInjector.getInjector().getInstance(Director.class);

		textureCache = AppInjector.getInjector().getInstance(TextureCache.class);
		
		noteService = AppInjector.getInjector().getInstance(NoteService.class);
		noteModel = AppInjector.getInjector().getInstance(NoteModel.class);
		panelModel = AppInjector.getInjector().getInstance(PanelModel.class);
		oscClient = AppInjector.getInjector().getInstance(OSCClient.class);

		// Set initial width and height.
		this.director.setWidth(DEFAULT_WIDTH);
		this.director.setHeight(DEFAULT_HEIGHT);

		// Add this as an event observer.
		this.director.registerEventHandler(this);
		
		// Load/Re-load textures
		textureCache.load(AppTextures.TEXTURES);
		
		// Set up director screen clear colour.
		this.director.setClearColourR(0.0f);
		this.director.setClearColourG(0.0f);
		this.director.setClearColourB(0.0f);

		// Set initial scene.
		this.director.setScene(getMenuScene());
	}

	/**
	 * Resize.
	 * 
	 */
	@Override
	public void resize(int width, int height)
	{
		// Recalculate scale factors for touch events.
		director.recalcScaleFactors(width, height);
	}

	/**
	 * Render view.
	 * 
	 */
	@Override
	public void render()
	{
		// Update director
		director.update();

		// We have to put this here to ensure it gets serviced.
		noteService.update();
	}

	@Override
	public void pause()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void resume()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose()
	{
		// Graphics.
		textureCache.dispose();

		// Views.
		director.setScene(null);

		// Network
		oscClient.disconnect();
	}

	/**
	 * Generate scene.
	 * 
	 * @return The target scene.
	 */
	public MenuScene getMenuScene()
	{
		if (menuScene == null)
		{
			menuScene = new MenuScene();
		}

		return menuScene;
	}

	/**
	 * Generate scene.
	 * 
	 * @return The target scene.
	 */
	public SettingsScene getSettingsScene()
	{
		if (settingsScene == null)
		{
			settingsScene = new SettingsScene();
		}

		return settingsScene;
	}

	/**
	 * Generate scene.
	 * 
	 * @return The target scene.
	 */
	public AboutScene getAboutScene()
	{
		if (aboutScene == null)
		{
			aboutScene = new AboutScene();
		}

		return aboutScene;
	}

	/**
	 * Generate scene.
	 * 
	 * @return The target scene.
	 */
	public ConnectingScene getConnectScene()
	{
		if (connectScene == null)
		{
			connectScene = new ConnectingScene();
		}

		return connectScene;
	}

	/**
	 * Handle controller events.
	 * 
	 * @param event
	 *            The actor event.
	 * 
	 * @return True if handled.
	 */
	@Override
	public boolean handleEvent(ActorEvent event)
	{
		boolean handled = false;

		switch (event.getId())
		{
		case AppEvents.EVENT_TRANSITION_TO_MENU_SCENE:
			handleTransitionToMenuScene();
			handled = true;
			break;
		case AppEvents.EVENT_TRANSITION_TO_SETTINGS_SCENE:
			handleTransitionToSettingsScene();
			handled = true;
			break;
		case AppEvents.EVENT_TRANSITION_TO_CONNECTING_SCENE:
			handleTransitionToConnectingScene();
			handled = true;
			break;
		case AppEvents.EVENT_TRANSITION_TO_PANEL_SCENE:
			handleTransitionToPanelScene();
			handled = true;
			break;
		case AppEvents.EVENT_TRANSITION_TO_ABOUT_SCENE:
			handleTransitionToAboutScene();
			handled = true;
			break;
		default:
			break;
		}

		return handled;
	}

	/**
	 * Run transition.
	 * 
	 */
	private void handleTransitionToMenuScene()
	{
		// Stop the note service.
		noteService.setCurrentTransportState(TransportState.STOP);

		Scene inScene = getMenuScene();
		Scene outScene = this.director.getScene();

		TransitionScene transitionScene = MoveInTTransitionScene.$(inScene, outScene, DURATION_TRANSITION_TO_MENU, Linear.INOUT);

		this.director.setScene(transitionScene);
	}

	/**
	 * Run transition.
	 * 
	 */
	private void handleTransitionToSettingsScene()
	{
		Scene inScene = getSettingsScene();
		Scene outScene = this.director.getScene();

		TransitionScene transitionScene = MoveInBTransitionScene.$(inScene, outScene, DURATION_TRANSITION_TO_SETTINGS, Linear.INOUT);

		this.director.setScene(transitionScene);
	}

	/**
	 * Run transition.
	 * 
	 */
	private void handleTransitionToConnectingScene()
	{
		Scene inScene = getConnectScene();
		Scene outScene = this.director.getScene();

		TransitionScene transitionScene = MoveInBTransitionScene.$(inScene, outScene, DURATION_TRANSITION_TO_CONNECTING, Linear.INOUT);

		this.director.setScene(transitionScene);
	}
	
	/**
	 * Run transition.
	 * 
	 */
	private void handleTransitionToPanelScene()
	{
		// Reset note model
		noteModel.reset();

		// Start panel
		panelModel.transitionToPanel(PanelModelImpl.DEFAULT_PANEL, Direction.DOWN);

		// Reset bar position in note service.
		noteService.setCurrentTransportState(TransportState.STOP);

		// Start playing in note service.
		noteService.setCurrentTransportState(TransportState.PLAY);
	}

	/**
	 * Run transition.
	 * 
	 */
	private void handleTransitionToAboutScene()
	{
		Scene inScene = getAboutScene();
		Scene outScene = this.director.getScene();

		TransitionScene transitionScene = MoveInBTransitionScene.$(inScene, outScene, DURATION_TRANSITION_TO_ABOUT, Linear.INOUT);

		this.director.setScene(transitionScene);
	}

}
