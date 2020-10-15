package game.ui;


import game.App;
import game.config.TetrisConstants;
import game.input.Keypad;
import game.input.MouseMotion;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.MemoryImageSource;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
		this.setTitle("俄罗斯方块");
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
		
		// set up the menu bar.
		JMenuBar menubar = new JMenuBar();
		this.setJMenuBar(menubar);
		
		JMenu menu = new JMenu("文件");
		menubar.add(menu);
		
		JMenuItem item = new JMenuItem("退出");
		menu.add(item);
		
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String command = e.getActionCommand();
				
				if (command == "退出")
				{
					System.exit(0);
				}
				else
				{
					System.out.println("菜单命令：" + command);
				}
			}
		};
		item.addActionListener(al);
		
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
