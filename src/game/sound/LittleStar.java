package game.sound;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/**
 * ±≥æ∞“Ù¿÷ –°–«–«
 */
public class LittleStar {
	
	private Sequence seq;
	private Track track;
	private int scale = 0;
	private Sequencer seqr;
	
	public LittleStar()
	{
		try {
			seq = new Sequence(Sequence.PPQ, 4);
			
			track = seq.createTrack();
			
			// note height
			int C = 48;
			int D = 50;
			int E = 52;
			int F = 53;
			int G = 55;
			int A = 57;
			
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

		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			seqr = MidiSystem.getSequencer();
			
			seqr.setSequence(seq);
			seqr.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			//seqr.addMetaEventListener(new MetaProc(seqr));
			//Receiver recv = seqr.getReceiver();
			//seqr.setTrackMute(0, true);
			
			seqr.open();
			
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void play()
	{
		seqr.start();
	}
	
	private void addNote(int noteHeight, int interval)
	{
		// prepare message
		ShortMessage msg1 = new ShortMessage();
		ShortMessage msg2 = new ShortMessage();
		
		try {
			msg1.setMessage(ShortMessage.NOTE_ON, 0, noteHeight, 100);
			msg2.setMessage(ShortMessage.NOTE_OFF, 0, noteHeight, 100);
			
			int noteTimevalueScale = scale;
			scale += interval;
			
			// prepare event(message)
			MidiEvent event1 = new MidiEvent(msg1, noteTimevalueScale);
			MidiEvent event2 = new MidiEvent(msg2, scale);
			
			track.add(event1);
			track.add(event2);
			
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
