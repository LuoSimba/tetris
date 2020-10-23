package game.sound;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
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
	private MidiChannel ch2;
	
	private Sequence seq;
	
	/**
	 * 一个音序器占用一个线程
	 * 
	 * 如果创建成千个音序器同时播放，虚拟机就会吃不消
	 */
	private Sequencer seqr;
	
	public void dispose()
	{
		if (seqr.isRunning())
		{
			System.out.println("stop pianist.seqr ...");
			seqr.stop();
		}
		
		if (seqr.isOpen())
		{
			System.out.println("close pianist.seqr...");
			seqr.close();
		}
	}
	
	public Pianist()
	{
		
		try {
			synth = MidiSystem.getSynthesizer();
			// open before get sound-bank
			synth.open();
			
			//Soundbank sb = synth.getDefaultSoundbank();
			//Instrument[] instruments = sb.getInstruments();
			MidiChannel[] channels = synth.getChannels();
			chPiano = channels[0];
			ch2     = channels[1];
			chPiano.controlChange(7, 100);
			// 为通道选择一种乐器
			ch2.programChange(1);
			
			seqr = MidiSystem.getSequencer(false);
			seqr.open();
			seqr.getTransmitter().setReceiver(synth.getReceiver());
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		try {
			seq = new Text();
		} catch (InvalidMidiDataException e) {
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
	
	/**
	 * 弹奏叮的一声
	 */
	public void ding1()
	{
		int C = 84;
		
		ch2.noteOn(C, 100);
		ch2.noteOff(C, 100);
	}
	
	public void ding2()
	{
		int D = 86;
		
		ch2.noteOn(D, 100);
		ch2.noteOff(D, 100);
	}
	
	public void ding3()
	{
		int E = 88;
		
		ch2.noteOn(E, 100);
		ch2.noteOff(E, 100);
	}
	
	public void ding4()
	{
		int G = 91;
		
		ch2.noteOn(G, 100);
		ch2.noteOff(G, 100);
	}
	
	synchronized private void playMusic(Sequence seq)
	{
		if (!seqr.isOpen())
		{
			System.out.println("pianist.seqr is closed! cant play music");
			return;
		}
		
		try {
			
			seqr.setSequence(seq);
			seqr.setMicrosecondPosition(0);
			seqr.start();
			
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void playMusic()
	{
		playMusic(seq);
	}
}
