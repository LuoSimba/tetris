package game.sound;

import javax.sound.midi.InvalidMidiDataException;

/**
 * ±≥æ∞“Ù¿÷ “ √……Ω–°µ˜
 */
class Yimeng extends Song {
	
	@Override
	public int getTempo()
	{
		return 90;
	}
	


	public Yimeng() throws InvalidMidiDataException
	{
		switchTrack(1);
		
		rightHandMain(60);
		rightHandIntermezzo(60);
		rightHandMain(60);
		
		rightHandMain(72);
		rightHandIntermezzo(72);
		rightHandMain(72);
		
		switchTrack(2);
		
		leftHandMain(60);
		leftHandIntermezzo(60);
		leftHandMain(60);
		
		leftHandMain(72);
		leftHandIntermezzo(72);
		leftHandMain(72);
	}
	
	private void rightHandMain(int base)
	{
		int e = base - 12 + 4;
		int g = base - 12 + 7;
		int a = base - 12 + 9;
		int b = base - 12 + 11;
		int C = base;
		int D = base + 2;
		int E = base + 4;
		int F = base + 5;
		int G = base + 7;
		int A = base + 9;
		int B = base + 11;
		
		// 25;323;5321;2;
		addNote(D, 4);
		addNote(G, 4);
		addNote(E, 2);
		addNote(D, 2);
		addNote(E, 4);
		addNote(G, 2);
		addNote(E, 2);
		addNote(D, 2);
		addNote(C, 2);
		addNote(D, 8);
		
		// 25;235;3216;1;
		addNote(D, 4);
		addNote(G, 4);
		addNote(D, 4);
		addNote(E, 3);
		addNote(G, 1);
		addNote(E, 2);
		addNote(D, 2);
		addNote(C, 2);
		addNote(a, 2);
		addNote(C, 8);
		
		// 13;235;2765;6;
		addNote(C, 4);
		addNote(E, 4);
		addNote(D, 2);
		addNote(E, 2);
		addNote(g, 4);
		addNote(D, 2);
		addNote(b, 2);
		addNote(a, 2);
		addNote(g, 2);
		addNote(a, 8);
		
		// 12;7653;5;5;
		addNote(C, 6);
		addNote(D, 2);
		addNote(b, 2);
		addNote(a, 2);
		addNote(g, 2);
		addNote(e, 2);
		addNote(g, 16);
	}
	
	private void rightHandIntermezzo(int base)
	{
		int e = base - 12 + 4;
		int g = base - 12 + 7;
		int a = base - 12 + 9;
		int b = base - 12 + 11;
		int C = base;
		int D = base + 2;
		int E = base + 4;
		int F = base + 5;
		int G = base + 7;
		int A = base + 9;
		int B = base + 11;
		
		// 13;235;2765;6-;
		addNote(C, 4);
		addNote(E, 4);
		addNote(D, 2);
		addNote(E, 2);
		addNote(g, 4);
		addNote(D, 2);
		addNote(b, 2);
		addNote(a, 2);
		addNote(g, 2);
		addNote(a, 8);
		
		// 12;7653;5676;5-;
		addNote(C, 6);
		addNote(D, 2);
		addNote(b, 2);
		addNote(a, 2);
		addNote(g, 2);
		addNote(e, 2);
		addNote(g, 3);
		addNote(a, 1);
		addNote(b, 2);
		addNote(a, 2);
		addNote(g, 8);
	}
	
	private void leftHandMain(int base)
	{
		int gg = base - 24 + 7;
		int aa = base - 24 + 9;
		
		int c = base - 12;
		int d = base - 12 + 2;
		int e = base - 12 + 4;
		int g = base - 12 + 7;
		int a = base - 12 + 9;
		int b = base - 12 + 11;
		
		int C = base;
		int D = base + 2;
		int E = base + 4;
		int F = base + 5;
		int G = base + 7;
		int A = base + 9;
		int B = base + 11;
		
		addNote(gg, 1);
		addNote(d, 1);
		addNote(g, 1);
		addNote(b, 1);
		addNote(D, 2);
		addNote(g, 2);
		
		addNote(c, 1);
		addNote(g, 1);
		addNote(C, 1);
		addNote(E, 1);
		addNote(G, 2);
		addNote(E, 2);
		
		addNote(c, 1);
		addNote(g, 1);
		addNote(C, 1);
		addNote(E, 1);
		addNote(G, 2);
		addNote(A, 2);
		
		addNote(d, 1);
		addNote(a, 1);
		addNote(D, 1);
		addNote(F, 1);
		addNote(A, 2);
		addNote(b, 2);

		//
		addNote(gg, 1);
		addNote(d, 1);
		addNote(g, 1);
		addNote(b, 1);
		addNote(D, 2);
		addNote(g, 2);
		
		addNote(gg, 1);
		addNote(d, 1);
		addNote(g, 1);
		addNote(b, 1);
		addNote(D, 2);
		addNote(g, 2);
		
		addNote(aa, 1);
		addNote(e, 1);
		addNote(a, 1);
		addNote(C, 1);
		addNote(E, 2);
		addNote(C, 2);
		
		addNote(aa, 1);
		addNote(e, 1);
		addNote(a, 1);
		addNote(C, 1);
		addNote(E, 2);
		addNote(a, 2);
		
		addNote(c, 1);
		addNote(g, 1);
		addNote(C, 1);
		addNote(E, 1);
		addNote(G, 2);
		addNote(E, 2);
		
		addNote(gg, 1);
		addNote(d, 1);
		addNote(g, 1);
		addNote(b, 1);
		addNote(D, 2);
		addNote(g, 2);

		addNote(gg, 1);
		addNote(d, 1);
		addNote(g, 1);
		addNote(b, 1);
		addNote(D, 2);
		addNote(g, 2);
		
		addNote(aa, 1);
		addNote(e, 1);
		addNote(a, 1);
		addNote(C, 1);
		addNote(E, 2);
		addNote(a, 2);

		addNote(aa, 1);
		addNote(e, 1);
		addNote(a, 1);
		addNote(C, 1);
		addNote(E, 2);
		addNote(a, 2);

		addNote(e, 1);
		addNote(b, 1);
		addNote(E, 1);
		addNote(G, 1);
		addNote(B, 2);
		addNote(E, 2);
		
		addNote(gg, 1);
		addNote(d, 1);
		addNote(g, 1);
		addNote(b, 1);
		addNote(D, 2);
		addNote(E, 2);
		
		addNote(g, 8);
	}
	
	private void leftHandIntermezzo(int base)
	{
		int gg = base - 24 + 7;
		int aa = base - 24 + 9;
		
		int c = base - 12;
		int d = base - 12 + 2;
		int e = base - 12 + 4;
		int g = base - 12 + 7;
		int a = base - 12 + 9;
		int b = base - 12 + 11;
		
		int C = base;
		int D = base + 2;
		int E = base + 4;
		int F = base + 5;
		int G = base + 7;
		int A = base + 9;
		int B = base + 11;
		
		addNote(aa, 1);
		addNote(e, 1);
		addNote(a, 1);
		addNote(C, 1);
		addNote(E, 2);
		addNote(a, 2);
		
		addNote(c, 1);
		addNote(g, 1);
		addNote(C, 1);
		addNote(E, 1);
		addNote(G, 2);
		addNote(E, 2);
		
		addNote(gg, 1);
		addNote(d, 1);
		addNote(g, 1);
		addNote(b, 1);
		addNote(D, 2);
		addNote(g, 2);

		addNote(gg, 1);
		addNote(d, 1);
		addNote(g, 1);
		addNote(b, 1);
		addNote(D, 2);
		addNote(g, 2);
		
		addNote(aa, 1);
		addNote(e, 1);
		addNote(a, 1);
		addNote(C, 1);
		addNote(E, 2);
		addNote(a, 2);

		addNote(aa, 1);
		addNote(e, 1);
		addNote(a, 1);
		addNote(C, 1);
		addNote(E, 2);
		addNote(a, 2);

		addNote(e, 1);
		addNote(b, 1);
		addNote(E, 1);
		addNote(G, 1);
		addNote(B, 2);
		addNote(E, 2);
		
		addNote(gg, 1);
		addNote(d, 1);
		addNote(g, 1);
		addNote(b, 1);
		addNote(D, 2);
		addNote(E, 2);
	}

}
