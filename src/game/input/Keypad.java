package game.input;

import game.App;
import game.model.Command;
import game.model.EventQueue;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keypad implements KeyListener {
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
		App app = App.getInstance();
		EventQueue queue = app.getQueue();
		
		app.hideMouse();
		
		try {
			if (code == KeyEvent.VK_RIGHT)
				queue.put(Command.RIGHT);
			else if (code == KeyEvent.VK_LEFT)
				queue.put(Command.LEFT);
			else if (code == KeyEvent.VK_UP)
				queue.put(Command.ROTATE_CW);
			/*else if (code == KeyEvent.VK_F)
				queue.put(Command.ROTATE_ACW);*/
			else if (code == KeyEvent.VK_D)
				queue.put(Command.ROTATE_CW);
			else if (code == KeyEvent.VK_DOWN)
				queue.put(Command.DOWN);
			else if (code == KeyEvent.VK_SPACE)
				queue.put(Command.NEXT_SHAPE);
			else if (code == KeyEvent.VK_P)
				queue.put(Command.PAUSE);
			else if (code == KeyEvent.VK_Q)
				queue.put(Command.RESUME);
			else if (code == KeyEvent.VK_F1)
				queue.put(Command.TEST);
			else
				; // nothing
			
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// do nothing
	}
}
