package com.netthreads.gdx.app.definition;

import java.util.LinkedList;
import java.util.List;

import com.netthreads.libgdx.texture.TextureDefinition;

/**
 * You can populate this if you are not using a predefined packed texture created using the TexturePacker class.
 * 
 */
@SuppressWarnings("serial")
public class AppTextures
{
	public static final String TEXTURE_PATH = "data/gfx";
	public static final String TEXTURE_LIBGDX_LOGO = "libgdx.png";
	public static final String TEXTURE_BUTTON = "buttonB.png";
	public static final String TEXTURE_BAR = "barA.png";
	public static final String TEXTURE_POINTER = "pointerA.png";
	public static final String TEXTURE_ARROW_TL = "arrow_tl.png";
	public static final String TEXTURE_ARROW_TR = "arrow_tr.png";
	public static final String TEXTURE_ARROW_BL = "arrow_bl.png";
	public static final String TEXTURE_ARROW_BR = "arrow_br.png";
	public static final String TEXTURE_ARROW_L = "arrow_l.png";
	public static final String TEXTURE_ARROW_R = "arrow_r.png";
	public static final String TEXTURE_ARROW_U = "arrow_u.png";
	public static final String TEXTURE_ARROW_D = "arrow_d.png";

	public static final List<TextureDefinition> TEXTURES = new LinkedList<TextureDefinition>()
	{
		{
			add(new TextureDefinition(TEXTURE_LIBGDX_LOGO, TEXTURE_PATH + "/" + TEXTURE_LIBGDX_LOGO));
			add(new TextureDefinition(TEXTURE_BUTTON, TEXTURE_PATH + "/" + TEXTURE_BUTTON));
			add(new TextureDefinition(TEXTURE_BAR, TEXTURE_PATH + "/" + TEXTURE_BAR));
			add(new TextureDefinition(TEXTURE_POINTER, TEXTURE_PATH + "/" + TEXTURE_POINTER));
			add(new TextureDefinition(TEXTURE_ARROW_TL, TEXTURE_PATH + "/" + TEXTURE_ARROW_TL));
			add(new TextureDefinition(TEXTURE_ARROW_TR, TEXTURE_PATH + "/" + TEXTURE_ARROW_TR));
			add(new TextureDefinition(TEXTURE_ARROW_BL, TEXTURE_PATH + "/" + TEXTURE_ARROW_BL));
			add(new TextureDefinition(TEXTURE_ARROW_BR, TEXTURE_PATH + "/" + TEXTURE_ARROW_BR));
			add(new TextureDefinition(TEXTURE_ARROW_L, TEXTURE_PATH + "/" + TEXTURE_ARROW_L));
			add(new TextureDefinition(TEXTURE_ARROW_R, TEXTURE_PATH + "/" + TEXTURE_ARROW_R));
			add(new TextureDefinition(TEXTURE_ARROW_U, TEXTURE_PATH + "/" + TEXTURE_ARROW_U));
			add(new TextureDefinition(TEXTURE_ARROW_D, TEXTURE_PATH + "/" + TEXTURE_ARROW_D));
		}
	};
}
