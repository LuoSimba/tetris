package game.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * сно╥в╢л╛йсм╪
 *
 */
public class StatusView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	StatusView()
	{
		this.setPreferredSize(new Dimension(32,32));
	}

	@Override
	protected void paintComponent(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		
		Color c = new Color(80, 80, 80);
		g2.setColor(c);
		g2.fillRect(0, 0, 32, 32);
	}

}
