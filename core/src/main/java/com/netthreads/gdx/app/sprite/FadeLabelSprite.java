package com.netthreads.gdx.app.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;

/**
 * Fading label sprite which actions can run on.
 * 
 */
public class FadeLabelSprite extends Actor
{
	private static final String FONT_FILE_SMALL = "data/font_small.fnt";
	private static final String FONT_IMAGE_SMALL = "data/font_small.png";

	private static final float DEFAULT_FADE_TIME = 1.0f;

	private BitmapFont textMessage;
	private static float START_ALPHA = 1;
	private static float END_ALPHA = 0;
	private float fadeTime;

	private StringBuilder text = new StringBuilder(100);

	/**
	 * Create layer which displays the FPS in the bottom corner.
	 * 
	 * @param width
	 * @param height
	 */
	public FadeLabelSprite()
	{
		this.fadeTime = DEFAULT_FADE_TIME;

		buildElements();
	}

	/**
	 * Build view elements.
	 * 
	 */
	private void buildElements()
	{
		textMessage = new BitmapFont(Gdx.files.internal(FONT_FILE_SMALL), Gdx.files.internal(FONT_IMAGE_SMALL), false);
	}

	/**
	 * Run fade action.
	 * 
	 */
	public void run()
	{
		// Initialise alpha.
		getColor().a = START_ALPHA;

		AlphaAction fadeOut = Actions.alpha(END_ALPHA, fadeTime);

		addAction(fadeOut);
	}

	/**
	 * Draw touch message.
	 * 
	 */
	@Override
	public void draw(SpriteBatch batch, float parentAlpha)
	{
		// Fade action is running against the Actor colour so assign it to the
		// text.
		textMessage.setColor(getColor());

		textMessage.setScale(getScaleX(), getScaleY());

		textMessage.draw(batch, text, getX(), getY());
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

		setWidth(textMessage.getBounds(text).width);
	}

	/**
	 * Return time label takes to fade.
	 * 
	 * @return The time label takes to fade.
	 */
	public float getFadeTime()
	{
		return fadeTime;
	}

	/**
	 * Set time label takes to fade.
	 * 
	 * @param fadeTime
	 */
	public void setFadeTime(float fadeTime)
	{
		this.fadeTime = fadeTime;
	}

}
