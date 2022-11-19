package game.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JPanel;

/**
 * ”Œœ∑◊¥Ã¨ ”Õº
 *
 */
public class StatusView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private boolean bBGM = false;
	private Image icoBGM = null;
	private Image icoHelp = null;
	
	private URL urlBGM = null;
	private URL urlHelp = null;
	
	private Color colorGray = new Color(80, 80, 80);
	private Color colorHi   = new Color(255, 80, 80);
	private Color colorText = new Color(0, 0, 0);
	
	StatusView()
	{
		this.setPreferredSize(new Dimension(32,32));
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		
		// ±≥æ∞“Ù¿÷Õº±Í
		urlBGM  = StatusView.class.getResource("/res/image/ico-bgm.png");
		urlHelp = StatusView.class.getResource("/res/image/ico-help.png");
		
		icoBGM  = tk.getImage(urlBGM);
		icoHelp = tk.getImage(urlHelp);
	}
	
	public void musicOn() {
		bBGM = true;
		this.repaint();
	}
	
	public void musicOff() {
		bBGM = false;
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setBackground(colorGray);
		g2.clearRect(0, 0, this.getWidth(), this.getHeight());
		
		g2.setColor(bBGM ? colorHi : colorGray);
		g2.fillRect(0, 0, 32, 32);
		
		g2.drawImage(icoBGM,  0,  0, null);
		g2.drawImage(icoHelp, 0, 32, null);
		
		g2.setColor(colorText);
		g2.drawString("M",  0, 32);
		g2.drawString("F1", 0, 64);
		
	}

}
