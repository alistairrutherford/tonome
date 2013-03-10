package com.netthreads.gdx.app.properties;

import com.google.inject.Singleton;

/**
 * Global settings.
 * 
 */
@Singleton
public class AppPropertiesImpl implements AppProperties
{
	private StringBuilder portText = new StringBuilder(10);
	private StringBuilder velocityText = new StringBuilder(10);
	private StringBuilder bpmText = new StringBuilder(10);

	private int velocity = DEFAULT_VELOCITY;
	private int bpm = DEFAULT_BPM;
	private String host = DEFAULT_HOST;
	private int port = DEFAULT_PORT;

	/**
	 * Constructor.
	 * 
	 * Note we set the number properties through their setters to ensure the text descriptions are updated.
	 */
	public AppPropertiesImpl()
	{
		setBpm(DEFAULT_BPM);
		setVelocity(DEFAULT_VELOCITY);
		setPort(DEFAULT_PORT);
	}

	public int getVelocity()
	{
		return velocity;
	}

	public void setVelocity(int velocity)
	{
		velocityText.setLength(0);
		velocityText.append(velocity);

		this.velocity = velocity;
	}

	public String getVelocityText()
	{
		return velocityText.toString();
	}

	public int getBpm()
	{
		return bpm;
	}

	public void setBpm(int bpm)
	{
		bpmText.setLength(0);
		bpmText.append(velocity);

		this.bpm = bpm;
	}

	public String getBpmText()
	{
		return bpmText.toString();
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
		portText.setLength(0);
		portText.append(port);

		this.port = port;
	}

	public String getPortText()
	{
		return portText.toString();
	}

}
