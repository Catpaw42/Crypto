package project2;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;

public class Main
{
	public static void main(String[] args)
	{
//		generateTable();
		
		for (int i = 10 ; i >= 0; i--)
		{
			int l = 0;
			for (int j = i; j < 10; j++)
			{
				System.out.print((i - j));
			}
			System.out.println();
		}
		
//		breakCode();

	}
	
	private static void breakCode()
	{
		final int challenge = 0xEA85AFC;
		int FOBResponse = EncFunc.FOBfunktion(challenge);
		
		RainbowTable rainbow = new RainbowTable("src/project2/RainbowTable2.ser");
		
		int chainLength = (int)Math.pow(2, 10);	//length of chain
		
		for (int i = chainLength - 1; i >= 0; i--)
		{
			int u = FOBResponse;
			for (int j = i; j < chainLength - 1; j++)
			{
				u = EncFunc.funktion(u, i - j);
			}
			if(rainbow.containsEndPoint(u))
			{
				System.out.println("found endpoint: u = " + u);
				break;
			}
		}
		
	}

	private static void generateTable()
	{
		RainbowTable rainbow = new RainbowTable();
		
		int keySpace = (int)Math.pow(2, 28);	//key space
		int chainLength = (int)Math.pow(2, 10);	//length of chain
		int tableSize = (int)Math.pow(2, 18);	//how many chains

		//to get random without repetition, I remove random elements from an ArrayList that covers keyspace
		Random rand = new Random();

		//used to count the total number of nodes checked
		boolean[] taken = new boolean[keySpace]; 
		ArrayList<Double> checked = new ArrayList<Double>();

		System.out.println("starting table loop");
		for (int i = 0; i < tableSize; i++)
		{
			
			int r = 0;
			while(rainbow.containsStartPoint(r))
				r = rand.nextInt(tableSize);
			int startPoint = r;
			int current = startPoint;

			//run a chain, no checks for repetition
			for (int j = 0; j < chainLength; j++)
			{
				current = EncFunc.funktion(current, j);
				taken[current] = true;
			}
			//store endpoint/startpoint, as key/value
			rainbow.put(current, startPoint);

			//take samples every 1000 chains
			if(i % 2000 == 0)
			{
				System.out.println("counting: " + i + "/" + tableSize);
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

		rainbow.saveTable();
	}
}
