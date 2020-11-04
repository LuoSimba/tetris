package game.ui;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;

/**
 * �����
 */
public class CursorUtil {
	
	private static Cursor cur;

	/**
	 * �õ�͸���������
	 */
	public static Cursor getTransparentCursor()
	{
		if (cur == null)
		{
			Toolkit tk = Toolkit.getDefaultToolkit();
			
			Point pt = new Point(0, 0);
			
			// ������Ӧ��ͼƬ
			MemoryImageSource src = new MemoryImageSource(0, 0, new int[0], 0, 0);
			Image img = tk.createImage(src);

			cur = tk.createCustomCursor(img, pt, "cursor");
		}
		
		return cur;
	}
	
	private CursorUtil()
	{
	}
}
