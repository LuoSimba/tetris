package game.sound;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/**
 * ±³¾°ÒôÀÖ Ð¡ÐÇÐÇ
 */
public class LittleStar extends Sequence {
	
	private Track track1;
	private Track track2;
	private Track currentTrack;
	private int scale;
	private int currentStrength;
	
	private void rightHand(int base)
	{
		int C = base;
		int D = base + 2;
		int E = base + 4;
		int F = base + 5;
		int G = base + 7;
		int A = base + 9;

		addNote(C, 4);
		addNote(C, 4);
		addNote(G, 4);
		addNote(G, 4);
		addNote(A, 4);
		addNote(A, 4);
		addNote(G, 8);
		
		addNote(F, 4);
		addNote(F, 4);
		addNote(E, 4);
		addNote(E, 4);
		addNote(D, 4);
		addNote(D, 4);
		addNote(C, 8);
		
		addNote(G, 4);
		addNote(G, 4);
		addNote(F, 4);
		addNote(F, 4);
		addNote(E, 4);
		addNote(E, 4);
		addNote(D, 8);
		
		addNote(G, 4);
		addNote(G, 4);
		addNote(F, 4);
		addNote(F, 4);
		addNote(E, 4);
		addNote(E, 4);
		addNote(D, 8);
		
		addNote(C, 4);
		addNote(C, 4);
		addNote(G, 4);
		addNote(G, 4);
		addNote(A, 4);
		addNote(A, 4);
		addNote(G, 8);
		
		addNote(F, 4);
		addNote(F, 4);
		addNote(E, 4);
		addNote(E, 4);
		addNote(D, 4);
		addNote(D, 4);
		addNote(C, 8);
	}

	/**
	 * ×óÊÖµ¯×à¸´µ÷
	 */
	private void leftHandPolyphonic(int base) 
	{
		int cc = base - 12;
		int ff = base - 12 + 5;
		int gg = base - 12 + 7;
		int aa = base - 12 + 9;
		int bb = base - 12 + 11;
		int c = base;
		int d = base + 2;
		int e = base + 4;
		int f = base + 5;
		
		addNote(cc, 4);
		addNote(c, 4);
		addNote(e, 4);
		addNote(c, 4);
		addNote(f, 4);
		addNote(c, 4);
		addNote(e, 4);
		addNote(c, 4);

		addNote(d, 4);
		addNote(bb, 4);
		addNote(c, 4);
		addNote(aa, 4);
		addNote(ff, 4);
		addNote(gg, 4);
		addNote(cc, 8);

		addNote(e, 4);
		addNote(gg, 4);
		addNote(d, 4);
		addNote(gg, 4);
		addNote(c, 4);
		addNote(gg, 4);
		addNote(bb, 4);
		addNote(gg, 4);

		addNote(e, 4);
		addNote(gg, 4);
		addNote(d, 4);
		addNote(gg, 4);
		addNote(c, 4);
		addNote(gg, 4);
		addNote(bb, 4);
		addNote(gg, 4);

		addNote(cc, 4);
		addNote(c, 4);
		addNote(e, 4);
		addNote(c, 4);
		addNote(f, 4);
		addNote(c, 4);
		addNote(e, 4);
		addNote(c, 4);

		addNote(d, 4);
		addNote(bb, 4);
		addNote(c, 4);
		addNote(aa, 4);
		addNote(ff, 4);
		addNote(gg, 4);
		addNote(cc, 8);

	}
	
	/**
	 * ×óÊÖµ¯×àºÍÏÒ
	 */
	private void leftHandChord(int base)
	{
		int bb = base - 1;
		int c = base;
		int d = base + 2;
		int e = base + 4;
		int f = base + 5;
		int g = base + 7;
		int a = base + 9;
		
		addNote(c, 2);
		addNote(g, 2);
		addNote(e, 2);
		addNote(g, 2);
		addNote(c, 2);
		addNote(g, 2);
		addNote(e, 2);
		addNote(g, 2);
		addNote(c, 2);
		addNote(a, 2);
		addNote(f, 2);
		addNote(a, 2);
		addNote(c, 2);
		addNote(g, 2);
		addNote(e, 2);
		addNote(g, 2);

		addNote(bb, 2);
		addNote(g, 2);
		addNote(d, 2);
		addNote(g, 2);
		addNote(c, 2);
		addNote(g, 2);
		addNote(e, 2);
		addNote(g, 2);
		addNote(bb, 2);
		addNote(g, 2);
		addNote(d, 2);
		addNote(g, 2);
		addNote(c, 2);
		addNote(g, 2);
		addNote(e, 2);
		addNote(g, 2);
		
		addNote(c, 2);
		addNote(g, 2);
		addNote(e, 2);
		addNote(g, 2);
		addNote(bb, 2);
		addNote(g, 2);
		addNote(d, 2);
		addNote(g, 2);
		addNote(c, 2);
		addNote(g, 2);
		addNote(e, 2);
		addNote(g, 2);
		addNote(bb, 2);
		addNote(g, 2);
		addNote(d, 2);
		addNote(g, 2);

		addNote(c, 2);
		addNote(g, 2);
		addNote(e, 2);
		addNote(g, 2);
		addNote(bb, 2);
		addNote(g, 2);
		addNote(d, 2);
		addNote(g, 2);
		addNote(c, 2);
		addNote(g, 2);
		addNote(e, 2);
		addNote(g, 2);
		addNote(bb, 2);
		addNote(g, 2);
		addNote(d, 2);
		addNote(g, 2);

		addNote(c, 2);
		addNote(g, 2);
		addNote(e, 2);
		addNote(g, 2);
		addNote(c, 2);
		addNote(g, 2);
		addNote(e, 2);
		addNote(g, 2);
		addNote(c, 2);
		addNote(a, 2);
		addNote(f, 2);
		addNote(a, 2);
		addNote(c, 2);
		addNote(g, 2);
		addNote(e, 2);
		addNote(g, 2);

		addNote(bb, 2);
		addNote(g, 2);
		addNote(d, 2);
		addNote(g, 2);
		addNote(c, 2);
		addNote(g, 2);
		addNote(e, 2);
		addNote(g, 2);
		addNote(bb, 2);
		addNote(g, 2);
		addNote(d, 2);
		addNote(g, 2);
		addNote(c, 2);
		addNote(g, 2);
		addNote(e, 2);
		addNote(g, 2);

	}
	
	public LittleStar() throws InvalidMidiDataException
	{
		super(Sequence.PPQ, 4);
			
		track1 = this.createTrack();
		track2 = this.createTrack();
			
		ShortMessage msg = new ShortMessage();
		msg.setMessage(ShortMessage.CONTROL_CHANGE, 0, 7, 60);
		track1.add(new MidiEvent(msg, 0));
			
			
		currentTrack = track1;
		scale = 0;
		currentStrength = 80;
			
		// note height
		rightHand(60);
		rightHand(72);
		rightHand(60);
		rightHand(72);

		
		currentTrack = track2;
		scale = 0;
		currentStrength = 40;
		
		leftHandPolyphonic(48);
		leftHandPolyphonic(60);
		
		leftHandChord(48);
		leftHandChord(60);
	}
	
	private void addNote(int noteHeight, int interval)
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
