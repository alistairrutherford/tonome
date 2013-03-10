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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
	private static final String URL_LABEL_FONT = "default-font";

	private static final float MIN_VELOCITY = 0;
	private static final float MAX_VELOCITY = 127;

	private static final float MIN_BPM = 10;
	private static final float MAX_BPM = 480;
	private static final float INC_BPM = 10;

	private Table table;
	private Skin skin;

	private StringBuilder velocityText = new StringBuilder(10);
	private StringBuilder bpmText = new StringBuilder(10);

	private Slider velocitySlider;
	private Label velocityValueLabel;
	private Label bpmValueLabel;
	private Slider bpmSlider;

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
		updateVolumeText();
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
		// Table
		// ---------------------------------------------------------------
		final Label titleLabel = new Label("Settings", skin, URL_LABEL_FONT, Color.YELLOW);

		// Label
		final Label velocityLabel = new Label("Velocity", skin);

		// Control
		velocitySlider = new Slider(MIN_VELOCITY, MAX_VELOCITY, 1, false, skin);

		velocityValueLabel = new Label("", skin);

		// Label
		final Label bpmLabel = new Label("BPM", skin);
		// Control
		bpmSlider = new Slider(MIN_BPM, MAX_BPM, INC_BPM, false, skin);
		// Value
		bpmSlider.setValue(appProperties.getBpm());

		bpmValueLabel = new Label("", skin);

		table = new Table();

		table.setSize(getWidth(), getHeight());

		table.add(titleLabel).expandX();
		table.row();
		table.add(velocityLabel).expandY();
		table.add(velocitySlider);
		table.add(velocityValueLabel).padRight(100);
		table.row();
		table.add(bpmLabel).expandY();
		table.add(bpmSlider);
		table.add(bpmValueLabel).padRight(100);

		table.setFillParent(true);
		
		table.pack();

		addActor(table);

		// Update display
		updateVolumeText();
		updateBpmText();

		// Handlers

		velocitySlider.addListener(new ChangeListener()
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
			}

		});

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
			}

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
			director.sendEvent(AppEvents.EVENT_TRANSITION_TO_MENU_SCENE, this);

			handled = true;
		}

		return handled;
	}

	/**
	 * Reformat text. Call invalidate to ensure it gets redrawn.
	 * 
	 */
	private void updateVolumeText()
	{
		float value = appProperties.getVelocity();

		// Value
		velocitySlider.setValue(value);

		velocityText.setLength(0);
		velocityText.append(Math.abs(value));
		velocityValueLabel.setText(velocityText);
		velocityValueLabel.invalidate();
	}

	/**
	 * Reformat text. Call invalidate to ensure it gets redrawn.
	 * 
	 */
	private void updateBpmText()
	{
		float value = appProperties.getBpm();

		bpmText.setLength(0);
		bpmText.append(value);
		bpmValueLabel.setText(bpmText);
		bpmValueLabel.invalidate();
	}

}
