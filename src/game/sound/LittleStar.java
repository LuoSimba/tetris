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
	
	private void rightHand(int C, int D, int E, int F, int G, int A)
	{
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
	private void leftHandPolyphonic(
			int cc, int ff, int gg, int aa, int bb, 
			int c, int d, int e, int f) 
	{
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
		int C,D,E,F,G,A;
		C = 60;
		D = 62;
		E = 64;
		F = 65;
		G = 67;
		A = 69;
		rightHand(C, D, E, F, G, A);
		C = 72;
		D = 74;
		E = 76;
		F = 77;
		G = 79;
		A = 81;
		rightHand(C, D, E, F, G, A);

		C = 60;
		D = 62;
		E = 64;
		F = 65;
		G = 67;
		A = 69;
		rightHand(C, D, E, F, G, A);
		C = 72;
		D = 74;
		E = 76;
		F = 77;
		G = 79;
		A = 81;
		rightHand(C, D, E, F, G, A);

		
		currentTrack = track2;
		scale = 0;
		currentStrength = 40;
		

		
		int cc,ff,gg,aa,bb,c,d,e,f;
		cc = 36;
		ff = 41;
		gg = 43;
		aa = 45;
		bb = 47;
		c = 48;
		d = 50;
		e = 52;
		f = 53;
		leftHandPolyphonic(cc, ff, gg, aa, bb, c, d, e, f);
		cc += 12;
		ff += 12;
		gg += 12;
		aa += 12;
		bb += 12;
		c  += 12;
		d  += 12;
		e  += 12;
		f  += 12;
		leftHandPolyphonic(cc, ff, gg, aa, bb, c, d, e, f);
		
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
