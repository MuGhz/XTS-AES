import java.math.BigInteger;

public class Utilities {
	// mwahahahaha 0x100000000000000000000000000000087
	static byte[] r = {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, (byte)0x87};
	static final BigInteger ROOT = new BigInteger(r);
	
	/**
	 * ngitung perkalian a*b dalam GF(2^128)
	 * Input:
	 * a - biginteger pertama yang mau dikaliin
	 * b - biginteger kedua yang mau dikaliin
	*/
	public static BigInteger mult128(BigInteger a, BigInteger b) {
		BigInteger c = BigInteger.ZERO;
		int i = 128;
		while((i > 0) && (!b.equals(BigInteger.ZERO))) {
			i--;
			if(b.testBit(0)) c = c.xor(a);
			a = a.shiftLeft(1);
			if(a.testBit(128)) a = a.xor(ROOT);
			b = b.shiftRight(1);
		}
		return c;
	}
	
	/**
	 * ngitung perkalian a*b dalam GF(2^128)
	 * Input:
	 * a - array of byte yang isinya bit yang mau dikaliin, most significant bit di indeks terkecil
	 * b - array of byte yang isinya bit yang mau dikaliin, most significant bit di indeks terkecil
	*/
	public static BigInteger mult128(byte[] a, byte[] b) {
		byte[] anew = new byte[17];
		byte[] bnew = new byte[17];
		anew[0] = 0;
		bnew[0] = 0;
		int lena = a.length;
		if(lena > 16) {
			int diff = lena - 16;
			for(int i = diff; i < lena; i++) {
				anew[i-diff+1] = a[i];
			}
		} else {
			int diff = 16 - lena;
			for(int i = diff; i < 16; i++) {
				anew[i+1] = a[i-diff];
			}
		}
		int lenb = b.length;
		if(lenb > 16) {
			int diff = lenb - 16;
			for(int i = diff; i < lenb; i++) {
				bnew[i-diff+1] = b[i];
			}
		} else {
			int diff = 16 - lenb;
			for(int i = diff; i < 16; i++) {
				bnew[i+1] = b[i-diff];
			}
		}
		return mult128(new BigInteger(anew), new BigInteger(bnew));
	}
}