/*
 * -----------------------------------------------------------------------
 * Copyright 2012 - Alistair Rutherford - www.netthreads.co.uk
 * -----------------------------------------------------------------------
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.netthreads.gdx.app.layer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.netthreads.gdx.app.definition.AppEvents;
import com.netthreads.gdx.app.properties.AppProperties;
import com.netthreads.libgdx.director.AppInjector;
import com.netthreads.libgdx.director.Director;
import com.netthreads.libgdx.scene.Layer;

/**
 * Settings layer.
 * 
 */
public class SettingsLayer extends Layer
{
	private static final String UI_FILE = "data/uiskin32.json";
	private static final String URL_LABEL_FONT = "normal-font";

	private static final float MIN_VELOCITY = 0;
	private static final float MAX_VELOCITY = 127;

	private static final float MIN_BPM = 10;
	private static final float MAX_BPM = 480;
	private static final float INC_BPM = 10;

	private Table table;
	private Skin skin;

	private Slider velocityValueSlider;
	private Label velocityValueLabel;
	private Slider bpmSlider;
	private Label bpmValueLabel;

	private TextField portValue;
	private TextField hostValue;

	private Director director;
	private AppProperties appProperties;

	/**
	 * Settings layer.
	 * 
	 * @param stage
	 *            The display stage.
	 */
	public SettingsLayer(float width, float height)
	{
		setWidth(width);
		setHeight(height);

		director = AppInjector.getInjector().getInstance(Director.class);

		appProperties = AppInjector.getInjector().getInstance(AppProperties.class);

		Gdx.input.setCatchBackKey(true);

		loadTextures();

		buildElements();

	}

	@Override
	public void enter()
	{
		super.enter();

		// Update display
		updateVelocityText();
		updateBpmText();
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
		// ---------------------------------------------------------------
		// Elements
		// ---------------------------------------------------------------
		final Label titleLabel = new Label("Settings", skin, URL_LABEL_FONT, Color.YELLOW);

		// Host
		final Label hostLabel = new Label("Host", skin);
		hostValue = new TextField("", skin);

		// Port
		final Label portLabel = new Label("Port", skin);
		portValue = new TextField("", skin);

		// Velocity
		final Label velocityLabel = new Label("Velocity", skin);
		velocityValueSlider = new Slider(MIN_VELOCITY, MAX_VELOCITY, 1, false, skin);
		velocityValueLabel = new Label("", skin);

		// BPM
		final Label bpmLabel = new Label("BPM", skin);
		bpmSlider = new Slider(MIN_BPM, MAX_BPM, INC_BPM, false, skin);
		bpmValueLabel = new Label("", skin);

		// ---------------------------------------------------------------
		// Table
		// ---------------------------------------------------------------

		table = new Table();

		table.setSize(getWidth(), getHeight());

		table.add(titleLabel).expandX();
		table.row();
		table.add(hostLabel).expandY();
		table.add(hostValue);
		table.row();
		table.add(portLabel).expandY();
		table.add(portValue);
		table.row();
		table.add(velocityLabel).expandY();
		table.add(velocityValueSlider);
		table.add(velocityValueLabel).padRight(100);
		table.row();
		table.add(bpmLabel).expandY();
		table.add(bpmSlider);
		table.add(bpmValueLabel).padRight(100);

		table.setFillParent(true);

		table.pack();

		addActor(table);

		// Update display
		updateHostText();
		updatePortText();
		updateVelocityText();
		updateBpmText();

		// Handlers

		velocityValueSlider.addListener(new ChangeListener()
		{

			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				Slider slider = (Slider) actor;

				float value = slider.getValue();

				if (value == 0)
				{
					appProperties.setVelocity(0);
				}
				else
				{
					appProperties.setVelocity((int) value);
				}

				updateVelocityText();
			}

		});

		final SettingsLayer layer = this;

		bpmSlider.addListener(new ChangeListener()
		{

			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				Slider slider = (Slider) actor;

				float value = slider.getValue();

				if (value == 0)
				{
					appProperties.setBpm(0);
				}
				else
				{
					appProperties.setBpm((int) value);
				}

				updateBpmText();
			}

		});

		hostValue.addListener(new InputListener()
		{
			public boolean keyUp(InputEvent event, int keycode)
			{
				boolean handled = layer.keyUp(keycode);

				appProperties.setPort(keycode);

				updatePortText();

				if (!handled && (keycode == Keys.BACKSPACE || keycode == Keys.DEL))
				{
					String value = hostValue.getText();
					int cursorPosition = hostValue.getCursorPosition();
					if (cursorPosition > 0 && value.length() > 0)
					{
						value = value.substring(0, value.length());
						hostValue.setText(value);
						hostValue.setCursorPosition(cursorPosition);
					}
				}

				return handled;
			};

		});

		portValue.addListener(new InputListener()
		{
			public boolean keyUp(InputEvent event, int keycode)
			{
				boolean handled = layer.keyUp(keycode);

				if (!handled && (keycode == Keys.BACKSPACE || keycode == Keys.DEL))
				{
					String value = portValue.getText();
					int cursorPosition = portValue.getCursorPosition();
					if (cursorPosition > 0 && value.length() > 0)
					{
						value = value.substring(0, value.length());
						portValue.setText(value);
						portValue.setCursorPosition(cursorPosition);
					}
				}

				return handled;
			};
		});

	}

	/**
	 * Catch escape key.
	 * 
	 */
	@Override
	public boolean keyUp(int keycode)
	{
		boolean handled = false;

		if (keycode == Keys.BACK || keycode == Keys.ESCAPE)
		{
			try
			{
				// Update values. We can't assign empty values.
				String hostText = hostValue.getText();
				if (hostText != null && !hostText.isEmpty())
				{
					appProperties.setHost(hostText);
				}

				String portText = portValue.getText();
				if (portText != null && !portText.isEmpty())
				{
					int port = Integer.valueOf(portText);

					appProperties.setPort(port);
				}
			}
			catch (Exception e)
			{
				// Error
			}

			director.sendEvent(AppEvents.EVENT_TRANSITION_TO_MENU_SCENE, this);

			handled = true;
		}

		return handled;
	}

	/**
	 * Reformat text. Call invalidate to ensure it gets redrawn.
	 * 
	 */
	private void updateVelocityText()
	{
		float value = appProperties.getVelocity();

		velocityValueSlider.setValue(value);

		velocityValueLabel.setText(appProperties.getVelocityText());
		velocityValueLabel.invalidate();
	}

	/**
	 * Reformat text. Call invalidate to ensure it gets redrawn.
	 * 
	 */
	private void updateBpmText()
	{
		float value = appProperties.getBpm();

		bpmSlider.setValue(value);

		bpmValueLabel.setText(appProperties.getBpmText());
		bpmValueLabel.invalidate();
	}

	/**
	 * Update text.
	 * 
	 */
	private void updateHostText()
	{
		String value = appProperties.getHost();

		hostValue.setText(value);
	}

	/**
	 * Update text.
	 * 
	 */
	private void updatePortText()
	{
		String value = appProperties.getPortText();

		portValue.setText(value);
	}
}
