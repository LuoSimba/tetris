package game.sound;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

public class Pianist {
	
	/**
	 * 负责演奏音符的频道
	 * 
	 * 相当于一个乐器或几个乐器的声音
	 */
	private Synthesizer synth;
	
	private MidiChannel chPiano;
	
	public Pianist()
	{
		
		//Sequence seq = Text.getBasic();

		try {
			synth = MidiSystem.getSynthesizer();
			
			Soundbank sb = synth.getDefaultSoundbank();
			Instrument[] instruments = sb.getInstruments();
			synth.loadInstrument(instruments[0]);
			
			synth.open();
			
			MidiChannel[] channels = synth.getChannels();
			chPiano = channels[0];
			chPiano.controlChange(7, 100);
			
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void playRotate()
	{
		chPiano.noteOn(23, 50);
		chPiano.noteOff(23, 1);
	}
	
	public void playKnock()
	{
		chPiano.noteOn(19, 100);
		chPiano.noteOff(19, 1);
	}
	
	public void ding()
	{
		int C = 84;
		int E = 88;
		int G = 91;
		
		C += 8;
		E += 8;
		G += 8;
		chPiano.noteOn(C, 100);
		chPiano.noteOn(E, 100);
		chPiano.noteOn(G, 100);
		
		chPiano.noteOff(C, 100);
		chPiano.noteOff(E, 100);
		chPiano.noteOff(G, 100);
	}
}
