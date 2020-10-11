package game.sound;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

abstract class Song extends Sequence {
	
	private int scale;
	private int currentStrength;
	
	// 存放演奏效果
	// 封装音符 msg 的一次演奏效果
	private Track track1;
	private Track track2;
	private Track currentTrack;

	public Song() throws InvalidMidiDataException
	{
		super(Sequence.PPQ, 4);
		
		track1 = this.createTrack();
		track2 = this.createTrack();
		switchTrack(1);
		
		ShortMessage msg = new ShortMessage();
		msg.setMessage(ShortMessage.CONTROL_CHANGE, 0, 7, 60);
		track1.add(new MidiEvent(msg, 0));
	}
	
	protected void switchTrack(int trackId)
	{
		if (trackId == 1)
		{
			currentTrack = track1;
			scale = 0;
			currentStrength = 80;
		}
		else
		{
			currentTrack = track2;
			scale = 0;
			currentStrength = 40;
		}
	}
	
	protected void addNote(int noteHeight, int interval)
	{
		// prepare message
		ShortMessage msg1 = new ShortMessage();
		ShortMessage msg2 = new ShortMessage();
		
		try {
			msg1.setMessage(ShortMessage.NOTE_ON, 0, noteHeight, currentStrength);
			msg2.setMessage(ShortMessage.NOTE_OFF, 0, noteHeight, currentStrength);
			
			int noteTimevalueScale = scale;
			scale += interval;
			
			// prepare event(message)
			MidiEvent event1 = new MidiEvent(msg1, noteTimevalueScale);
			MidiEvent event2 = new MidiEvent(msg2, scale);
			
			currentTrack.add(event1);
			currentTrack.add(event2);
			
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
