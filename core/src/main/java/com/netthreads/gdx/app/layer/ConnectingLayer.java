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
 * Currently you will get thrown back to the menu if the connection doesn't work.
 * 
 */
public class ConnectingLayer extends Layer implements ActorEventObserver
{
	private static final String UI_FILE = "data/uiskin32.json";
	private static final String LABEL_FONT = "default-font";

	private static final String TEXT_CONNECTING = "Connect to server";

	private Table table;
	private Skin skin;
	private Label connectLabel;
	private Label hostLabel;
	private Label portLabel;

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
		connectLabel = new Label(TEXT_CONNECTING, skin, LABEL_FONT, Color.WHITE);
		hostLabel = new Label(appProperties.getHost(), skin, LABEL_FONT, Color.WHITE);
		portLabel = new Label(appProperties.getPortText(), skin, LABEL_FONT, Color.WHITE);

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
		table.add(connectLabel).expandY().expandX();
		table.row();
		table.add(hostLabel).expandY().expandX();
		table.row();
		table.add(portLabel).expandY().expandX();
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
		case AppEvents.EVENT_CONNECT_OSC_FAIL:
			handleConnectFail();
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
		try
		{
			String host = appProperties.getHost();
			int port = appProperties.getPort();

			if (oscClient.connect(host, port))
			{
				director.sendEvent(AppEvents.EVENT_CONNECT_OSC_SUCCESS, null);
			}
		}
		catch (Exception e)
		{
			// Error
		}

		if (!oscClient.isConnected())
		{
			director.sendEvent(AppEvents.EVENT_CONNECT_OSC_FAIL, null);
		}
	}

	/**
	 * Successful connection handler.
	 * 
	 */
	private void handleConnectSuccess()
	{
		/** TODO PUT AUDIO END HERE */

		director.sendEvent(AppEvents.EVENT_TRANSITION_TO_PANEL_SCENE, null);
	}

	/**
	 * Failed connection handler.
	 * 
	 */
	private void handleConnectFail()
	{
		/** TODO PUT AUDIO END HERE */

		director.sendEvent(AppEvents.EVENT_TRANSITION_TO_MENU_SCENE, null);
	}

}
