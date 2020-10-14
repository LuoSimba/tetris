package game.sound;

import game.model.Util;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

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
	
	private class CtrlProc implements ControllerEventListener {

		@Override
		public void controlChange(ShortMessage event) {
			
			int cmd     = event.getCommand();
			int channel = event.getChannel();
			int data1   = event.getData1();
			int data2   = event.getData2();
			
			// �������޸�����
			if (cmd == ShortMessage.CONTROL_CHANGE && data1 == 7)
			{
				int volumn = ( data2 * 50 ) / 127;
				
				synth.getChannels()[channel].controlChange(7, volumn);
			}
			else 
			{
				System.out.println("cmd: " + cmd);
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
	
	private Synthesizer synth;
	
	public RealPlayer()
	{
		
		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
			
			player = MidiSystem.getSequencer(false);
			player.addMetaEventListener(new MetaProc());
			int[] ctls = { 7 };
			player.addControllerEventListener(new CtrlProc(), ctls);
			player.open();
			// player.close();
			//player.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			
			// connect(player->synth)
			player.getTransmitter().setReceiver(synth.getReceiver());
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// ��ʼ�������б�
		list = new ArrayList<Sequence>();
		
		try {
			list.add(getSequence("min-g"));
			list.add(getSequence("LetUsSwayTwinOars"));
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
			
			// ���ǽ���������Ϊ�̶�ֵ
			setVolumn(50);
			
			//player.setTrackMute(0, true);
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setVolumn(int val)
	{
		MidiChannel[] list = synth.getChannels();
		
		for (MidiChannel ch : list)
		{
			//ch.setMute(true);
			ch.controlChange(7, val);
		}
	}
	
	public void play()
	{
		player.start();
		
		// player.isRunning();
	}
}
