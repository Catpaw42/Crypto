package project2;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncFunc
{
	//the 28 bit hex challenge
	private static int challenge = 0x0A85AFC;
	
	public static int function(int s)
	{	
		byte[] sBytes = ByteBuffer.allocate(4).putInt(s).array();
		byte[] chalengeBytes = ByteBuffer.allocate(4).putInt(challenge).array();

		byte[] total = new byte[sBytes.length + chalengeBytes.length];
		
		for (int i = 0; i < sBytes.length; i++)
			total[i] = sBytes[i];
		for (int i = sBytes.length; i < total.length; i++)
			total[i] = chalengeBytes[i - sBytes.length];
		
		
		MessageDigest md;
		try
		{
			md = MessageDigest.getInstance("MD5");
			
			md.update(total);
			byte[] digest = md.digest();

			return ByteBuffer.wrap(digest).getInt();
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public static void main(String[] args)
	{
		System.out.println(Integer.toHexString(function(0x0AEEAFC)));
		System.out.println(Integer.toHexString(function(0x1A85AFC)));
		System.out.println(Integer.toHexString(function(0x0225AFC)));
	}
}
