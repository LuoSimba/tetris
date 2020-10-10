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
 * 背景音乐 小星星
 */
public class LittleStar {
	
	private Sequence seq;
	private Track track1;
	private Track track2;
	private Track currentTrack;
	private int scale;
	/**
	 * 定序器（音序器）
	 * 
	 * 负责播放音符序列
	 */
	private Sequencer player;
	private int currentStrength;
	
	public LittleStar()
	{
		try {
			seq = new Sequence(Sequence.PPQ, 4);
			
			track1 = seq.createTrack();
			track2 = seq.createTrack();
			
			ShortMessage msg = new ShortMessage();
			msg.setMessage(ShortMessage.CONTROL_CHANGE, 0, 7, 60);
			track1.add(new MidiEvent(msg, 0));
			
			
			currentTrack = track1;
			scale = 0;
			currentStrength = 80;
			
			// note height
			int C = 60;
			int D = 62;
			int E = 64;
			int F = 65;
			int G = 67;
			int A = 69;
			
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
			
			C = 72;
			D = 74;
			E = 76;
			F = 77;
			G = 79;
			A = 81;
			
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

			
			currentTrack = track2;
			scale = 0;
			currentStrength = 40;
			
			int cc = 36;
			int ff = 41;
			int gg = 43;
			int aa = 45;
			int bb = 47;
			int c = 48;
			int d = 50;
			int e = 52;
			int f = 53;
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
			
			cc += 12;
			ff += 12;
			gg += 12;
			aa += 12;
			bb += 12;
			c  += 12;
			d  += 12;
			e  += 12;
			f  += 12;
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


		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			player = MidiSystem.getSequencer();
			
			player.setSequence(seq);
			player.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			
			
			//seqr.addMetaEventListener(new MetaProc(seqr));
			//Receiver recv = seqr.getReceiver();
			//seqr.setTrackMute(0, true);
			
			player.open();
			
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
		// 每分钟 90 拍
		// 这里调整歌曲的播放速度
		player.setTempoInBPM(90);
		player.start();
		
		// player.isRunning();
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
