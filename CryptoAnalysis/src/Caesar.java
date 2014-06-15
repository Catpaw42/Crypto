import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Caesar
{
	public static void main(String[] args)
	{
						//GJBFW JYMJN IJXTK RFWHM
		String cipherText = "GJBFWJYMJNIJXTKRFWHM";
		
		
		Map<Character, Integer> alphabet = new HashMap<Character, Integer>();
		char[] charSet  = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		
		for (int i = 0; i < charSet.length; i++)
		{
			alphabet.put(charSet[i], i);
//			System.out.print(charSet[i] + " = ");
//			System.out.print(alphabet.get(charSet[i]));
//			System.out.println();
		}
		ArrayList<String> results = new ArrayList<String>();
		for (int j = 0; j < 26; j++)
		{
			String test = "";
			for (int i = 0; i < cipherText.length(); i++)
			{
				test +=(charSet[((alphabet.get(cipherText.charAt(i))) + j) % 25]);
			}
//			test = new StringBuilder(test).insert(test.length() - 5, " ").toString();
//			test = new StringBuilder(test).insert(test.length() - 11, " ").toString();
//			test = new StringBuilder(test).insert(test.length() - 17, " ").toString();
			results.add(test);
		}
		
		for (int i = 0; i < results.size(); i++)
		{
			System.out.print(i % 25 + " ");
			System.out.println(results.get(i));
		}
	}
			//BEVAR ETHEI DESOF MARCH
			//BEVARE THE IDES OF MARCH
}
