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
 * ֻ�ܴ���һ������ʵ��
 * 
 * �����Լ�����ؼ�
 * 
 * ==========================================================
 * 
 * �����¼��ļ���ȫ���� AWT-EventQueue-0 �߳���ִ�У�
 * 
 * ���ڸ����������
 *  Keypad
 * 
 * ���ڸ�����˵����� 
 *  ActionListener.actionPerformed(ActionEvent e); 
 * 
 * ���ڸ���������ƶ��¼�
 *  MouseMotionListener.mouseMoved(MouseEvent);
 * 
 * �����Զ���ر��¼�
 *  WindowListener.windowClosed(WindowEvent e);
 *  
 * ==========================================================
 * 
 * ���ڿ��Ʊ������ֵĿ���
 */
public class Window extends JFrame 
implements ActionListener, MouseMotionListener, WindowListener {
	
	/**
	 * ������
	 */
	private class Keypad implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			
			// ��������ʱ�������
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
	 * ��Ŵ���ʵ��
	 */
	private static Window inst = null;
	
	/**
	 * �������ڵ�ͳһ��ʽ
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
	 * �����ϵĲ˵���
	 */
	final private MenuBar menu;
	final private JPanel content;
	private GameView view1;
	private GameView view2;
	
	private Window()
	{
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
		menu = new MenuBar(this);
		this.setJMenuBar(menu);
		
		content = (JPanel) this.getContentPane();
		content.setLayout(new BoxLayout(content, BoxLayout.X_AXIS));
		
		view1 = new GameView("���A");
		view2 = new GameView("���B");
		
		menu.add(view1.getMenu());
		
		this.add(view1);
		this.pack();
		// ʹ�������
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
		
		
		if (command == "˫����Ϸ")
		{
			JCheckBoxMenuItem item = (JCheckBoxMenuItem) src;
			boolean isCheck = item.isSelected();
			
			setDouble(isCheck);
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
	
		// �ǵùرձ�������
		RealPlayer.close();
		
		// �Ͷ�ʱ������
		Task.close();
		
		System.exit(0);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		
		// �رմ���ʱ�����������Ϸʵ��
		
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
