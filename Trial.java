public class Trial	{
	public static void main(String[] args)	{
		byte[] input = {
			(byte)0x14, (byte)0x24,(byte)0x14, (byte)0x24,
			(byte)0x14, (byte)0x24,(byte)0x14, (byte)0x24,
			(byte)0x14, (byte)0x24,(byte)0x14, (byte)0x24,
			(byte)0x14, (byte)0x24,(byte)0x14, (byte)0x24
		};
		Xts x = new Xts(input,"AASSDDFFGGHHJJKKAASSDDFFGGHHJJKKAASSDDFFGGHHJJKKAASSDDFFGGHHJJKK");
		System.out.println(Util.toHEX(input));
		String enz = x.encode().replace(" ","");
		System.out.println(enz);
		Xts out = new Xts(Util.hexStringToByteArray(enz),"AASSDDFFGGHHJJKKAASSDDFFGGHHJJKKAASSDDFFGGHHJJKKAASSDDFFGGHHJJKK");
		System.out.println(out.decode());
	}
}
