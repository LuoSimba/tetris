package game.ui;


import game.App;
import game.input.Keypad;
import game.input.MouseMotion;
import game.model.Command;
import game.model.EventQueue;
import game.signal.GameOverSignal;
import game.sound.RealPlayer;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * �����Լ�����ؼ�
 * 
 * ���ڸ����������
 */
public class Window extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static Window win;
	
	private final Cursor cursor;
	private boolean isCursorShow;
	private EventQueue queue;
	private App app;
	
	public static Window getInstance()
	{
		if (win == null)
			win = new Window();
		
		return win;
	}
	
	private Window()
	{
		queue = new EventQueue();
		
		this.setTitle("����˹����");
		// WindowConstants.DISPOSE_ON_CLOSE;
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		this.setSize(200, 200);
		//this.setBounds(500, 50, 200, 200);
		
		this.enableInputMethods(false);
		
		// ����һ��͸���������
		Toolkit tk = Toolkit.getDefaultToolkit();
		Point pt = new Point(0, 0);
		MemoryImageSource src = new MemoryImageSource(0, 0, new int[0], 0, 0);
		Image imageCursor = tk.createImage(src);
		/*URL classUrl = this.getClass().getResource("");
		Image imageCursor = tk.getImage(classUrl);*/
		cursor = tk.createCustomCursor(imageCursor, pt, "cursor");
		isCursorShow = true;
		
		// set up the menu bar.
		this.setJMenuBar(new MenuBar());
		
		this.add(new GamePanel());
		this.add(new SidePanel(), BorderLayout.EAST);
		this.pack();
		// ʹ�������
		this.setLocationRelativeTo(null);
		
		this.addKeyListener(new Keypad(this));
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
	
	/**
	 * ����һ������
	 */
	public void putCommand(Command cmd)
	{
		// put ������һֱ�ȴ�
		//queue.put(cmd);
		//queue.add(cmd);
		

		queue.offer(cmd);
	}
	
	public void play()
	{
		try {
			app.gameStart();
			
			while (true)
			{
				Command cmd = queue.take();
				
				if (app.isPaused() && cmd == Command.PAUSE)
					cmd = Command.RESUME;
				
				app.play(cmd);
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GameOverSignal e) {
			
			// ��Ϸ����
			RealPlayer.getInstance().playGameOver();
		}
		
		// App.dispose();
	}
}
