package game.input;

import game.App;
import game.model.Command;
import game.model.EventQueue;
import game.ui.Window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keypad implements KeyListener {
	
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		Window win = Window.getInstance();

		App app = win.getApp();
		if (app == null)
			return;
		
		if (app.isGameOver())
			return;

		// ��Ϸʵ����������
		win.hideCursor();
		EventQueue queue = app.getQueue();
		
		Command cmd = key2cmd(code);
		
		if (cmd != null)
		{
			// put ������һֱ�ȴ�
			//queue.put(cmd);
			//queue.add(cmd);
			
			queue.offer(cmd);
		}
	}
	
	/**
	 * ������ת��������
	 * 
	 * ���û�ж�Ӧ������򷵻� null
	 */
	private Command key2cmd(int keyCode)
	{
		switch (keyCode)
		{
		case KeyEvent.VK_RIGHT:
			return Command.RIGHT;
		case KeyEvent.VK_LEFT:
			return Command.LEFT;
		case KeyEvent.VK_UP:
			return Command.ROTATE;
		case KeyEvent.VK_DOWN:
			return Command.DOWN;
		case KeyEvent.VK_SPACE:
			return Command.NEXT_SHAPE;
		case KeyEvent.VK_P:
			return Command.PAUSE;
		case KeyEvent.VK_Q:
			return Command.RESUME;
		case KeyEvent.VK_F1:
			return Command.TEST;
		default:
			return null;
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
