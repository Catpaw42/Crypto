package project1;

public interface ControllerInteface
{
	public String solveCryptogram();

	public String selectNewCryptogram();

	public String getPlaintext();

	public double[] getFrequency(frequencyGroup fg);
	
	public enum frequencyGroup{CRYPTOGRAM, ENGLISH}
}
