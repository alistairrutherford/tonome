package com.netthreads.gdx.html;

import com.netthreads.gdx.core.Tonome;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class TonomeHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new Tonome();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}
