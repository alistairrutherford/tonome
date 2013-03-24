package com.netthreads.gdx.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.netthreads.gdx.core.Tonome;

public class TonomeDesktop
{
	private static final String APPLICATION_NAME = "Tonome";

	public static void main(String[] args)
	{
		// last parameter false = use OpenGL 1.1 and not 2.1+
		new LwjglApplication(new Tonome(), APPLICATION_NAME, Tonome.DEFAULT_WIDTH, Tonome.DEFAULT_HEIGHT, false);
	}
}
