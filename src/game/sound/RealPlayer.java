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
	}
	
	
	/**
	 * 放置歌曲列表
	 */
	private ArrayList<Sequence> list;

	/**
	 * 定序器（音序器）
	 * 
	 * 负责播放音符序列
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
		
		// 初始化歌曲列表
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
		
		// 默认加载第一首
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
		
		Sequence seq = null;
		
		seq = MidiSystem.getSequence(url);
		
		return seq;
	}
	
	private void loadMusic(Sequence song)
	{
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
	
	public void play()
	{
		player.start();
		
		// player.isRunning();
	}
}
