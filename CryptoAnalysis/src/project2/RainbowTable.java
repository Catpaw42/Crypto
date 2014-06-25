package project2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Hashtable;

public class RainbowTable
{
	private Hashtable<Integer, Integer> hTable;

	public RainbowTable()
	{
		this.hTable = new Hashtable<Integer, Integer>();
	}

	public RainbowTable(String filePath)
	{
		this.hTable = this.loadTable(filePath);		
	}

	@SuppressWarnings("unchecked")
	private Hashtable<Integer, Integer> loadTable(String filePath)
	{
		Hashtable<Integer, Integer> tempTable;

		FileInputStream fileIn;
		try
		{
			fileIn = new FileInputStream(filePath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			tempTable = (Hashtable<Integer, Integer>) in.readObject();
			in.close();
			fileIn.close();
			
			return tempTable;
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public void saveTable()
	{
		FileOutputStream fileOut;
		try
		{
			fileOut = new FileOutputStream("src/project2/RainbowTable2.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this.hTable);
			out.close();
			fileOut.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * Add a endpoint / startpoint as key / value to the table
	 * @param endPoint
	 * @param startPoint
	 */
	public void put(int endPoint, int startPoint)
	{
		this.hTable.put(endPoint, startPoint);
	}
	
	/**
	 * get the startpoint that belongs to this endpoint
	 * @param endPoint
	 * @return
	 */
	public int get(int endPoint)
	{
		return this.hTable.get(endPoint);
	}
	
	/**
	 * check if this endpoint exists in the table
	 * @param endPoint
	 * @return
	 */
	public boolean containsEndPoint(int endPoint)
	{
		return this.hTable.containsKey(endPoint);
	}
	
	/**
	 * check if this startpoint exists in the table
	 * @param startPoint
	 * @return
	 */
	public boolean containsStartPoint(int startPoint)
	{
		return this.hTable.containsValue(startPoint);
	}
}
