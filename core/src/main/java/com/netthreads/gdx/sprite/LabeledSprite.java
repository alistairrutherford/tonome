package com.netthreads.gdx.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.netthreads.libgdx.sprite.SimpleSprite;

/**
 * Texture and text sprite.
 * 
 */
public class LabeledSprite extends SimpleSprite
{
	private static final String FONT_FILE_LARGE = "data/font_large.fnt";
	private static final String FONT_IMAGE_LARGE = "data/font_large.png";
	
	private TextSprite labelSprite;
	
	/**
	 * Construct sprite.
	 * 
	 * @param textureRegion
	 *            The sprite texture.
	 */
	public LabeledSprite(TextureRegion textureRegion)
	{
		super(textureRegion);
		
		labelSprite = new TextSprite(Gdx.files.internal(FONT_FILE_LARGE), Gdx.files.internal(FONT_IMAGE_LARGE), Color.WHITE);
		
		addActor(labelSprite);
	}
	
	/**
	 * Assign text.
	 * 
	 * @param text
	 */
	public void setText(CharSequence text)
	{
		if (this.labelSprite != null)
		{
			this.labelSprite.setText(text);
			
			this.labelSprite.setOriginX(getOriginX() - labelSprite.getWidth() / 2);
			this.labelSprite.setOriginY(getOriginY() + labelSprite.getHeight() / 2);
		}
	}
	
	/**
	 * Assign text.
	 * 
	 * @param text
	 */
	public void setText(Character character)
	{
		if (this.labelSprite != null)
		{
			this.labelSprite.getText().setLength(0);
			this.labelSprite.getText().append(character);
			
			this.labelSprite.setOriginX(getOriginX() - labelSprite.getWidth() / 2);
			this.labelSprite.setOriginY(getOriginY() + labelSprite.getHeight() / 2);
		}
	}
	
	/**
	 * Return associated text sprite.
	 * 
	 * @return The associated text sprite.
	 */
	public TextSprite getLabelSprite()
	{
		return labelSprite;
	}
	
}
