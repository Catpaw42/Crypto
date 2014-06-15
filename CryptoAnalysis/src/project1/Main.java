package project1;

import java.io.FileNotFoundException;

public class Main
{
	public static void main(String[] args)
	{
		
		ControllerInteface c = null;
		
		try
		{
			c = new Controller("src/project1/plaintexts.txt");
			System.out.println();
		}
		catch (FileNotFoundException e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		
	}
}
