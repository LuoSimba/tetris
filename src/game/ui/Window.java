package game.ui;


import game.model.Command;
import game.model.Task;
import game.sound.RealPlayer;

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

import javax.swing.BoxLayout;
import javax.swing.JCheckBoxMenuItem;
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
				
			// player B
			case KeyEvent.VK_NUMPAD6:
				view2.putCommand(Command.RIGHT);
				break;
			case KeyEvent.VK_NUMPAD4:
				view2.putCommand(Command.LEFT);
				break;
			case KeyEvent.VK_NUMPAD8:
				view2.putCommand(Command.ROTATE);
				break;
			case KeyEvent.VK_NUMPAD5:
				view2.putCommand(Command.DOWN);
				break;
			case KeyEvent.VK_NUMPAD0:
				view2.putCommand(Command.PAUSE);
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
	
	private final Cursor cursor;
	private boolean isCursorShow;
	/**
	 * 窗口上的菜单栏
	 */
	final private MenuBar menu;
	final private JPanel content;
	private GameView view1;
	private GameView view2;
	
	private Window()
	{
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
		
		content = (JPanel) this.getContentPane();
		content.setLayout(new BoxLayout(content, BoxLayout.X_AXIS));
		
		view1 = new GameView("玩家A");
		view2 = new GameView("玩家B");
		
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
	
	private void setDouble(boolean isDouble)
	{
		if (isDouble)
		{
			menu.add(view2.getMenu());
			content.add(view2);
		}
		else
		{
			view2.killGame();
			menu.remove(view2.getMenu());
			content.remove(view2);
		}
		
		this.pack();
	}
	
	@Override 
	synchronized public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
		Object src = e.getSource();
		
		
		if (command == "双人游戏")
		{
			JCheckBoxMenuItem item = (JCheckBoxMenuItem) src;
			boolean isCheck = item.isSelected();
			
			setDouble(isCheck);
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
		
		view1.killGame();
		view2.killGame();
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
