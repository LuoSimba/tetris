package game.ui;


import game.App;
import game.input.Keypad;
import game.model.Command;
import game.signal.GameOverSignal;
import game.sound.RealPlayer;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.MemoryImageSource;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * 窗口自己管理控件
 * 
 * 窗口负责接收输入
 * 
 * 窗口负责处理菜单命令
 * 
 * 窗口负责处理鼠标移动事件
 * 
 * 窗口自定义关闭事件
 */
public class Window extends JFrame 
implements ActionListener, MouseMotionListener, WindowListener {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 存放窗口实例
	 */
	private static HashSet<Window> set = new HashSet<Window>();
	
	/**
	 * 创建窗口的统一方式
	 */
	public static void createWindow()
	{
		Window win = new Window();
		set.add(win);
	}
	
	private final Cursor cursor;
	private boolean isCursorShow;
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
		this.setJMenuBar(new MenuBar(this));
		
		this.add(new GamePanel());
		this.add(new SidePanel(), BorderLayout.EAST);
		this.pack();
		// 使窗体居中
		this.setLocationRelativeTo(null);
		
		this.addKeyListener(new Keypad(this));
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
	 * 创建一个新的游戏实例
	 * 
	 * 自动丢弃旧的游戏
	 */
	private void createNewGame()
	{
		// 自动清理旧数据
		if (this.app != null)
		{
			this.app.dispose();
		}
		
		this.app = new App(this);
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
		if (app != null)
		{
			app.putCommand(cmd);
		}
	}
	
	/**
	 * ActionListener method
	 */
	@Override 
	synchronized public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
		
		if (command == "新游戏")
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
		set.remove(this);
		
		if (set.isEmpty())
		{
			System.out.println("No window exists. Exit program.");
			
			// XXX force exit!
			System.exit(0);
		}
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// do nothing
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
