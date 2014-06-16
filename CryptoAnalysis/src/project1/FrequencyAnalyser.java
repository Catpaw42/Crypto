package project1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.FileAlreadyExistsException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to analyze  the letter frequency of a given text
 * @author Magnus Brandt Sløgedal
 *
 */
public class FrequencyAnalyser
{
	private File f;
	private char[] charSet = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	/**
	 * 
	 * @param path Path to the file that should be analyzed
	 * @throws FileNotFoundException If the given path does not match a readable file
	 */
	public FrequencyAnalyser(String path) throws FileNotFoundException
	{
		f = new File(path);
		
		if(!f.exists())
			throw new FileNotFoundException();
		
	}
	
	public void generateReport(String path) throws FileAlreadyExistsException
	{
		File newfile = new File(path);
		if(newfile.exists())
			throw new FileAlreadyExistsException("The file you try to write alreaddy exists!!");
		try
		{
			int total = this.countCharsInFile();
			int[] frequency = this.countOccurence(charSet);
			NumberFormat formatter = new DecimalFormat("#0.000");
			PrintWriter writer = new PrintWriter(path, "UTF-8");
			writer.println("Report for: " + this.f.getName());
			writer.println("-------------------------------");
			for (int i = 0; i < frequency.length; i++)
			{
				writer.println(charSet[i] + " occurs: " + frequency[i] + " times. = " + formatter.format((double)frequency[i] * 100.0 / (double)total) + "%");
			}
			writer.println("-------------------------------");
			writer.println("total length = " + total);
			writer.close();
		}
		catch (FileNotFoundException e)
		{
			System.err.println("Cant create new file");
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * Count the total number of Unicode letters that exist in this file
	 * @return The number of letters
	 * @throws FileNotFoundException If the given path does not match a readable file
	 */
	public int countCharsInFile() throws FileNotFoundException
	{
		Scanner scan = new Scanner(f);
		int totalLength = 0;
		
		//run through the file
		while(scan.hasNext())
		{
			//read the file one line at a time
			String input = scan.nextLine();
			//format: uppercase letters
			input = input.toUpperCase();
			//format: remove anything that is'nt a letter. (\p{L} or \p{Letter}: any kind of letter from any language. Unicode Regex)
			input = input.replaceAll("[^\\p{L}]+", "");
			
			//add the line's length to total length
			totalLength = totalLength +input.length();
		}
		scan.close();
		return totalLength;
	}
	
	/**
	 * counts the total number of occurrences of a given letter or n-gram (string)
	 * @param s The string to search the file for.
	 * @return The number of occurrences of the given n-gram or letter
	 * @throws FileNotFoundException If the given path does not match a readable file
	 */
	public int countOccurence(String s) throws FileNotFoundException
	{
		//make sure the string is uppercase
		s = s.toUpperCase();
		
		Scanner scan = new Scanner(f);
		int count = 0;
		String remainder = "";
		while(scan.hasNext())
		{
			String input = remainder + scan.nextLine();
			//format: uppercase letters
			input = input.toUpperCase();
			//format: remove anything that is'nt a letter. (\p{L} or \p{Letter}: any kind of letter from any language. Unicode Regex)
			input = input.replaceAll("[^\\p{L}]+", "");
			
			//create a regex patteren from the given string s
			Pattern pattern = Pattern.compile(s);
			//create a mathcher from this regex and the search string (input)
			Matcher matcher = pattern.matcher(input);
			
			//find all occurrences
			while(matcher.find())
				count++;
			//if were working on n-grams > 1 add the n-gram length - 1 to the next line
			//to avoid loosing data between line-changes
			if(s.length() > 1)
				remainder = input.substring(input.length() - (s.length() - 1), input.length());
		}
		scan.close();
		return count;
	}
	
	public int[] countOccurence(char[] alphabet) throws FileNotFoundException
	{
		int[] ocurence = new int[alphabet.length];
		for (int i = 0; i < alphabet.length; i++)
		{
			ocurence[i] = countOccurence(""+alphabet[i]);
		}
		return ocurence;
	}
	
	public char[] getCharSet()
	{
		return charSet;
	}
	public void setCharSet(char[] charSet)
	{
		this.charSet = charSet;
	}
	
	public static void main(String[] args)
	{
		try
		{
			FrequencyAnalyser freak = new FrequencyAnalyser("src/project1/Pride_and_Prejudice_by_Jane_Austen.txt");
			
			freak.generateReport("src/project1/Pride_and_Prejudice_by_Jane_Austen_REPORT.txt");
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (FileAlreadyExistsException e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
}
