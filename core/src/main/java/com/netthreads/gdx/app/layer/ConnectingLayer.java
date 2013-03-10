package com.netthreads.gdx.app.layer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.netthreads.gdx.app.definition.AppEvents;
import com.netthreads.gdx.app.properties.AppProperties;
import com.netthreads.libgdx.director.AppInjector;
import com.netthreads.libgdx.director.Director;
import com.netthreads.libgdx.event.ActorEvent;
import com.netthreads.libgdx.event.ActorEventObserver;
import com.netthreads.libgdx.scene.Layer;
import com.netthreads.network.osc.client.OSCClient;

/**
 * Attempts to connect to OSC end-point.
 * 
 */
public class ConnectingLayer extends Layer implements ActorEventObserver
{
	private static final String UI_FILE = "data/uiskin32.json";
	private static final String LABEL_FONT = "default-font";
	
	private static final String TEXT_CONNECTING = "Connect to server";
	
	private Table table;
	private Skin skin;
	private Label loadingLabel;
	
	private Button connectButton;
	
	// Director of the action.
	private Director director;
	
	private OSCClient oscClient;
	
	private AppProperties appProperties;
	
	/**
	 * Construct the screen.
	 * 
	 * @param stage
	 */
	public ConnectingLayer(float width, float height)
	{
		setWidth(width);
		setHeight(height);
		
		director = AppInjector.getInjector().getInstance(Director.class);

		appProperties = AppInjector.getInjector().getInstance(AppProperties.class);
		
		oscClient = AppInjector.getInjector().getInstance(OSCClient.class);
		
		loadTextures();
		
		buildElements();
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
	 * Load view textures.
	 * 
	 */
	private void loadTextures()
	{
		skin = new Skin(Gdx.files.internal(UI_FILE));
	}
	
	/**
	 * Build view elements.
	 * 
	 */
	private void buildElements()
	{
		// Title
		loadingLabel = new Label(TEXT_CONNECTING, skin, LABEL_FONT, Color.WHITE);
		
		// ---------------------------------------------------------------
		// Buttons.
		// ---------------------------------------------------------------
		connectButton = new TextButton("Connect", skin);
		
		// Table
		// ---------------------------------------------------------------
		table = new Table();

		table.setSize(getWidth(), getHeight());

		table.row();
		
		table.row();
		table.add(loadingLabel).expandY().expandX();
		table.row();
		table.add(connectButton).expandY().expandX();
		table.row();
		
		table.setFillParent(true);

		// Listener.
		// Listener.
		connectButton.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				director.sendEvent(AppEvents.EVENT_CONNECT_OSC_START, null);
			}

		});
		
		// Add table to view
		addActor(table);
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
			case AppEvents.EVENT_CONNECT_OSC_START:
				handleConnectStart();
				handled = true;
				break;
			case AppEvents.EVENT_CONNECT_OSC_SUCCESS:
				handleConnectSuccess();
				handled = true;
				break;
			default:
				break;
		}
		
		return handled;
	}
	
	/**
	 * Start connection attempt handler.
	 * 
	 */
	private void handleConnectStart()
	{
		if (oscClient.connect(appProperties.getHost(), appProperties.getPort()))
		{
			director.sendEvent(AppEvents.EVENT_CONNECT_OSC_SUCCESS, null);
		}
	}
	
	/**
	 * Successful connection handler.
	 * 
	 */
	private void handleConnectSuccess()
	{
		/** TODO PUT AUDIO END HERE */
		
		director.sendEvent(AppEvents.EVENT_TRANSITION_TO_MENU_SCENE, null);
	}
}
