package game.sound;

import javax.sound.midi.InvalidMidiDataException;

/**
 * ±³¾°ÒôÀÖ Ð¡ÐÇÐÇ
 */
public class LittleStar extends Song {
	
	private void rightHand(int base)
	{
		int C = base;
		int D = base + 2;
		int E = base + 4;
		int F = base + 5;
		int G = base + 7;
		int A = base + 9;

		addNote(C, 4);
		addNote(C, 4);
		addNote(G, 4);
		addNote(G, 4);
		addNote(A, 4);
		addNote(A, 4);
		addNote(G, 8);
		
		addNote(F, 4);
		addNote(F, 4);
		addNote(E, 4);
		addNote(E, 4);
		addNote(D, 4);
		addNote(D, 4);
		addNote(C, 8);
		
		addNote(G, 4);
		addNote(G, 4);
		addNote(F, 4);
		addNote(F, 4);
		addNote(E, 4);
		addNote(E, 4);
		addNote(D, 8);
		
		addNote(G, 4);
		addNote(G, 4);
		addNote(F, 4);
		addNote(F, 4);
		addNote(E, 4);
		addNote(E, 4);
		addNote(D, 8);
		
		addNote(C, 4);
		addNote(C, 4);
		addNote(G, 4);
		addNote(G, 4);
		addNote(A, 4);
		addNote(A, 4);
		addNote(G, 8);
		
		addNote(F, 4);
		addNote(F, 4);
		addNote(E, 4);
		addNote(E, 4);
		addNote(D, 4);
		addNote(D, 4);
		addNote(C, 8);
	}

	/**
	 * ×óÊÖµ¯×à¸´µ÷
	 */
	private void leftHandPolyphonic(int base) 
	{
		int cc = base - 12;
		int ff = base - 12 + 5;
		int gg = base - 12 + 7;
		int aa = base - 12 + 9;
		int bb = base - 12 + 11;
		int c = base;
		int d = base + 2;
		int e = base + 4;
		int f = base + 5;
		
		addNote(cc, 4);
		addNote(c, 4);
		addNote(e, 4);
		addNote(c, 4);
		addNote(f, 4);
		addNote(c, 4);
		addNote(e, 4);
		addNote(c, 4);

		addNote(d, 4);
		addNote(bb, 4);
		addNote(c, 4);
		addNote(aa, 4);
		addNote(ff, 4);
		addNote(gg, 4);
		addNote(cc, 8);

		addNote(e, 4);
		addNote(gg, 4);
		addNote(d, 4);
		addNote(gg, 4);
		addNote(c, 4);
		addNote(gg, 4);
		addNote(bb, 4);
		addNote(gg, 4);

		addNote(e, 4);
		addNote(gg, 4);
		addNote(d, 4);
		addNote(gg, 4);
		addNote(c, 4);
		addNote(gg, 4);
		addNote(bb, 4);
		addNote(gg, 4);

		addNote(cc, 4);
		addNote(c, 4);
		addNote(e, 4);
		addNote(c, 4);
		addNote(f, 4);
		addNote(c, 4);
		addNote(e, 4);
		addNote(c, 4);

		addNote(d, 4);
		addNote(bb, 4);
		addNote(c, 4);
		addNote(aa, 4);
		addNote(ff, 4);
		addNote(gg, 4);
		addNote(cc, 8);

	}
	
	/**
	 * ×óÊÖµ¯×àºÍÏÒ
	 */
	private void leftHandChord(int base)
	{
		int bb = base - 1;
		int c = base;
		int d = base + 2;
		int e = base + 4;
		int f = base + 5;
		int g = base + 7;
		int a = base + 9;
		
		addNote(c, 2);
		addNote(g, 2);
		addNote(e, 2);
		addNote(g, 2);
		addNote(c, 2);
		addNote(g, 2);
		addNote(e, 2);
		addNote(g, 2);
		addNote(c, 2);
		addNote(a, 2);
		addNote(f, 2);
		addNote(a, 2);
		addNote(c, 2);
		addNote(g, 2);
		addNote(e, 2);
		addNote(g, 2);

		addNote(bb, 2);
		addNote(g, 2);
		addNote(d, 2);
		addNote(g, 2);
		addNote(c, 2);
		addNote(g, 2);
		addNote(e, 2);
		addNote(g, 2);
		addNote(bb, 2);
		addNote(g, 2);
		addNote(d, 2);
		addNote(g, 2);
		addNote(c, 2);
		addNote(g, 2);
		addNote(e, 2);
		addNote(g, 2);
		
		addNote(c, 2);
		addNote(g, 2);
		addNote(e, 2);
		addNote(g, 2);
		addNote(bb, 2);
		addNote(g, 2);
		addNote(d, 2);
		addNote(g, 2);
		addNote(c, 2);
		addNote(g, 2);
		addNote(e, 2);
		addNote(g, 2);
		addNote(bb, 2);
		addNote(g, 2);
		addNote(d, 2);
		addNote(g, 2);

		addNote(c, 2);
		addNote(g, 2);
		addNote(e, 2);
		addNote(g, 2);
		addNote(bb, 2);
		addNote(g, 2);
		addNote(d, 2);
		addNote(g, 2);
		addNote(c, 2);
		addNote(g, 2);
		addNote(e, 2);
		addNote(g, 2);
		addNote(bb, 2);
		addNote(g, 2);
		addNote(d, 2);
		addNote(g, 2);

		addNote(c, 2);
		addNote(g, 2);
		addNote(e, 2);
		addNote(g, 2);
		addNote(c, 2);
		addNote(g, 2);
		addNote(e, 2);
		addNote(g, 2);
		addNote(c, 2);
		addNote(a, 2);
		addNote(f, 2);
		addNote(a, 2);
		addNote(c, 2);
		addNote(g, 2);
		addNote(e, 2);
		addNote(g, 2);

		addNote(bb, 2);
		addNote(g, 2);
		addNote(d, 2);
		addNote(g, 2);
		addNote(c, 2);
		addNote(g, 2);
		addNote(e, 2);
		addNote(g, 2);
		addNote(bb, 2);
		addNote(g, 2);
		addNote(d, 2);
		addNote(g, 2);
		addNote(c, 2);
		addNote(g, 2);
		addNote(e, 2);
		addNote(g, 2);

	}
	
	public LittleStar() throws InvalidMidiDataException
	{
		switchTrack(1);
			
		rightHand(60);
		rightHand(72);
		rightHand(60);
		rightHand(72);
		
		switchTrack(2);
		
		leftHandPolyphonic(48);
		leftHandPolyphonic(60);
		leftHandChord(48);
		leftHandChord(60);
	}
}
