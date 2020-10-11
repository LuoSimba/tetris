package game;

import javax.sound.midi.InvalidMidiDataException;

import game.signal.GameOverSignal;
import game.sound.LittleStar;
import game.sound.RealPlayer;
import game.sound.Yimeng;
import game.ui.Window;

public class Boot {
	
	private static Window win;
	
	private static void init()
	{
		win = Window.getInstance();
		
		//Player player = Player.getPlayer();
		//player.ding();
		
		RealPlayer player = new RealPlayer();
		try {
			//player.loadMusic(new LittleStar());
			player.loadMusic(new Yimeng());
			player.play();
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		init();
		
		App app = new App(win);
		
		try {
			app.play();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GameOverSignal e) {
			// ”Œœ∑Ω· ¯
			// do nothing
		}
			
		//App.dispose();
	}
}

//Integer.toBinaryString(-1);

