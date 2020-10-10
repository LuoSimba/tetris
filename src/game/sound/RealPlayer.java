package game.sound;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class RealPlayer {

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
			
			player.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			
			//seqr.addMetaEventListener(new MetaProc(seqr));
			//Receiver recv = seqr.getReceiver();
			//seqr.setTrackMute(0, true);

			
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
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void play()
	{
		// ÿ���� 90 ��
		// ������������Ĳ����ٶ�
		player.setTempoInBPM(90);
		player.start();
		
		// player.isRunning();
	}
}
