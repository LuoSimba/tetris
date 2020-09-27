package game.input;

import game.ui.Window;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMotion implements MouseMotionListener {

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		Window win = Window.getInstance();
		
		win.showCursor();
	}
}
