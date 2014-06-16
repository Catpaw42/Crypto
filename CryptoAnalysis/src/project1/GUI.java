package project1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GUI extends JFrame implements GUIInterface, ActionListener
{
	ArrayList<LetterPanel> letters = new ArrayList<LetterPanel>();
	JButton[] buttons = new JButton[4];
	JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	JPanel Statistics = new JPanel();
	JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER));

	public GUI(int width, int height, ControllerInteface c)
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(width, height);
		this.setPreferredSize(new Dimension(width, height));
		
		Statistics.setPreferredSize(new Dimension(width / 3, height));
		Statistics.setBackground(Color.GREEN);
		Statistics.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.getContentPane().add(Statistics, BorderLayout.EAST);
		
		
		for (int i = 0; i < buttons.length; i++)
		{
			buttons[i] = new JButton();
			buttons[i].setPreferredSize(new Dimension(width / 6, height / 20));
			controls.add(buttons[i]);
		}
		buttons[0].setText("Reset");
		buttons[1].setText("Solve");
		buttons[2].setText("Hint");
		buttons[3].setText("New Cryptogram");
		
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
				LetterPanel p = new LetterPanel(mainPanel.getWidth() / 25, mainPanel.getHeight() / 10, cryptogramText.charAt(i));
				letters.add(p);
				mainPanel.add(p);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		
		
	}
}
