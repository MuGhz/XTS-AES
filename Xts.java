import java.util.Arrays;

public class Xts{

	byte[] plaintext;
	byte[] key;

	public Xts(byte[] plaintext, String key)	{
		this.plaintext = plaintext;
		this.key = Util.hexStringToByteArray(key);
	}

	public byte[] getPlainText()	{
		return this.plaintext;
	}

	public byte[] getKey()	{
		return this.key;
	}

	public byte[] encode()	{
		int len = this.plaintext.length;
		byte[] enc = new byte[len];
		//fullblock
		if(len % 16 == 0)	{
			for(int i = 0; i < len; i += 16)	{
				byte[] block = Arrays.copyOfRange(this.plaintext,i,i+16);
				byte[] encrypted = XTSAES.encryptBlock(this.key,i,block);
				for(int j = i; j < i+16; j++)	{
					enc[j] = encrypted[j%16];
				}
			}
		}
		//handle ciphertext stealing
		else	{
			int diff = len%16;
			byte[] last = new byte[len-diff];
			for(int i = diff; i > 0; i--)	{
				last[diff-i] = this.plaintext[len-i];
			}
			int d = 0;
			for(int i = 0; i < len-diff; i += 16)	{
				byte[] block = Arrays.copyOfRange(this.plaintext,i,i+16);
				byte[] encrypted = XTSAES.encryptBlock(this.key,i,block);
				if(i+16 > len)	{
					int c = 0;
					for(int j = len-diff; j < len; j++)	{
						enc[j] = encrypted[c++];
					}
					for(int j = c; j < 16; j++)	{
						last[j] = encrypted[j];
					}
					d = i+16;
				} else	{
					for(int j = i; j < i+16; j++)	{
						enc[j] = encrypted[j%16];
					}
				}
			}
			byte[] final_block = XTSAES.encryptBlock(this.key,d,last);
			int last_empty_block = len-diff-16;
			for(int i = 0; i < 16; i++)	{
				enc[last_empty_block+i] = final_block[i];
			}
		}
		return enc;
	}

	public byte[] decode()	{
		int len = this.plaintext.length;
		byte[] enc = new byte[len];
		//fullblock
		if(len % 16 == 0)	{
			for(int i = 0; i < len; i += 16)	{
				byte[] block = Arrays.copyOfRange(this.plaintext,i,i+16);
				byte[] encrypted = XTSAES.decryptBlock(this.key,i,block);
				for(int j = i; j < i+16; j++)	{
					enc[j] = encrypted[j%16];
				}
			}
		}
		//handle ciphertext stealing
		else	{
			int diff = len%16;
			byte[] last = new byte[len-diff];
			for(int i = diff; i > 0; i--)	{
				last[diff-i] = this.plaintext[len-i];
			}
			int d = 0;
			for(int i = 0; i < len-diff; i += 16)	{
				byte[] block = Arrays.copyOfRange(this.plaintext,i,i+16);
				byte[] encrypted = XTSAES.decryptBlock(this.key,i,block);
				if(i+16 > len)	{
					int c = 0;
					for(int j = len-diff; j < len; j++)	{
						enc[j] = encrypted[c++];
					}
					for(int j = c; j < 16; j++)	{
						last[j] = encrypted[j];
					}
					d = i+16;
				} else	{
					for(int j = i; j < i+16; j++)	{
						enc[j] = encrypted[j%16];
					}
				}
			}
			byte[] final_block = XTSAES.decryptBlock(this.key,d,last);
			int last_empty_block = len-diff-16;
			for(int i = 0; i < 16; i++)	{
				enc[last_empty_block+i] = final_block[i];
			}
		}
		return enc;
	}
}
