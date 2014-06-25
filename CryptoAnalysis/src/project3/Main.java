package project3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.TimeZone;

public class Main
{
	public static void main(String[] args)
	{
		//create 2 empty calendars
		Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		Calendar c2 = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		c1.clear();
		c2.clear();
		//set 22'th
		c1.set(2009, 06, 22, 00, 00, 00);
		//set 28'th
		c2.set(2009, 06, 28, 23, 59, 59);
		
		
		
		
		long time1 =  c1.getTimeInMillis() / 1000;
		long time2 =  c2.getTimeInMillis() / 1000;
			
		System.out.println(time1);
		System.out.println(time2);
		
		
		for (long i = time1; i < time2 +1; i++)
		{
			String plaintext = decrypt("src/project3/ciphertext_sheet3.txt", generateKey(i));
			if(plaintext.contains("NSA") || plaintext.contains("Obama"))
			{
				System.out.print("key = ");
				for (int j = 0; j < 16; j++)
				{
					System.out.print(generateKey(i)[j]);
				}
				System.out.println();
				System.out.println("time = " + i);
				System.out.println("---------------------------------------------------------------");
				System.out.println(plaintext);
				break;
			}
				
		}
	}
	
	public static byte[] generateKey(long state)
	{
		byte[] key = new byte[16];
		for (int i = 0; i < 16; i++)
		{
			state = updateInnerState(state);
			key[i] = (byte) (0xFF & state);
		}
		return key;
	}
	
	public static long updateInnerState(long s)
	{
		//si = 69069 * s(i-1) + 5 mod 2^32.
		return (long)((69069 * s + 5) % (long)Math.pow(2, 32));
	}
	
	public static String decrypt(String filePath, byte[] key)
	{
		try
		{			
			Path path = Paths.get(filePath);
			byte[] fileData = Files.readAllBytes(path);
		    
		    byte[] result = new byte[fileData.length];
		    
		    for (int i = 0; i < fileData.length; i++)
				result[i] = (byte) (0xff & (fileData[i] ^ key[i % 16]));
		    
		    return new String(result, StandardCharsets.UTF_8);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
