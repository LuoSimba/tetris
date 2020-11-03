package game.ui;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;

/**
 * 鼠标光标
 */
public class CursorUtil {
	
	private static Cursor cur;

	/**
	 * 得到透明的鼠标光标
	 */
	public static Cursor getTransparentCursor()
	{
		if (cur == null)
		{
			Toolkit tk = Toolkit.getDefaultToolkit();
			
			Point pt = new Point(0, 0);
			
			MemoryImageSource src = new MemoryImageSource(0, 0, new int[0], 0, 0);
			
			Image imageCursor = tk.createImage(src);
			/*URL classUrl = this.getClass().getResource("");
			Image imageCursor = tk.getImage(classUrl);*/

			cur = tk.createCustomCursor(imageCursor, pt, "cursor");
		}
		
		return cur;
	}
	
	private CursorUtil()
	{
	}
}
