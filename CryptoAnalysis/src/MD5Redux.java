import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5Redux
{
	public static String getMD5Hash(String text, int bitLimit)
	{
		MessageDigest md;
		
		//TODO: check that length matches criteria if not, throw error
		
		try
		{
			md = MessageDigest.getInstance("MD5");
			md.update(text.getBytes("UTF-8"));
			
			byte[] digest = md.digest();
			
			BigInteger bigInt = new BigInteger(1,digest);
			String result = bigInt.toString(16); //md5 produces hex so use radix 16
			// add leading zeroes if below length limit
			while(result.length() < 32 ) //TODO: change to length variable
			  result = "0"+result;
			return result;
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String[] args)
	{
		System.out.println(getMD5Hash("1", 20));
	}
}
