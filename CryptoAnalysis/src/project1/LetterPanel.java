package project1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * A class to display a letter from the cryptogram, and to recieve the plaintext guess from the user.
 * @author Magnus Brandt Sløgedal
 *
 */
@SuppressWarnings("serial")
public class LetterPanel extends JPanel implements KeyListener
{
	private JLabel cryptogramLabel;
	private JTextField plaintextField;

	/**
	 * The constructor takes a width and a height variable, plus a letter to display.
	 * @param width The desired width of the Panel.
	 * @param height The desired height of the Panel.
	 * @param letter The letter from the cryptogram to display.
	 */
	public LetterPanel(int width, int height, char letter)
	{
		cryptogramLabel = new JLabel();
		plaintextField = new JTextField();
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.setLayout(new GridLayout(2, 1));
		this.setSize(width, height);
		this.setPreferredSize(new Dimension(width, height));

		plaintextField.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		plaintextField.setHorizontalAlignment(SwingConstants.CENTER);
		plaintextField.setSize(width, height / 2);
		plaintextField.addKeyListener(this);
		this.add(plaintextField);

		cryptogramLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		cryptogramLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cryptogramLabel.setSize(width, height / 2);
		this.add(cryptogramLabel);

		this.setVisible(true);
		
		this.setCryptogramLetter(""+letter);
	}

	/**
	 * sets a new letter on the cryptogram
	 * @param letter The letter from the cryptogram to display.
	 */
	public void setCryptogramLetter(String letter)
	{
		this.cryptogramLabel.setText(letter.toUpperCase());
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if(e.getSource().equals(plaintextField))
			plaintextField.setText("");
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if(e.getSource().equals(plaintextField))
		{
			String t = plaintextField.getText();
			t = t.toUpperCase();
			plaintextField.setText(t);
		}
	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}
}
