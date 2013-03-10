package com.netthreads.audio.note.service;

import com.google.inject.ImplementedBy;
import com.netthreads.gdx.app.definition.NoteDefinition;

@ImplementedBy(NoteBuilderImpl.class)  
public interface NoteBuilder
{
	/**
	 * Begin building note.
	 * 
	 */
	public void begin();
	
	/**
	 * Add note on message.
	 * 
	 * @param noteDefinition
	 */
	public void noteOn(NoteDefinition noteDefinition);
	
	/**
	 * Add note off message.
	 * 
	 * @param noteDefinition
	 */
	public void noteOff(NoteDefinition noteDefinition);
	
	/**
	 * End build note.
	 * 
	 */
	public void end();
}
