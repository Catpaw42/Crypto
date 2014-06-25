package project1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import project1.ControllerInteface.frequencyGroup;

@SuppressWarnings("serial")
public class GUI extends JFrame implements GUIInterface, ActionListener, KeyListener
{
	private ArrayList<LetterPanel> letters = new ArrayList<LetterPanel>();
	private JButton[] buttons = new JButton[5];
	private JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	private JPanel statistics = new JPanel();
	private JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private ControllerInteface controller;

	public GUI(int width, int height, ControllerInteface c)
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(width, height);
		this.setPreferredSize(new Dimension(width, height));
		this.controller = c;

		statistics.setPreferredSize(new Dimension(width / 3, height));
		statistics.setBackground(Color.GREEN);
		statistics.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.getContentPane().add(statistics, BorderLayout.EAST);

		for (int i = 0; i < buttons.length; i++)
		{
			buttons[i] = new JButton();
			buttons[i].setPreferredSize(new Dimension(width / 6, height / 20));
			buttons[i].addActionListener(this);
			controls.add(buttons[i]);
		}
		buttons[0].setText("Reset");
		buttons[1].setText("Solve");
		buttons[2].setText("Hint");
		buttons[3].setText("New Cryptogram");
		buttons[4].setText("English Frequency");

		controls.setPreferredSize(new Dimension(width ,height / 8));
		controls.setBackground(Color.GREEN);
		this.getContentPane().add(controls, BorderLayout.SOUTH);

		mainPanel.setPreferredSize(new Dimension(width * 8/10, height * 3/4));
		mainPanel.setBackground(Color.GREEN);
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);

		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.pack();
	}

	@Override
	public void displayCryptogramText(String cryptogramText)
	{
		this.letters = new ArrayList<LetterPanel>();
		this.mainPanel.removeAll();
		for (int i = 0; i < cryptogramText.length(); i++)
		{
			if(cryptogramText.charAt(i) == ' ')
			{
				JPanel panel = new JPanel();
				panel.setSize(mainPanel.getWidth() / 25, mainPanel.getHeight() / 10);
				panel.setPreferredSize(new Dimension(mainPanel.getWidth() / 25, mainPanel.getHeight() / 10));
				panel.setBackground(Color.GREEN);
				panel.setVisible(true);
				mainPanel.add(panel);
			}
			else
			{
				LetterPanel p = new LetterPanel(mainPanel.getWidth() / 25, mainPanel.getHeight() / 10, cryptogramText.charAt(i), this);
				letters.add(p);
				mainPanel.add(p);
			}
		}
		this.pack();
		this.repaint();
	}

	public void displayFrequencies(double[] frequencyArray)
	{
		String text ="<html>" + (this.buttons[4].getText().equalsIgnoreCase("English Frequency") ? "Cryptogram<br>" : "English<br>");
		NumberFormat formatter = new DecimalFormat("#0.000");
		for (int i = 0; i < frequencyArray.length; i++)
		{
			text += Controller.charSet[i] + " : " + formatter.format(frequencyArray[i]) + "%<br>";
		}
		
		this.statistics.removeAll();
		JLabel freqLabel = new JLabel();
		freqLabel.setPreferredSize(new Dimension(this.statistics.getWidth() / 2, this.statistics.getHeight()));
		freqLabel.setText(text);
		this.statistics.add(freqLabel);
		
		this.pack();
		this.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		//reset
		if(e.getSource().equals(buttons[0]))
		{
			for (int i = 0; i < letters.size(); i++)
			{
				letters.get(i).setText("");
			}
		}
		//solve
		else if(e.getSource().equals(buttons[1]))
		{
			String plaintext = this.controller.solveCryptogram();

			for (int i = 0; i < letters.size(); i++)
			{
				letters.get(i).setText(""+plaintext.charAt(i));
			}
		}
		//hint
		else if(e.getSource().equals(buttons[2]))
		{
			Random rand = new Random();
			int r = rand.nextInt(letters.size());
			String p = this.controller.getPlaintext();
			p = p.replaceAll("[^\\p{L}]+", "");
			for (int j = 0; j < letters.size(); j++)
			{
				if(letters.get(j).getCryptogramLetter().equals(letters.get(r).getCryptogramLetter()))
					letters.get(j).setText("" + p.charAt(r));
			}
		}
		//new cryptogram
		else if(e.getSource().equals(buttons[3]))
		{
			String cr = this.controller.selectNewCryptogram();
			this.displayCryptogramText(cr);
		}
		//change frequency displayed
		else if(e.getSource().equals(buttons[4]))
		{
			double[] frequencyArray;
			if(buttons[4].getText().equalsIgnoreCase("English Frequency"))
			{
				frequencyArray = this.controller.getFrequency(frequencyGroup.ENGLISH);
				buttons[4].setText("Cryptogram Frequency");
			}
			else
			{
				frequencyArray = this.controller.getFrequency(frequencyGroup.CRYPTOGRAM);
				buttons[4].setText("English Frequency");
			}
				this.displayFrequencies(frequencyArray);
		}
	}


	@Override
	public void keyPressed(KeyEvent e)
	{

	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		for (int i = 0; i < letters.size(); i++)
		{
			if(e.getSource().equals(letters.get(i).getComponent(0)))
			{
				String t = "" + e.getKeyChar();
				t = t.toUpperCase();
				for (int j = 0; j < letters.size(); j++)
				{
					if(letters.get(j).getCryptogramLetter().equals(letters.get(i).getCryptogramLetter()))
						letters.get(j).setText(t);
				}
			}		
		}

		String text = "";
		String pText = this.controller.getPlaintext();
		pText = pText.replaceAll("[^\\p{L}]+", "");
		for (int i = 0; i < letters.size(); i++)
			text += letters.get(i).getText();

		if(text.equals(pText))
			JOptionPane.showMessageDialog(new JFrame(), "You solved it!");
	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}
}
