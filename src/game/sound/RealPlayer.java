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

/**
 * �������ֲ�����
 * 
 * ֻ��һ�������������������������
 * һͬ���ţ���ǳ����ӣ�
 */
public class RealPlayer implements MetaEventListener, ControllerEventListener {
	
	private static RealPlayer inst;
	
	public static RealPlayer getInstance() {
		
		if (inst == null)
			inst = new RealPlayer();
		
		return inst;
	}
	
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
	
	
	/**
	 * ���ø����б�
	 */
	private ArrayList<Sequence> list;
	
	private Sequence musicGameOver;

	/**
	 * ����������������
	 * 
	 * ���𲥷���������
	 */
	private Sequencer player;
	
	private Synthesizer synth;
	
	/**
	 * �رձ������ֲ��������ͷ���Դ
	 */
	synchronized public void shutdown()
	{
		// �ر�������
		if (player.isRunning())
			player.stop();
		
		if (player.isOpen())
			player.close();
	}
	
	private RealPlayer()
	{
		
		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
			
			player = MidiSystem.getSequencer(false);
			player.addMetaEventListener(this);
			int[] ctls = { 7 };
			player.addControllerEventListener(this, ctls);
			player.open();
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
			list.add(getSequence("LetUsSwayTwinOars"));
			list.add(new BanDal());
			list.add(new LittleStar());
			list.add(new Yimeng());
			
			// ��������
			musicGameOver = getSequence("min-g");
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
		
		return MidiSystem.getSequence(url);
	}
	
	/**
	 * �������������ŵĸ���
	 * 
	 * ֻ�����������򿪵�����²���
	 */
	synchronized private void loadMusic(Sequence song)
	{
		// ����������Ѿ��رգ�ʲôҲ������
		if (!player.isOpen())
			return;
		
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
	
	/**
	 * ��Ϸ����
	 */
	synchronized public void playGameOver()
	{
		loadMusic(musicGameOver);
		play();
	}
	
	/**
	 * ��ʼ����
	 */
	synchronized public void play()
	{
		// ����������Ѿ����ر�(close)������Ӧ�������
		// Ҳ���ᱨ��
		if (player.isOpen())
		{
			player.start();
		}
	}
}
