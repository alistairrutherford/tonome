package com.netthreads.gdx.app.properties;

import com.google.inject.ImplementedBy;

/**
 * Global settings.
 * 
 */
@ImplementedBy(AppPropertiesImpl.class)
public interface AppProperties
{
	public static final String DEFAULT_HOST = "localhost";
	public static final int DEFAULT_PORT = 9000;
	public static final int DEFAULT_VELOCITY = 127;
	public static final int DEFAULT_BPM = 240;

	public int getVelocity();

	public void setVelocity(int velocity);

	public String getVelocityText();

	public int getBpm();

	public void setBpm(int bpm);

	public String getBpmText();
	
	public String getHost();

	public void setHost(String host);

	public int getPort();

	public void setPort(int port);

	public String getPortText();
	
}
