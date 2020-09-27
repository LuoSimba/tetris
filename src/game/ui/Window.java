package game.ui;


import game.App;
import game.input.Keypad;
import game.input.MouseMotion;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * 窗口自己管理控件
 */
public class Window extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static Window win;
	
	private final Cursor cursor;
	private boolean isCursorShow;
	private App app;
	
	public static Window getInstance()
	{
		if (win == null)
			win = new Window();
		
		return win;
	}
	
	private Window()
	{
		this.setTitle("Tetris");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setBounds(500, 50, 200, 200);
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
		
		this.add(new GamePanel());
		this.add(new SidePanel(), BorderLayout.EAST);
		this.pack();
		
		this.addKeyListener(new Keypad());
		this.addMouseMotionListener(new MouseMotion());
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
	
	public void setApp(App app)
	{
		this.app = app;
	}
	
	public App getApp()
	{
		return app;
	}
}
