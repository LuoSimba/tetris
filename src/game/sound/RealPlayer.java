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
 * 背景音乐播放器
 * 
 * 只有一个播放器（如果有两个播放器
 * 一同播放，会非常嘈杂）
 */
public class RealPlayer implements MetaEventListener, ControllerEventListener {
	
	private static RealPlayer inst;
	
	/**
	 * 打开播放器
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
	 * 关闭背景音乐播放器，释放资源
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
		
		// 不允许修改音量
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
	 * 放置歌曲列表
	 */
	private ArrayList<Sequence> list;
	
	private Sequence musicGameOver;

	/**
	 * 定序器（音序器）
	 * 
	 * 负责播放音符序列
	 */
	private Sequencer player;
	
	private Synthesizer synth;
	
	private void shutdown()
	{
		// 关闭音序器
		if (player.isRunning())
			player.stop();
		
		if (player.isOpen())
			player.close();
		
		// 关闭合成器
		if (synth.isOpen())
			synth.close();
	}
	
	private RealPlayer() throws MidiUnavailableException, InvalidMidiDataException, IOException
	{
		// 1. 合成器
		synth = MidiSystem.getSynthesizer();
		synth.open();
		
		// 2. 音序器
		player = MidiSystem.getSequencer(false);
		player.addMetaEventListener(this);
		int[] ctls = { 7 };
		player.addControllerEventListener(this, ctls);
		player.open();
		//player.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
		
		// 3. 连接音序器与合成器
		player.getTransmitter().setReceiver(synth.getReceiver());
		
		// 4. 载入歌曲列表
		list = new ArrayList<Sequence>();
		list.add(getSequence("LetUsSwayTwinOars"));
		list.add(new BanDal());
		list.add(new LittleStar());
		list.add(new Yimeng());
		// 结束音乐
		musicGameOver = getSequence("min-g");
		
		
		// 5. 默认加载第一首
		loadMusic(list.get(0));
	}
	
	/**
	 * 从文件中加载乐曲
	 * @throws IOException 
	 * @throws InvalidMidiDataException 
	 */
	private Sequence getSequence(String resName) throws InvalidMidiDataException, IOException
	{
		URL url = this.getClass().getResource("res/" + resName + ".mid");
		
		return MidiSystem.getSequence(url);
	}
	
	/**
	 * 设置音序器播放的歌曲
	 * 
	 * 只能在音序器打开的情况下操作
	 */
	private void loadMusic(Sequence song)
	{
		// 如果音序器已经关闭，什么也不用做
		if (!player.isOpen())
			return;
		
		try {
			player.setSequence(song);
			player.setMicrosecondPosition(0);

			// 每分钟 90 拍
			// 这里调整歌曲的播放速度
			if (song instanceof Song)
			{
				player.setTempoInBPM(((Song)song).getTempo());
			}
			
			// 总是将音量设置为固定值
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
	 * 游戏结束
	 */
	private void playGameOver()
	{
		loadMusic(musicGameOver);
		play();
	}
	
	/**
	 * 开始播放
	 */
	private void play()
	{
		player.start();
	}
}
