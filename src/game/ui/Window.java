package game.ui;


import game.model.Command;
import game.model.TaskService;
import game.sound.RealPlayer;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * 只能创建一个窗口实例
 * 
 * 窗口自己管理控件
 * 
 * ==========================================================
 * 
 * 以下事件的监听全部由 AWT-EventQueue-0 线程来执行：
 * 
 * 窗口负责接收输入
 *  Keypad
 * 
 * 窗口负责处理鼠标移动事件
 *  MouseMotionListener.mouseMoved(MouseEvent);
 * 
 * 窗口自定义关闭事件
 *  WindowListener.windowClosed(WindowEvent e);
 *  
 * ==========================================================
 * 
 * 窗口控制背景音乐的开关
 */
public class Window extends JFrame 
implements MouseMotionListener, WindowListener {
	
	/**
	 * 处理按键
	 */
	private class Keypad implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			
			// 发生按键时隐藏鼠标
			Window.this.hideCursor();
			
			int code = e.getKeyCode();
			
			switch (code)
			{
			// player A
			case KeyEvent.VK_RIGHT:
				view1.putCommand(Command.RIGHT);
				break;
			case KeyEvent.VK_LEFT:
				view1.putCommand(Command.LEFT);
				break;
			case KeyEvent.VK_UP:
				view1.putCommand(Command.ROTATE);
				break;
			case KeyEvent.VK_DOWN:
				view1.putCommand(Command.DOWN);
				break;
			case KeyEvent.VK_SPACE:
				view1.putCommand(Command.NEXT_SHAPE); // for debug only
				break;
			case KeyEvent.VK_P:
				view1.putCommand(Command.PAUSE);
				break;
				
			default:
				break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}
	}
	
	
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 存放窗口实例
	 */
	private static Window inst = null;
	
	/**
	 * 创建窗口的统一方式
	 */
	public static void createWindow()
	{
		if (inst == null)
		{
			inst = new Window();
		}
	}
	
	private boolean isCursorShow;
	/**
	 * 窗口上的菜单栏
	 */
	final private MenuBar menu;
	final private JPanel content;
	private GameView view1;
	
	private Window()
	{
		this.setTitle("俄罗斯方块");
		// WindowConstants.EXIT_ON_CLOSE;
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		
		this.setSize(200, 200);
		//this.setBounds(500, 50, 200, 200);
		
		this.enableInputMethods(false);
		
		isCursorShow = true;
		
		// set up the menu bar.
		menu = new MenuBar(this);
		this.setJMenuBar(menu);
		
		content = (JPanel) this.getContentPane();
		content.setLayout(new BoxLayout(content, BoxLayout.X_AXIS));
		
		view1 = new GameView("游戏");
		
		menu.add(view1.getMenu());
		
		this.add(view1);
		this.pack();
		// 使窗体居中
		this.setLocationRelativeTo(null);
		
		this.addKeyListener(new Keypad());
		this.addMouseMotionListener(this);
		this.addWindowListener(this);
		this.setVisible(true);
	}
	
	
	private void hideCursor()
	{
		if (isCursorShow)
		{
			this.setCursor(CursorUtil.getTransparentCursor());
			isCursorShow = false;
		}
	}
	
	private void showCursor()
	{
		if (!isCursorShow)
		{
			this.setCursor(Cursor.getDefaultCursor());
			isCursorShow = true;
		}
	}
	
	/**
	 * MouseMotionListener method
	 */
	@Override 
	public void mouseDragged(MouseEvent e)
	{
		// TODO Auto-generated method stub
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		showCursor();
	}

	// --------------- WindowListener ----------------
	@Override
	public void windowActivated(WindowEvent e) {
		// do nothing
	}

	@Override
	public void windowClosed(WindowEvent e) {
	
		// 记得关闭背景音乐
		RealPlayer.close();
		
		// 和定时器服务
		TaskService.close();
		
		System.exit(0);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		
		// 关闭窗口时，必须结束游戏实例
		view1.killGame();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// do nothing
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// do nothing
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// do nothing
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// do nothing
	}
}
