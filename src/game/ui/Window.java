package game.ui;


import game.App;
import game.input.Keypad;
import game.model.Command;
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
 * �����Լ�����ؼ�
 * 
 * ���ڸ����������
 * 
 * ���ڸ�����˵�����
 * 
 * ���ڸ���������ƶ��¼�
 * 
 * �����Զ���ر��¼�
 * 
 * ���ڿ��Ʊ������ֵĿ���
 */
public class Window extends JFrame 
implements ActionListener, MouseMotionListener, WindowListener {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * ��Ŵ���ʵ��
	 */
	private static HashSet<Window> set = new HashSet<Window>();
	
	/**
	 * �������ڵ�ͳһ��ʽ
	 */
	public static void createWindow()
	{
		if (set.isEmpty())
			RealPlayer.getInstance().play();
		
		Window win = new Window();
		set.add(win);
	}
	
	private final Cursor cursor;
	private boolean isCursorShow;
	private App app;
	
	private Window()
	{
		app = null;
		
		this.setTitle("����˹����");
		// WindowConstants.EXIT_ON_CLOSE;
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
		this.setJMenuBar(new MenuBar(this));
		
		this.add(new GamePanel());
		this.add(new SidePanel(), BorderLayout.EAST);
		this.pack();
		// ʹ�������
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
	 * ����һ���µ���Ϸʵ��
	 * 
	 * �Զ������ɵ���Ϸ
	 */
	private void createNewGame()
	{
		// �Զ����������
		if (this.app != null)
		{
			this.app.dispose();
		}
		
		this.app = new App(this);
		// ������ʼ����
		this.app.start();
	}
	
	synchronized public App getApp()
	{
		return app;
	}
	
	/**
	 * ����һ������
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
		
		if (command == "����Ϸ")
		{
			createNewGame();
		}
		else if (command == "����")
		{
			new HelpDialog(this);
		}
		else if (command == "�˳�")
		{
			// �˳�����˼�ǣ� �رյ�ǰ����
			this.dispose();
		}
		else
		{
			System.out.println("�˵����" + command);
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
			
			RealPlayer.getInstance().shutdown();
			
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
