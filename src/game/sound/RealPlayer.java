package game.sound;

import game.model.Util;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class RealPlayer {
	
	
	private class MetaProc implements MetaEventListener {
		
		@Override
		public void meta(MetaMessage meta) {
			
			int type = meta.getType();
			
			if (type == 47)
			{
				//player.close();
				
				int i = Util.random(list.size());
				loadMusic(list.get(i));
				play();
			}
			else 
			{
				System.out.println("meta type " + type);
			}
		}
	}
	
	
	/**
	 * ���ø����б�
	 */
	private ArrayList<Sequence> list;

	/**
	 * ����������������
	 * 
	 * ���𲥷���������
	 */
	private Sequencer player;
	
	public RealPlayer()
	{
		try {
			player = MidiSystem.getSequencer();
			player.addMetaEventListener(new MetaProc());
			//player.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			
			player.open();
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// ��ʼ�������б�
		list = new ArrayList<Sequence>();
		
		try {
			list.add(getSequence("Fur-Elise"));
			list.add(getSequence("LetUsSwayTwinOars"));
			list.add(getSequence("min-g"));
			list.add(new BanDal());
			list.add(new LittleStar());
			list.add(new Yimeng());
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Ĭ�ϼ��ص�һ��
		loadMusic(list.get(0));
	}
	
	/**
	 * ���ļ��м�������
	 * @throws IOException 
	 * @throws InvalidMidiDataException 
	 */
	private Sequence getSequence(String resName) throws InvalidMidiDataException, IOException
	{
		URL url = this.getClass().getResource("res/" + resName + ".mid");
		
		Sequence seq = null;
		
		seq = MidiSystem.getSequence(url);
		
		return seq;
	}
	
	private void loadMusic(Sequence song)
	{
		try {
			player.setSequence(song);
			player.setMicrosecondPosition(0);
			// ÿ���� 90 ��
			// ������������Ĳ����ٶ�
			if (song instanceof Song)
			{
				player.setTempoInBPM(((Song)song).getTempo());
			}
			
			//player.setTrackMute(0, true);
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void play()
	{
		player.start();
		
		// player.isRunning();
	}
}
