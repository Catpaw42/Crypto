import java.nio.charset.StandardCharsets;


public class CBC
{
	public static void main(String[] args)
	{
		int[] key = {13,4,3,12,1,0,8,10,14,6,9,15,11,2,5,7};
		String[] key2 ={"1101", "0100", "0011", "1100", "0001", "0000", "1000", "1010", "1110", "0110", "1001", "1111", "1011", "0010", "0101", "0111"};
		String plaintext = "0011";
		
		String init = "0000";
		String plain = "0011";
		for (int i = 0; i < 16; i++)
		{
			plain = XOR(plain, init);
			System.out.print(plain + " ");
			plain = key2[Integer.parseInt(plain, 2)];
			System.out.println(plain);
		}
	}

	private static String XOR (String s1, String s2)
	{
		String result = "";
		for (int i = 0; i < s1.length(); i++)
		{
			result += XOR(s1.charAt(i), s2.charAt(i));
		}
		return result;
	}
	
	private static int XOR(char i, char j)
	{
		if(i == j)
			return 0;
		else
			return 1;
	}
}
