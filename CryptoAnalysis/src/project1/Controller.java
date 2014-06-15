package project1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * main class for the program, controls all data.
 * @author Magnus Brandt Sløgedal
 *
 */
public class Controller implements ControllerInteface
{
	private HashMap<Character, Character> CryptogramKey;
	private char[] charSet  = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	private GUIInterface gui;
	private String plaintext;
	private String cryptogram;
	private File plaintextFile;
	
	/**
	 * Takes a single String argument to indicate where the plaintexts are.
	 * @param plaintextFilePath path to a file containing one or more single line plaintexts.
	 * @throws FileNotFoundException if no file was found at the given path.
	 */
	public Controller(String plaintextFilePath) throws FileNotFoundException
	{
		this.gui = new GUI(800, 600, this);
		this.CryptogramKey = generateNewKey();
		this.plaintextFile = new File(plaintextFilePath);
		if(!plaintextFile.exists())
			throw new FileNotFoundException("Plaintext file did'nt load");
		
		//setup first cryptogram
		this.setPlaintext("this is a test that is longer");
		gui.displayCryptogramText(this.cryptogram);
	}
	
	/**
	 * Generates a random substitution key.
	 * @return A HashMap containing the English alphabet, mapped to itself in a random sequence.
	 */
	private HashMap<Character, Character> generateNewKey()
	{
		Random rand = new Random();
		char[] alphabet = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		for (int i = 0; i < alphabet.length; i++)
		{
			int randomPosition = rand.nextInt(alphabet.length);
			// swap objects at i and randomPosition
			char temp = alphabet[i];
			alphabet[i] = alphabet[randomPosition];
			alphabet[randomPosition] = temp;
		}
		
		HashMap<Character, Character> key = new HashMap<Character, Character>();	
		for (int i = 0; i < alphabet.length; i++)
			key.put(charSet[i], alphabet[i]);
		key.put(' ', ' ');
		
		return key;
	}

	@Override
	public void setPlaintext(String text)
	{
		//format: uppercase letters
		text = text.toUpperCase();
		//format: remove anything that is'nt a letter. (\p{L} or \p{Letter}: any kind of letter from any language. Unicode Regex)
		text = text.replaceAll("[^\\p{L}\\s]+", "");
		
		this.plaintext = text;
		this.cryptogram = encrypt(text);
	}
	
	@Override
	public void pickRandomPlaintextFromFile()
	{
		try
		{
			Scanner scan = new Scanner(plaintextFile);
			int lines = 1;
			while(scan.hasNext())
			{
				scan.nextLine();
				lines++;
			}
			System.out.println(lines);
			scan.close();
			
			Random rand = new Random();
			int pick = rand.nextInt(lines);
			String plaintext = "";
			scan = new Scanner(plaintextFile);
			for (int i = 0; i < pick; i++)
			{
				plaintext = scan.nextLine();
			}
			scan.close();
			this.setPlaintext(plaintext);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Encrypts a given string using the programs substitution key.
	 * @param plaintext the text to encrypt.
	 * @return a cryptogram of the given plaintext.
	 */
	private String encrypt(String plaintext)
	{
		String encrypted = "";
		for (int i = 0; i < plaintext.length(); i++)
			encrypted += this.CryptogramKey.get(plaintext.charAt(i));
		return encrypted;
	}
}
