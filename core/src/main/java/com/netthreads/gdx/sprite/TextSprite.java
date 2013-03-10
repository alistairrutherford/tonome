package com.netthreads.gdx.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Label sprite.
 * 
 */
public class TextSprite extends Actor
{
	private static final String FONT_FILE_LARGE = "data/font_large.fnt";
	private static final String FONT_IMAGE_LARGE = "data/font_large.png";

	private static final int MAX_CHARS = 10;

	private BitmapFont labelMessage;
	private StringBuilder text = new StringBuilder(MAX_CHARS);

	/**
	 * Default text sprite.
	 * 
	 */
	public TextSprite()
	{
		labelMessage = new BitmapFont(Gdx.files.internal(FONT_FILE_LARGE), Gdx.files.internal(FONT_IMAGE_LARGE), false);

		labelMessage.setColor(getColor());

		setText("");
	}

	/**
	 * Construct sprite.
	 * 
	 * @param textureRegion
	 *            The sprite texture.
	 */
	public TextSprite(FileHandle fontFile, FileHandle imageFile, Color color)
	{
		labelMessage = new BitmapFont(fontFile, imageFile, false);

		labelMessage.setColor(color);

		setText("");
	}

	/**
	 * Draw touch message.
	 * 
	 */
	@Override
	public void draw(SpriteBatch batch, float parentAlpha)
	{
		float xPos = getX() + getOriginX();
		float yPos = getY() + getOriginY();

		labelMessage.draw(batch, text, xPos, yPos);
	}

	/**
	 * Assign text.
	 * 
	 * @param text
	 */
	public void setText(CharSequence text)
	{
		this.text.setLength(0);
		this.text.append(text);
	}

	/**
	 * Return label text.
	 * 
	 * @return
	 */
	public StringBuilder getText()
	{
		return text;
	}

	/**
	 * Return primitive item.
	 * 
	 * @return The label primitive.
	 */
	public BitmapFont getLabelMessage()
	{
		return labelMessage;
	}

	/**
	 * Return height of label sprite (Max letter height + descender).
	 * 
	 * @return The height.
	 */
	public float getHeight()
	{
		TextBounds bounds = labelMessage.getBounds(text);

		return bounds.height;
	}

	/**
	 * Return width of message.
	 * 
	 * @return The width.
	 */
	public float getWidth()
	{
		TextBounds bounds = labelMessage.getBounds(text);

		return bounds.width;
	}

}
