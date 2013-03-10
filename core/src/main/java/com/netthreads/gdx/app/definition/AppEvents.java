package com.netthreads.gdx.app.definition;

/**
 * Define application events
 * 
 */
public class AppEvents
{
	private static final int EVENT_BASE = 0;
	
	public static final int EVENT_TRANSITION_TO_PANEL_SCENE = EVENT_BASE + 1;
	public static final int EVENT_TRANSITION_TO_MENU_SCENE = EVENT_BASE + 2;
	public static final int EVENT_TRANSITION_TO_SETTINGS_SCENE = EVENT_BASE + 3;
	public static final int EVENT_TRANSITION_TO_ABOUT_SCENE = EVENT_BASE + 4;
	public static final int EVENT_TRANSITION_TO_CONNECTING_SCENE = EVENT_BASE + 5;
		
	public static final int EVENT_PAD_TOUCH_DOWN = EVENT_BASE + 6;
	public static final int EVENT_PAD_TOUCH_DRAGGED = EVENT_BASE + 7;
	public static final int EVENT_PAD_TOUCH_UP = EVENT_BASE + 8;

	public static final int EVENT_NOTE_ON = EVENT_BASE + 9;
	public static final int EVENT_NOTE_OFF = EVENT_BASE + 10;
	
	public static final int EVENT_TICK = EVENT_BASE + 11;

	public static final int EVENT_CONNECT_OSC_START = EVENT_BASE + 12;
	public static final int EVENT_CONNECT_OSC_SUCCESS = EVENT_BASE + 13;
	public static final int EVENT_CONNECT_OSC_FAIL = EVENT_BASE + 14;

	public static final int EVENT_EXIT = EVENT_BASE + 99;
}
