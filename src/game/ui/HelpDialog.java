package game.ui;

import game.config.TetrisConstants;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

/**
 * 帮助 - 对话框
 */
public class HelpDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	public HelpDialog(Window win)
	{
		super(win, "帮助", true);
		
		java.awt.Window owner = this.getOwner();

		this.setResizable(true);
		
		
		JTextArea text = new JTextArea();
		text.setOpaque(false);
		text.setLineWrap(true);
		text.setEditable(false);
		text.setFont(TetrisConstants.FONT);
		String doc = getHelpContents();
		text.setText(doc);
		
		
		
		//jTextField.setBorder(null);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setPreferredSize(new Dimension(600, 400));
		scroll.setBorder(null);
		scroll.setViewportBorder(new EmptyBorder(10, 10, 10, 10));
		scroll.setViewportView(text);
		scroll.getViewport().setOpaque(false);
		scroll.setOpaque(false);
		
		this.add(scroll);
		this.pack();
		// 再重新定位在 Window 之上
		this.setLocationRelativeTo(owner);
		
		
		this.setVisible(true);
	}
	
	/**
	 * 返回帮助文本
	 * 
	 * 帮助文本必须为 UTF-8 编码
	 */
	private String getHelpContents()
	{
		String path = TetrisConstants.RES_DOC_FOLDER + "/help.txt";
		
		try {
			URL url = HelpDialog.class.getResource(path);
			
			if (url == null)
				return "error(1)";
			
			URI uri = url.toURI();
			File file = new File(uri);
			FileInputStream is = new FileInputStream(file);
			InputStreamReader rd = new InputStreamReader(is, "UTF-8");
			
			char[] buffer = new char[1000];
			StringBuilder sb = new StringBuilder();
			int readChars;
			
			while (true) {
				readChars = rd.read(buffer);
				
				if (readChars == -1)
					break;
				
				sb.append(buffer, 0, readChars);
			}
			String doc = sb.toString();
			
			return doc;
		} 
		catch (URISyntaxException e) 
		{
			e.printStackTrace();
			return "error";
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			return "error";
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return "error";
		}
	}
}
