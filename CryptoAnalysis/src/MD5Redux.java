import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;


public class MD5Redux
{
	public static int getMD5Hash(int hexInput, int bitSizeLimiter)
	{
		try
		{
			//calculate the limit to use as a bit-mask
			int mask = (int) (Math.pow(16, (bitSizeLimiter / 4))-1);
			//use the mask to set any bits above the wanted limit to 0.
			hexInput = mask & hexInput;
			
			//get the byte value 			   4 bytes for a 32-bit integer
			byte[] bytes = ByteBuffer.allocate(4).putInt(hexInput).array();
			
			//create a MD5 digest
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(bytes);
			byte[] digest = md.digest();

			//get the integer value
			int result = ByteBuffer.wrap(digest).getInt();
			
			//use the mask again to limit the output
			result = mask & result;
			return result;
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int getMD5Hash(int hexInput, int i, int bitSizeLimiter)
	{
		int temp = getMD5Hash(hexInput, bitSizeLimiter);
		temp = temp ^ i;
		return temp;
	}

	public static void main(String[] args)
	{
		assignment17();
	}
	
	private static void assignment17()
	{
		Random rand = new Random();
		
		int loopCount = (int)Math.pow(2, 16);	//how many chains
		int chainLength = (int)Math.pow(2, 8);	//length of chain
		int keySpace = (int)Math.pow(2, 20);	//key space
		
		//for now I'm storing start / end in arraylists.
		ArrayList<Integer> startPoints = new ArrayList<Integer>();
		ArrayList<Integer> endpoints = new ArrayList<Integer>();
		
		//to get random without repetition, I remove random elements from an ArrayList that covers keyspace
		ArrayList<Integer> keys = new ArrayList<Integer>();
		for (int i = 0; i < keySpace; i++)
			keys.add(i);
		
		//used to count the total number of nodes checked
		boolean[] taken = new boolean[keySpace]; 
		ArrayList<Double> checked = new ArrayList<Double>();
		
		for (int i = 0; i < loopCount; i++)
		{
			//pick a random number
			int pick = keys.remove(rand.nextInt(keys.size()));
			startPoints.add(pick);
			//run a chain, no checks for repetition
			for (int j = 0; j < chainLength; j++)
			{
				pick = getMD5Hash(pick, j, 20);
				taken[pick] = true;
			}
			endpoints.add(pick);
			
			//take samples every 100 chains
			if(i % 100 == 0)
			{
				int count = 0;
				for (int k = 0; k < taken.length; k++)
					if(taken[k])
						count++;
				
				checked.add((double)count / (double)taken.length);
			}
		}
		NumberFormat formatter = new DecimalFormat("#0.000");
		for (int i = 0; i < checked.size(); i++)
			System.out.println(formatter.format(checked.get(i)* 100) + "%");
	}
	

	private static void assignment15()
	{
		Random rand = new Random();
		
		int loopCount = (int)Math.pow(2, 16);	//how many chains
		int chainLength = (int)Math.pow(2, 8);	//length of chain
		int keySpace = (int)Math.pow(2, 20);	//key space
		
		//for now I'm storing start / end in arraylists.
		ArrayList<Integer> startPoints = new ArrayList<Integer>();
		ArrayList<Integer> endpoints = new ArrayList<Integer>();
		
		//to get random without repetition, I remove random elements from an ArrayList that covers keyspace
		ArrayList<Integer> keys = new ArrayList<Integer>();
		for (int i = 0; i < keySpace; i++)
			keys.add(i);
		
		//used to count the total number of nodes checked
		boolean[] taken = new boolean[keySpace]; 
		ArrayList<Double> checked = new ArrayList<Double>();
		
		for (int i = 0; i < loopCount; i++)
		{
			//pick a random number
			int pick = keys.remove(rand.nextInt(keys.size()));
			startPoints.add(pick);
			//run a chain, no checks for repetition
			for (int j = 0; j < chainLength; j++)
			{
				pick = getMD5Hash(pick, 20);
				taken[pick] = true;
			}
			endpoints.add(pick);
			
			//take samples every 100 chains
			if(i % 100 == 0)
			{
				int count = 0;
				for (int k = 0; k < taken.length; k++)
					if(taken[k])
						count++;
				
				checked.add((double)count / (double)taken.length);
			}
		}
		
		NumberFormat formatter = new DecimalFormat("#0.000");
		for (int i = 0; i < checked.size(); i++)
			System.out.println(formatter.format(checked.get(i)* 100) + "%");
	}
}
