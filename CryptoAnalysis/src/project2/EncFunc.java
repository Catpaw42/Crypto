package project2;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncFunc
{
	//the 28 bit hex challenge
	private static final int challenge = 0xEA85AFC;
	
	/**
	 * Represents the key's funktion, takes a chalenge and computes a response based on its internal secret.
	 * @param u the challenge.
	 * @return the response based on the MD5 function.
	 */
	public static int FOBfunktion(int u /* must be 28bit */)
	{	
		int fobSecret = 0xFF12A8F;
		
		//concat into one long (s||challenge) chalenge.length() = 28
		long total = ((long)fobSecret << 28) | u;
		
		//get the respective byte values
		byte[] bytes = ByteBuffer.allocate(8).putLong(total).array();
		
		MessageDigest md;
		try
		{
			md = MessageDigest.getInstance("MD5");
			
			md.update(bytes);
			byte[] digest = md.digest();
			
			//mask the result to 28bit
			return (int) (ByteBuffer.wrap(digest).getLong() & 0xFFFFFFFL);
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Used to compute the Rainbowtable.
	 * @param s The secret to test
	 * @return the result based on the MD5 hash.
	 */
	public static int funktion(int s /* must be 28bit */)
	{	
		//concat into one long (s||challenge) chalenge.length() = 28
		long total = ((long)s << 28) | challenge;
		
		//get the respective byte values
		byte[] bytes = ByteBuffer.allocate(8).putLong(total).array();
		
		MessageDigest md;
		try
		{
			md = MessageDigest.getInstance("MD5");
			
			md.update(bytes);
			byte[] digest = md.digest();
			
			//mask the result to 28bit
			return (int) (ByteBuffer.wrap(digest).getLong() & 0xFFFFFFFL);
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int funktion(int s, int i)
	{
		int result = (funktion(s) + i) % 0xFFFFFFF;
		return result;
	}
}
