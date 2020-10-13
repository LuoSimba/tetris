package game.sound;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class Pianist {
	
	/**
	 * 负责演奏音符的频道
	 * 
	 * 相当于一个乐器或几个乐器的声音
	 */
	private Synthesizer synth;
	
	private MidiChannel channel;
	
	public Pianist()
	{
		
		//Sequence seq = Text.getBasic();

		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
			
			MidiChannel[] channels = synth.getChannels();
			System.out.println(channels);
			channel = channels[0];
			
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void playRotate()
	{ 
		channel.noteOn(23, 50);
		channel.noteOff(23, 1);
	}
	
	public void playKnock()
	{
		channel.noteOn(19, 100);
		channel.noteOff(19, 1);
	}
	
	public void ding()
	{
		channel.noteOn(90, 100);
		channel.noteOff(90, 100);
	}
}
