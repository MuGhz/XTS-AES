import java.math.BigInteger;
import java.util.Arrays;

public class XTSAES {
	// mwahahahaha 0x100000000000000000000000000000087
	static byte[] r = {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, (byte)0x87};
	static final BigInteger ROOT = new BigInteger(1, r);
	// 170DEB8F863C5F98EE8548FC6EE396C4
	static final byte[] TWEAK_VALUE = {(byte)0x17, (byte)0x0D, (byte)0xEB, (byte)0x8F, (byte)0x86,
								(byte)0x3C, (byte)0x5F, (byte)0x98, (byte)0xEE, (byte)0x85, 
								(byte)0x48, (byte)0xFC, (byte)0x6E, (byte)0xE3, (byte)0x96, (byte)0xC4};
	
	/**
	 * ngitung perkalian a*b dalam GF(2^128)
	 * Input:
	 * a - biginteger pertama yang mau dikaliin
	 * b - biginteger kedua yang mau dikaliin
	*/
	private static BigInteger mult128(BigInteger a, BigInteger b) {
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
	public static byte[] mult128(byte[] a, byte[] b) {
		return pad128(mult128(new BigInteger(1, a), new BigInteger(1, b)).toByteArray());
	}
	
	public static byte[] xor128(byte[] a, byte[] b) {
		BigInteger anew = new BigInteger(1, a);
		BigInteger bnew = new BigInteger(1, b);
		return pad128(anew.xor(bnew).toByteArray());
	}
	
	public static byte[] pad128(byte[] a) {
		byte[] anew = new byte[16];
		int len = a.length;
		if(len > 16) {
			int diff = len - 16;
			for(int i = diff; i < len; i++) {
				anew[i-diff] = a[i];
			}
		} else {
			int diff = 16 - len;
			for(int i = diff; i < 16; i++) {
				anew[i] = a[i-diff];
			}
		}

		return anew;
	}
	
	private static byte[] generateT(byte[] k2, int j) {
		if(k2.length != 16) throw new IllegalArgumentException("Ukuran kunci untuk generate T tidak sama dengan 16 byte");
		AES aes = new AES();
		aes.setKey(k2);
		byte[] result = aes.encrypt(TWEAK_VALUE);
		
		BigInteger a = new BigInteger("2");
		a = a.pow(j);
		
		return mult128(result, a.toByteArray());
	}
	
	public static byte[] encryptBlock(byte[] k, int j, byte[] p) {
		if(k.length != 32) throw new IllegalArgumentException("Ukuran kunci tidak sama dengan 32 byte");
		if(p.length != 16) throw new IllegalArgumentException("Ukuran blok plaintext tidak sama dengan 16 byte");
		if(j < 0) throw new IllegalArgumentException("j harus integer nonnegatif");
		
		byte[] k1 = Arrays.copyOfRange(k, 0, 16);
		byte[] k2 = Arrays.copyOfRange(k, 16, 32);
		
		byte[] t = generateT(k2, j);
		byte[] pp = xor128(t,p);
		
		AES aes = new AES();
		aes.setKey(k1);
		byte[] cc = aes.encrypt(pp);
		byte[] c = xor128(t,cc);
		return c;
	}
	
	public static byte[] decryptBlock(byte[] k, int j, byte[] c) {
		if(k.length != 32) throw new IllegalArgumentException("Ukuran kunci tidak sama dengan 32 byte");
		if(c.length != 16) throw new IllegalArgumentException("Ukuran blok ciphertext tidak sama dengan 16 byte");
		if(j < 0) throw new IllegalArgumentException("j harus integer nonnegatif");
		
		byte[] k1 = Arrays.copyOfRange(k, 0, 16);
		byte[] k2 = Arrays.copyOfRange(k, 16, 32);
		
		byte[] t = generateT(k2, j);
		byte[] cc = xor128(t,c);
		
		AES aes = new AES();
		aes.setKey(k1);
		byte[] pp = aes.decrypt(cc);
		byte[] p = xor128(t,pp);
		return p;
	}
	
	// buat ngetes
	public static void test() {
		byte[] plaintext = {(byte)0x12, (byte)0x34, (byte)0x56, (byte)0x78, (byte)0x9A,
								(byte)0xBC, (byte)0xDE, (byte)0xF0, (byte)0x11, (byte)0x22, 
								(byte)0x33, (byte)0x44, (byte)0x55, (byte)0x66, (byte)0x77, (byte)0x88};
		byte[] key = {(byte)0xFF, (byte)0x00, (byte)0xFF, (byte)0x00, (byte)0xFF,
								(byte)0x00, (byte)0xFF, (byte)0x00, (byte)0xFF, (byte)0x00, 
								(byte)0xFF, (byte)0x00, (byte)0xFF, (byte)0x00, (byte)0xFF, (byte)0x00, 
								(byte)0xFF, (byte)0x00, (byte)0xFF, (byte)0x00, (byte)0xFF,
								(byte)0x00, (byte)0xFF, (byte)0x00, (byte)0xFF, (byte)0x00, 
								(byte)0xFF, (byte)0x00, (byte)0xFF, (byte)0x00, (byte)0xFF, (byte)0x01};
								
		byte[] ciphertext = encryptBlock(key, 2, plaintext);
		byte[] plaintext2 = decryptBlock(key, 2, ciphertext);
		
		System.out.print("Plaintext asli: ");
		System.out.println(Util.toHEX(plaintext));
		
		System.out.print("Plaintext setelah enkripsi: ");
		System.out.println(Util.toHEX(ciphertext));
		
		System.out.print("Plaintext setelah dekripsi: ");
		System.out.println(Util.toHEX(plaintext2));
	}
}