package game.sound;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class RealPlayer {

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
			
			player.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			
			//seqr.addMetaEventListener(new MetaProc(seqr));
			//Receiver recv = seqr.getReceiver();
			
			player.open();
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadMusic(Sequence seq)
	{
		try {
			player.setSequence(seq);
			//player.setTrackMute(0, true);
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void play()
	{
		// 每分钟 90 拍
		// 这里调整歌曲的播放速度
		player.setTempoInBPM(90);
		player.start();
		
		// player.isRunning();
	}
}
