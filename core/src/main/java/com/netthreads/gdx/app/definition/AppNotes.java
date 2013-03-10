package com.netthreads.gdx.app.definition;

import java.util.HashMap;
import java.util.Map;

/**
 * Note definitions.
 * 
 */
@SuppressWarnings("serial")
public class AppNotes
{
	/**
	 * Heres what we are going to use which matches the tonematrix note spread.
	 */
	public static final String NOTE_0 = "A3";
	public static final String NOTE_1 = "B3";
	public static final String NOTE_2 = "D3";
	public static final String NOTE_3 = "E3";
	public static final String NOTE_4 = "G3";
	public static final String NOTE_5 = "A4";
	public static final String NOTE_6 = "B4";
	public static final String NOTE_7 = "D4";
	
	public static final String[] APP_NOTES =
	{
	        NOTE_0, NOTE_1, NOTE_2, NOTE_3, NOTE_4, NOTE_5, NOTE_6, NOTE_7
	};
	
	/**
	 * All MIDI note names.
	 */
	public static final String[] NOTE_NAMES =
	{
	        "C-1", "C0", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C#-1", "C#0", "C#1", "C#2", "C#3", "C#4", "C#5", "C#6", "C#7", "C#89", "C#91", "D-1 ", "D0", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D#-1", "D#0", "D#1", "D#2", "D#3", "D#4", "D#5", "D#6", "D#7", "D#8", "D#9", "E-1 ", "E0", "E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9", "F-1 ", "F0", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F#-1", "F#0", "F#1", "F#2", "F#3", "F#4", "F#5", "F#6", "F#7", "F#8",
	        "F#9", "G-1 ", "G0", "G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8", "G9", "G#-1", "G#0", "G#1", "G#2", "G#3", "G#4", "G#5", "G#6", "G#7", "G#8", "A-1 ", "A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A#-1", "A#0", "A#1", "A#2", "A#3", "A#4", "A#5", "A#6", "A#7", "A#8", "B-1 ", "B0", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8"
	};
	
	/**
	 * All MIDI note values.
	 */
	public static final int[] NOTE_VALUES =
	{
	        0, 12, 24, 36, 48, 60, 72, 84, 96, 108, 120, 1, 13, 25, 37, 49, 61, 73, 85, 97, 109, 121, 2, 14, 26, 38, 50, 62, 74, 86, 98, 110, 122, 3, 15, 27, 39, 51, 63, 75, 87, 99, 111, 123, 4, 16, 28, 40, 52, 64, 76, 88, 100, 112, 124, 5, 17, 29, 41, 53, 65, 77, 89, 101, 113, 125, 6, 18, 30, 42, 54, 66, 78, 90, 102, 114, 126, 7, 19, 31, 43, 55, 67, 79, 91, 103, 115, 127, 8, 20, 32, 44, 56, 68, 80, 92, 104, 116, 9, 21, 33, 45, 57, 69, 81, 93, 105, 117, 10, 22, 34, 46, 58, 70, 82, 94, 106, 118, 11, 23, 35,
	        47, 59, 71, 83, 95, 107, 119
	};
	
	/**
	 * Define sounds for each panel.
	 */
	public static final Map<String, Integer> NOTES = new HashMap<String, Integer>()
	{
		{
			intialise(this, NOTE_NAMES, NOTE_VALUES);
		}
	};
	
	/**
	 * Load all note values.
	 * 
	 * @param noteNames
	 * @param noteValues
	 */
	protected static void intialise(Map<String, Integer> map, String[] noteNames, int[] noteValues)
	{
		for (int index = 0; index < NOTE_NAMES.length; index++)
		{
			map.put(NOTE_NAMES[index], NOTE_VALUES[index]);
		}
	}
}
