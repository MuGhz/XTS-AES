public class Trial	{
	public static void main(String[] args)	{
		byte[] input = {
			(byte)0x14, (byte)0x24,(byte)0x14, (byte)0x24,
			(byte)0x13, (byte)0x23,(byte)0x13, (byte)0x23,
			(byte)0x14, (byte)0x24,(byte)0x14, (byte)0x24,
			(byte)0x1A, (byte)0x2D,(byte)0x16, (byte)0x23,
			(byte)0x14, (byte)0x24,(byte)0x14, (byte)0x24,
			(byte)0x1D, (byte)0x25,(byte)0x12, (byte)0x25,
				(byte)0x14, (byte)0x24,(byte)0x14, (byte)0x24,
				(byte)0x13, (byte)0x23,(byte)0x13, (byte)0x23,
				(byte)0x14, (byte)0x24,(byte)0x14, (byte)0x24,
				(byte)0x1A, (byte)0x2D,(byte)0x16, (byte)0x23,
				(byte)0x14, (byte)0x24,(byte)0x14, (byte)0x24,
				(byte)0x1D, (byte)0x25,(byte)0x12, (byte)0x25,
					(byte)0x14, (byte)0x24,(byte)0x14, (byte)0x24,
					(byte)0x13, (byte)0x23,(byte)0x13, (byte)0x23,
					(byte)0x14, (byte)0x24,(byte)0x14, (byte)0x24,
					(byte)0x1A, (byte)0x2D,(byte)0x16, (byte)0x23,
					(byte)0x14, (byte)0x24,(byte)0x14, (byte)0x24,
					(byte)0x1D, (byte)0x25,(byte)0x12, (byte)0x25,
			(byte)0x14,
		};

		Xts x = new Xts(input,"AASSDDFFGGHHJJKKAASSDDFFGGHHJJKKAASSDDFFGGHHJJKKAASSDDFFGGHHJJKK");
		System.out.println(Util.toHEX(input));
		byte[] enz = x.encrypt();
		System.out.println(Util.toHEX(enz));
		Xts out = new Xts(enz,"AASSDDFFGGHHJJKKAASSDDFFGGHHJJKKAASSDDFFGGHHJJKKAASSDDFFGGHHJJKK");
		System.out.println(Util.toHEX(out.decrypt()));

	}
}
