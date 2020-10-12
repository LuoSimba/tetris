package game.sound.res;

import game.sound.Song;

import java.io.IOException;
import java.net.URL;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

/**
 * ����
 */
public class Test {

	/**
	 * ����Դ�ļ�������������
	 */
	private static Sequence loadSequence(String resName) throws InvalidMidiDataException, IOException
	{
		URL url = Test.class.getResource(resName);
		
		System.out.println(url);
		
		Sequence seq = null;
		
		seq = MidiSystem.getSequence(url);
		
		System.out.println(seq);
		
		return seq;
	}
	
	/**
	 * ��������
	 */
	private static void playSound(Sequence seq) throws MidiUnavailableException, InvalidMidiDataException
	{
		Sequencer player = MidiSystem.getSequencer();
		player.open();
		
		player.setSequence(seq);
		if (seq instanceof Song)
		{
			player.setTempoInBPM(((Song)seq).getTempo());
		}
		
		player.start();
	}
	
	public static void main(String[] args) throws InvalidMidiDataException, IOException, MidiUnavailableException
	{
		Sequence seq = loadSequence("min-g.mid");
		
		//seq = new game.sound.BanDal();
		
		playSound(seq);
	}
}
