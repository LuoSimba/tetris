package game.sound;

import javax.sound.midi.InvalidMidiDataException;

/**
 * ±≥æ∞“Ù¿÷ –°∞◊¥¨
 */
public class BanDal extends Song {
	
	@Override
	public int getTempo()
	{
		return 50;
	}
	


	public BanDal() throws InvalidMidiDataException
	{
		switchTrack(1);
		
		// 1=bE
		rightHand(63);
		
		switchTrack(2);
		leftHand(63-12);
	}
	
	private void rightHand(int base)
	{
		int g = base - 12 + 7;
		int a = base - 12 + 9;
		int C = base;
		int D = base + 2;
		int E = base + 4;
		int F = base + 5;
		int G = base + 7;
		int A = base + 9;
		int B = base + 11;
		int CC = base + 12;
		
		// 5-6;5-3;5321;5--;
		addNote(G, 4);
		addNote(A, 2);
		addNote(G, 4);
		addNote(E, 2);
		addNote(G, 2);
		addNote(E, 1);
		addNote(D, 1);
		addNote(C, 2);
		addNote(g, 6);
		
		// 6-1;2-5;3--;---;
		addNote(a, 4);
		addNote(C, 2);
		addNote(D, 4);
		addNote(G, 2);
		addNote(E, 12);
		
		// 5-6;5-3;5321;5--;
		addNote(G, 4);
		addNote(A, 2);
		addNote(G, 4);
		addNote(E, 2);
		addNote(G, 2);
		addNote(E, 1);
		addNote(D, 1);
		addNote(C, 2);
		addNote(g, 6);
		
		// 6-1;5-2;1--;
		addNote(a, 4);
		addNote(C, 2);
		addNote(g, 4);
		addNote(D, 2);
		addNote(C, 6);
		
		// 3-2;3-2;3-6;5--;
		addNote(E, 4);
		addNote(D, 2);
		addNote(E, 4);
		addNote(D, 2);
		addNote(E, 4);
		addNote(A, 2);
		addNote(G, 6);
		
		// 3-2;3-6;5--;
		addNote(E, 4);
		addNote(D, 2);
		addNote(E, 4);
		addNote(A, 2);
		addNote(G, 6);
		
		// i--;5--;3-5;6--;
		addNote(CC, 6);
		addNote(G, 6);
		addNote(E, 4);
		addNote(G, 2);
		addNote(A, 6);
		
		// 5321;5-2;1--;---;
		addNote(G, 2);
		addNote(E, 1);
		addNote(D, 1);
		addNote(C, 2);
		addNote(g, 4);
		addNote(D, 2);
		addNote(C, 12);
	}
	
	private void leftHand(int base)
	{
		int c = base - 12;
		int f = base - 12 + 5;
		int g = base - 12 + 7;
		int C = base;
		int D = base + 2;
		int E = base + 4;
		int G = base + 7;
		int A = base + 9;
		int B = base + 11;
		int CC = base + 12;
		
		//// 153535;
		addNote(c, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		// 153535;
		addNote(c, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		// 153535;
		addNote(c, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		// 153535;
		addNote(c, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		
		////416161
		addNote(f, 1);
		addNote(C, 1);
		addNote(A, 1);
		addNote(C, 1);
		addNote(A, 1);
		addNote(C, 1);
		
		// 527272
		addNote(g, 1);
		addNote(D, 1);
		addNote(B, 1);
		addNote(D, 1);
		addNote(B, 1);
		addNote(D, 1);
		
		// 153535;
		addNote(c, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		// 153535;
		addNote(c, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		
		//// 153535;
		addNote(c, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		// 153535;
		addNote(c, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		// 153535;
		addNote(c, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		// 153535;
		addNote(c, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);

		////416161
		addNote(f, 1);
		addNote(C, 1);
		addNote(A, 1);
		addNote(C, 1);
		addNote(A, 1);
		addNote(C, 1);
		
		// 527272
		addNote(g, 1);
		addNote(D, 1);
		addNote(B, 1);
		addNote(D, 1);
		addNote(B, 1);
		addNote(D, 1);
		
		// 153535;
		addNote(c, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);

		//// 153535;
		addNote(c, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		// 153535;
		addNote(c, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		// 153535;
		addNote(c, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		// 153535;
		addNote(c, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);

		//// 153535;
		addNote(c, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		// 153535;
		addNote(c, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		// 153535;
		addNote(c, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);

		//// 153535;
		addNote(c, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);

		// 153535;
		addNote(c, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);

		// 153535;
		addNote(c, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		
		//416161
		addNote(f, 1);
		addNote(C, 1);
		addNote(A, 1);
		addNote(C, 1);
		addNote(A, 1);
		addNote(C, 1);

		//// 153535;
		addNote(c, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);
		
		// 527272
		addNote(g, 1);
		addNote(D, 1);
		addNote(B, 1);
		addNote(D, 1);
		addNote(B, 1);
		addNote(D, 1);
		
		addNote(c, 1);
		addNote(g, 1);
		addNote(c, 1);
		addNote(E, 1);
		addNote(g, 1);
		addNote(E, 1);
		addNote(g, 1);

		addNote(C, 1);
		addNote(E, 1);
		addNote(G, 1);
		addNote(A, 1);
		
		addNote(CC, 2);
	}
}
