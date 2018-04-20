import XTSAES;

public class Xts{

	byte[] plaintext;
	String key1;
	String key2;
	byte[] key1byte;
	byte[] key2byte;

	public Xts(byte[] plaintext, String key)	{
		this.plaintext = plaintext;
		this.key1 = key.substring(0,8)
		this.key2 = key.substring(8,16)
	}

	public byte[] getPlainText()	{
		return this.plaintext
	}

	public String getKeyOne()	{
		return this.key1;
	}

	public String getKeyTwo()	{
		return this.key2;
	}

	public String encode()	{
		
		return "OK"
	}

	public String decode()	{
		
		return "OK"
	}
}