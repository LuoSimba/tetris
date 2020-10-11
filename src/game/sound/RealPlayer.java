package game.sound;

import game.model.Util;

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
	 * 放置歌曲列表
	 */
	private ArrayList<Song> list;

	/**
	 * 定序器（音序器）
	 * 
	 * 负责播放音符序列
	 */
	private Sequencer player;
	
	public RealPlayer()
	{
		try {
			player = MidiSystem.getSequencer();
			player.addMetaEventListener(new MetaProc());
			//player.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			
			//Receiver recv = seqr.getReceiver();
			
			player.open();
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 初始化歌曲列表
		list = new ArrayList<Song>();
		
		try {
			list.add(new BanDal());
			list.add(new LittleStar());
			list.add(new Yimeng());
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 默认加载第一首
		loadMusic(list.get(0));
	}
	
	private void loadMusic(Song song)
	{
		try {
			player.setSequence(song);
			player.setMicrosecondPosition(0);
			// 每分钟 90 拍
			// 这里调整歌曲的播放速度
			player.setTempoInBPM(song.getTempo());
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
