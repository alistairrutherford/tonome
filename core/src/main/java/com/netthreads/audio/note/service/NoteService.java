package com.netthreads.audio.note.service;

import com.google.inject.ImplementedBy;
import com.netthreads.audio.note.service.NoteServiceImpl.TransportState;

@ImplementedBy(NoteServiceImpl.class)
public interface NoteService
{
	/**
	 * Update service.
	 * 
	 */
	public void update();
	
	/**
	 * Get state of 'transport' control.
	 *  
	 * @return The current state.
	 */
	public TransportState getCurrentTransportState();
	
	/**
	 * Set the state of the 'transport' control.
	 * 
	 * @param transportState
	 */
	public void setCurrentTransportState(TransportState transportState);
}
