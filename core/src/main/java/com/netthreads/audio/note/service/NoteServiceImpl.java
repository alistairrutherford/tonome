package com.netthreads.audio.note.service;

import com.badlogic.gdx.Gdx;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.netthreads.gdx.app.definition.AppEvents;
import com.netthreads.gdx.app.properties.AppProperties;
import com.netthreads.libgdx.director.AppInjector;
import com.netthreads.libgdx.director.Director;

/**
 * Service plays notes from model while 'play' activated.
 * 
 */
@Singleton
public class NoteServiceImpl implements NoteService
{
	private Director director;
	private NoteModel noteModel;
	
	public enum TransportState
	{
		PLAY, PAUSE, STOP
	};
	
	private float tick;
	private float elapsed;
	
	private TransportState currentTransportState;
	private int bpm;
	
	private AppProperties appProperties;
	
	/**
	 * Construct service.
	 * 
	 * Register for note ticks.
	 * 
	 */
	@Inject
	public NoteServiceImpl(NoteModel noteModel)
	{
		this.noteModel = noteModel;
		
		director = AppInjector.getInjector().getInstance(Director.class);
	
		appProperties = AppInjector.getInjector().getInstance(AppProperties.class);
		
		setBpm(appProperties.getBpm());
		
		this.currentTransportState = TransportState.PAUSE;
	}
	
	/**
	 * Update service state.
	 * 
	 */
	@Override
	public void update()
	{
		// Only update if playing activated.
		if (currentTransportState == TransportState.PLAY)
		{
			float delta = Gdx.graphics.getDeltaTime();
			
			elapsed += delta;
			
			if (elapsed >= tick)
			{
				elapsed = 0;
				
				noteModel.play();
				
				director.sendEvent(AppEvents.EVENT_TICK, null);
			}
		}
	}
	
	/**
	 * Return transport state.
	 * 
	 * @return The state.
	 */
	@Override
	public TransportState getCurrentTransportState()
	{
		return currentTransportState;
	}
	
	/**
	 * Set Transport state.
	 * 
	 * @param currentTransportState
	 */
	@Override
	public void setCurrentTransportState(TransportState transportState)
	{
		// Note that changing the state usually means resetting the elapsed
		// time. We want to resume from the start of a bar not somewhere in the
		// middle.
		switch (transportState)
		{
			case STOP:
				noteModel.reset();
				elapsed = 0;
				break;
			case PAUSE:
				elapsed = 0;
				break;
			case PLAY:
				elapsed = 0;
				setBpm(appProperties.getBpm());
				break;
			default:
				break;
		}
		
		this.currentTransportState = transportState;
	}
	
	/**
	 * Return beats per minute.
	 * 
	 * @return The bpm.
	 */
	public int getBpm()
	{
		return bpm;
	}
	
	/**
	 * Set beats per minute.
	 * 
	 * @param bpm
	 *            The bpm value.
	 */
	public void setBpm(int bpm)
	{
		this.bpm = bpm;
		
		tick = getBpmTick();
	}
	
	/**
	 * Return BPM tick secs.
	 * 
	 * @param bpm
	 * 
	 * @return The tick.
	 */
	public float getBpmTick()
	{
		float elapsedBpm = (60.0f / bpm);
		
		return elapsedBpm;
	}
	
}
