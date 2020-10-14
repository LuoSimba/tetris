package game.sound;

import javax.sound.midi.InvalidMidiDataException;

public class Text extends Song {
	
	@Override
	public int getTempo()
	{
		return 90;
	}
	
	public Text() throws InvalidMidiDataException
	{
		switchTrack(1);
		
		int E = 64;
		int A = 69;
		
		addNote(E, 1);
		addNote(A, 8);
	}
}
