public class Trial	{
	public static void main(String[] args)	{
		byte[] input = {
			(byte)0x14, (byte)0x24
		};
		Xts x = new Xts(input,"aassddffgghhjjkk");
		System.out.println(x.getPlainText());
		System.out.println(x.getKeyOne());
		System.out.println(x.getKeyTwo());
		System.out.println("DONE");
	}
}