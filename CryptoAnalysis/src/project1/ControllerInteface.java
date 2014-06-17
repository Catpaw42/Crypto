package project1;

public interface ControllerInteface
{
	/**
	 * sets a specific text as the plaintext (mostly for testing purposes).
	 * @param text the text to display.
	 */
	public void setPlaintext(String text);
	
	/**
	 * Picks a new randomly selected text as plaintext.
	 */
	public void pickRandomPlaintextFromFile();

	public String solveCryptogram();

	public String selectNewCryptogram();
}
