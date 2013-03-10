package com.netthreads.gdx.layer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.netthreads.libgdx.scene.Layer;

/**
 * FPS Layer.
 * 
 * Note: I have reused the FPS StringBuilder technique from Robert Greens website:
 * http://www.rbgrn.net/content/290-light-racer-20-days-32-33-getting-great -game-performance
 * 
 */
public class FpsLayer extends Layer
{
	private static final String FONT_FILE_SMALL = "data/default.fnt";
	private static final String FONT_IMAGE_SMALL = "data/default.png";

	private static final String TEXT_FPS = "fps:";

	private BitmapFont smallFont;

	static final char chars[] = new char[100];
	static final StringBuilder textStringBuilder = new StringBuilder(100);

	/**
	 * Create layer which displays the FPS in the bottom corner.
	 * 
	 * @param width
	 * @param height
	 */
	public FpsLayer(float width, float height)
	{
		this.setWidth(width);
		this.setHeight(height);

		buildElements();
	}

	/**
	 * Build view elements.
	 * 
	 */
	private void buildElements()
	{
		smallFont = new BitmapFont(Gdx.files.internal(FONT_FILE_SMALL), Gdx.files.internal(FONT_IMAGE_SMALL), false);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha)
	{
		// FPS
		textStringBuilder.setLength(0);
		textStringBuilder.append(TEXT_FPS);
		textStringBuilder.append(Gdx.graphics.getFramesPerSecond());
		textStringBuilder.getChars(0, textStringBuilder.length(), chars, 0);

		smallFont.draw(batch, textStringBuilder, 10, 15);
	}
}
