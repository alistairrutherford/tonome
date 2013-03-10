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
import com.netthreads.gdx.app.Tonome;
import com.netthreads.gdx.app.definition.AppEvents;
import com.netthreads.libgdx.director.AppInjector;
import com.netthreads.libgdx.director.Director;
import com.netthreads.libgdx.scene.Layer;

/**
 * Menu layer.
 * 
 */
public class MenuLayer extends Layer
{
	private static final String UI_FILE = "data/uiskin32.json";
	private static final String UI_FONT = "normal-font";

	private Table table;
	private Skin skin;
	private Label titleLabel;
	private Button startButton;
	private Button settingsButton;
	private Button aboutButton;

	// Director of the action.
	private Director director;

	/**
	 * Construct the screen.
	 * 
	 * @param stage
	 */
	public MenuLayer(float width, float height)
	{
		setWidth(width);
		setHeight(height);

		director = AppInjector.getInjector().getInstance(Director.class);

		Gdx.input.setCatchBackKey(true);

		loadTextures();

		buildElements();
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
		titleLabel = new Label(Tonome.APPLICATION_NAME, skin, UI_FONT, Color.WHITE);

		// ---------------------------------------------------------------
		// Buttons.
		// ---------------------------------------------------------------
		startButton = new TextButton("Start", skin);
		settingsButton = new TextButton("Settings", skin);
		aboutButton = new TextButton("About", skin);

		// ---------------------------------------------------------------
		// Table
		// ---------------------------------------------------------------
		table = new Table();

		table.setSize(getWidth(), getHeight());

		table.row();
		table.add(titleLabel).expandY().expandX();
		table.row();
		table.add(startButton).expandY().expandX();
		table.row();
		table.add(settingsButton).expandY().expandX();
		table.row();
		table.add(aboutButton).expandY().expandX();

		table.setFillParent(true);

		table.pack();

		// Listener.
		startButton.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				director.sendEvent(AppEvents.EVENT_TRANSITION_TO_CONNECTING_SCENE, event.getRelatedActor());
			}

		});

		// Listener.
		settingsButton.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				director.sendEvent(AppEvents.EVENT_TRANSITION_TO_SETTINGS_SCENE, event.getRelatedActor());
			}

		});

		// Listener.
		aboutButton.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				director.sendEvent(AppEvents.EVENT_TRANSITION_TO_ABOUT_SCENE, event.getRelatedActor());
			}

		});

		// Add table to view
		addActor(table);

	}
}
