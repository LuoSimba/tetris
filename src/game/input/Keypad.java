package game.input;

import game.App;
import game.model.Command;
import game.ui.Window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keypad implements KeyListener {
	
	private Window win;
	
	public Keypad(Window win)
	{
		this.win = win;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		App app = win.getApp();
		if (app == null)
			return;
		
		if (app.isGameOver())
			return;

		// ��Ϸʵ����������
		win.hideCursor();
		
		Command cmd = key2cmd(code);
		
		if (cmd != null)
		{
			win.putCommand(cmd);
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
