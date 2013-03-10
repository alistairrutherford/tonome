package com.netthreads.gdx.app.properties;

import com.google.inject.Singleton;

/**
 * Global settings.
 * 
 */
@Singleton
public class AppPropertiesImpl implements AppProperties
{
	private int velocity = DEFAULT_VELOCITY;
	private int bpm = DEFAULT_BPM;
	private String host = DEFAULT_HOST;
	private int port = DEFAULT_PORT;
	
	public int getVelocity()
	{
		return velocity;
	}
	
	public void setVelocity(int velocity)
	{
		this.velocity = velocity;
	}
	
	public int getBpm()
	{
		return bpm;
	}
	
	public void setBpm(int bpm)
	{
		this.bpm = bpm;
	}
	
	public String getHost()
	{
		return host;
	}
	
	public void setHost(String host)
	{
		this.host = host;
	}
	
	public int getPort()
	{
		return port;
	}
	
	public void setPort(int port)
	{
		this.port = port;
	}
	
}
