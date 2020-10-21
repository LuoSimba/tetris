package game.sound;

import game.model.MusicEvent;
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
	
	/**
	 * �򿪲�����
	 */
	synchronized public static void open()
	{
		try {
			if (inst == null) {
				inst = new RealPlayer();
				
				inst.play();
			}
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	synchronized public static void execute(MusicEvent e)
	{
		if (inst == null)
			return;
		
		if (e == MusicEvent.GAME_OVER)
			inst.playGameOver();
	}
	
	/**
	 * �رձ������ֲ��������ͷ���Դ
	 */
	synchronized public static void close()
	{
		if (inst != null)
		{
			inst.shutdown();
			inst = null;
		}
	}
	
	// ============================================
	
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
	
	
	// ===========================================
	
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
	
	private void shutdown()
	{
		// �ر�������
		if (player.isRunning())
			player.stop();
		
		if (player.isOpen())
			player.close();
		
		// �رպϳ���
		if (synth.isOpen())
			synth.close();
	}
	
	private RealPlayer() throws MidiUnavailableException, InvalidMidiDataException, IOException
	{
		// 1. �ϳ���
		synth = MidiSystem.getSynthesizer();
		synth.open();
		
		// 2. ������
		player = MidiSystem.getSequencer(false);
		player.addMetaEventListener(this);
		int[] ctls = { 7 };
		player.addControllerEventListener(this, ctls);
		player.open();
		//player.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
		
		// 3. ������������ϳ���
		player.getTransmitter().setReceiver(synth.getReceiver());
		
		// 4. ��������б�
		list = new ArrayList<Sequence>();
		list.add(getSequence("LetUsSwayTwinOars"));
		list.add(new BanDal());
		list.add(new LittleStar());
		list.add(new Yimeng());
		// ��������
		musicGameOver = getSequence("min-g");
		
		
		// 5. Ĭ�ϼ��ص�һ��
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
	private void loadMusic(Sequence song)
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
	private void playGameOver()
	{
		loadMusic(musicGameOver);
		play();
	}
	
	/**
	 * ��ʼ����
	 */
	private void play()
	{
		player.start();
	}
}
