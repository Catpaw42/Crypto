import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;


public class XORencrypt
{
	public static String encrypt(String plaintext, String key)
	{
		//check that we have 128 bit = 16 char key and text
//		if(key.length() != 16 || plaintext.length()  != 16)
//			return null;

		String cryptogram = "";
		//get the byte representation of the strings
		byte[] plaintextBytes = plaintext.getBytes(StandardCharsets.UTF_8);
		byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
		//for each byte
		for (int i = 0; i < plaintext.length(); i++)
		{
			

			//convert to a BinaryString and pad it to length 8
			String pad = String.format("%0" + 8 + 'd', 0);

			String plainBin = Integer.toBinaryString(plaintextBytes[i]);
			plainBin = pad.substring(plainBin.length()) + plainBin;

			String keyBin = Integer.toBinaryString(keyBytes[i]);
			keyBin = pad.substring(keyBin.length()) + keyBin;

			//now that we have a binary representation, do the XOR for each binary digit
			String binCryptogram = XOR(plainBin, keyBin);
			
			//convert back to chars
			int charCode = Integer.parseInt(binCryptogram, 2);
			cryptogram += new Character((char)charCode).toString();
		}
		//return XOR product
		return cryptogram;
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

	public static String bruteForce(String cryptogram, String plaintext)
	{
		//check that we have 128 bit = 16 char key and text
//		if(cryptogram.length() != 16 || plaintext.length()  != 16)
//			return null;

		String key = "";
		//get the byte representation of the strings
		byte[] plaintextBytes = plaintext.getBytes(StandardCharsets.UTF_8);
		byte[] keyBytes = cryptogram.getBytes(StandardCharsets.UTF_8);
		//for each byte
		for (int i = 0; i < plaintext.length(); i++)
		{
			//convert to a BinaryString and pad it to length 8
			String pad = String.format("%0" + 8 + 'd', 0);

			String plainBin = Integer.toBinaryString(plaintextBytes[i]);
			plainBin = pad.substring(plainBin.length()) + plainBin;

			String cryptogramBin = Integer.toBinaryString(keyBytes[i]);
			cryptogramBin = pad.substring(cryptogramBin.length()) + cryptogramBin;
			
			String keyBin = null;
			//start the brute force approach, try everything between 0b00000000 and 0b11111111
			for (int j = 0; j < 0b11111111; j++)
			{
				keyBin = Integer.toBinaryString(j);
				keyBin = pad.substring(keyBin.length()) + keyBin;
				//if we find the correct one, stop searching
				if(XOR(cryptogramBin, keyBin).equals(plainBin))
					break;
			}
			//convert back to char and add it to the key
			int charCode = Integer.parseInt(keyBin, 2);
			key += new Character((char)charCode).toString();
		}
		return key;
	}

	public static void main(String[] args)
	{
		String key = "";
		String s =   "";
		Random rand = new Random();
		for (int i = 0; i < 1024; i++)
		{
			key +=rand.nextInt(10);
			s +=rand.nextInt(10);
		}
		System.out.println("s = " + s);
		System.out.println("key = " + key);
		String cryptogram = encrypt(key, s);
		System.out.println("cryptogram = " + cryptogram);
		System.out.println("decrypt via XOR = " + encrypt(key, cryptogram));
		final long startTime = System.currentTimeMillis();
		System.out.println("decrypt via Bruteforce = " + bruteForce(cryptogram, s));
		final long runtime = System.currentTimeMillis() - startTime;
		NumberFormat formatter = new DecimalFormat("#0.00000");
		System.out.println("Execution time for bruteforce is " + formatter.format((runtime) / 1000d) + " seconds");
		
		System.out.println("XOR plaintext and cryptogram = " + encrypt(s, cryptogram));
		System.out.println(key.length());
	}
}
