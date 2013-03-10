package com.netthreads.gdx.app.layer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.netthreads.gdx.app.Tonome;
import com.netthreads.gdx.app.definition.AppTextures;
import com.netthreads.libgdx.director.AppInjector;
import com.netthreads.libgdx.scene.Layer;
import com.netthreads.libgdx.texture.TextureCache;
import com.netthreads.libgdx.texture.TextureDefinition;

/**
 * Scene layer.
 * 
 */
public class AboutLayer extends Layer
{
	private static final String URL_LABEL_FONT = "default-font";
	private static final String UI_FILE = "data/uiskin32.json";

	private Table table;
	private TextureRegion logo;
	private Skin skin;
	private Label urlLabel;
	private Label authorLabel;
	private Label versionLabel;
	private TextureCache textureCache;

	/**
	 * Construct layer.
	 * 
	 * @param stage
	 */
	public AboutLayer(float width, float height)
	{
		setWidth(width);
		setHeight(height);

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

		textureCache = AppInjector.getInjector().getInstance(TextureCache.class);

		TextureDefinition definition = textureCache.getDefinition(AppTextures.TEXTURE_LIBGDX_LOGO);
		logo = textureCache.getTexture(definition);
	}

	/**
	 * Build view elements.
	 * 
	 */
	private void buildElements()
	{
		// ---------------------------------------------------------------
		// Background.
		// ---------------------------------------------------------------
		Image image = new Image(logo);

		image.setWidth(getWidth());
		image.setHeight(getHeight());

		// ---------------------------------------------------------------
		// Labels
		// ---------------------------------------------------------------
		urlLabel = new Label("www.netthreads.co.uk", skin, URL_LABEL_FONT, Color.WHITE);

		authorLabel = new Label("Tonome - (c) Alistair Rutherford", skin, URL_LABEL_FONT, Color.WHITE);;

		versionLabel = new Label(Tonome.VERSION_TEXT, skin, URL_LABEL_FONT, Color.WHITE);

		// ---------------------------------------------------------------
		// Table
		// ---------------------------------------------------------------
		table = new Table();

		table.size((int) getWidth(), (int) getHeight());

		table.row();
		table.add(authorLabel).expandY().expandX();
		table.row();
		table.add(urlLabel).expandY().expandX();
		table.row();
		table.add(versionLabel).expandY().expandX();

		table.pack();

		table.setFillParent(true);

		addActor(table);
	}

}
