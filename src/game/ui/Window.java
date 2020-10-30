package game.ui;


import game.App;
import game.model.Command;
import game.model.Task;
import game.sound.RealPlayer;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.MemoryImageSource;

import javax.swing.JFrame;
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
 * 窗口负责处理菜单命令 
 *  ActionListener.actionPerformed(ActionEvent e); 
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
implements ActionListener, MouseMotionListener, WindowListener {
	
	/**
	 * 处理按键
	 */
	private class Keypad implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			
			// 发生按键时隐藏鼠标
			Window.this.hideCursor();
			
			int code = e.getKeyCode();
			
			Command cmd = key2cmd(code);
			
			if (cmd != null)
			{
				Window.this.putCommand(cmd);
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
		
		/**
		 * 将按键转换成命令
		 * 
		 * 如果没有对应的命令，则返回 null
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
	
	private final Cursor cursor;
	private boolean isCursorShow;
	/**
	 * 窗口上的菜单栏
	 */
	final private MenuBar menu;
	private App app;
	
	private Window()
	{
		app = null;
		
		this.setTitle("俄罗斯方块");
		// WindowConstants.EXIT_ON_CLOSE;
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		
		this.setSize(200, 200);
		//this.setBounds(500, 50, 200, 200);
		
		this.enableInputMethods(false);
		
		// 创建一个透明的鼠标光标
		Toolkit tk = Toolkit.getDefaultToolkit();
		Point pt = new Point(0, 0);
		MemoryImageSource src = new MemoryImageSource(0, 0, new int[0], 0, 0);
		Image imageCursor = tk.createImage(src);
		/*URL classUrl = this.getClass().getResource("");
		Image imageCursor = tk.getImage(classUrl);*/
		cursor = tk.createCustomCursor(imageCursor, pt, "cursor");
		isCursorShow = true;
		
		// set up the menu bar.
		menu = new MenuBar(this);
		this.setJMenuBar(menu);
		
		this.add(new GamePanel());
		this.add(new SidePanel(), BorderLayout.EAST);
		this.pack();
		// 使窗体居中
		this.setLocationRelativeTo(null);
		
		this.addKeyListener(new Keypad());
		this.addMouseMotionListener(this);
		this.addWindowListener(this);
		this.setVisible(true);
	}
	
	
	public void hideCursor()
	{
		if (isCursorShow)
		{
			this.setCursor(cursor);
			
			isCursorShow = false;
		}
	}
	
	public void showCursor()
	{
		if (!isCursorShow)
		{
			this.setCursor(Cursor.getDefaultCursor());
			
			isCursorShow = true;
		}
	}
	
	/**
	 * 清理当前的游戏实例
	 */
	private void killGame()
	{
		if (this.app != null)
		{
			this.app.dispose();
			this.app = null;
		}
	}
	
	/**
	 * 创建一个新的游戏实例
	 * 
	 * 自动清理已存在的游戏实例
	 */
	private void createNewGame()
	{
		killGame();
		
		this.app = new App(this);
		
		this.app.addGameListener(menu);
		// 立即开始运行
		this.app.start();
	}
	
	synchronized public App getApp()
	{
		return app;
	}
	
	/**
	 * 保存一个命令
	 */
	public void putCommand(Command cmd)
	{
		if (app == null)
			return;
		
		if (app.isGameOver())
			return;

		app.putCommand(cmd);
	}
	
	@Override 
	synchronized public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
		
		if (command == "结束游戏")
		{
			killGame();
			
			this.repaint();
		}
		else if (command == "暂停")
		{
			//JCheckBoxMenuItem item = (JCheckBoxMenuItem) e.getSource();
			//boolean isCheck = item.isSelected();
			
			putCommand(Command.PAUSE);
		}
		else if (command == "新游戏")
		{
			createNewGame();
		}
		else if (command == "帮助")
		{
			new HelpDialog(this);
		}
		else if (command == "退出")
		{
			// 退出的意思是： 关闭当前窗口
			this.dispose();
		}
		else
		{
			System.out.println("菜单命令：" + command);
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
		Task.close();
		
		System.exit(0);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		
		// 关闭窗口时，必须结束游戏实例
		killGame();
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
