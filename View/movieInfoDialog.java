package view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class movieInfoDialog {
	
	public movieInfoDialog(JFrame parentFrame,String movieContents,String movieTitle) 
	{
		JOptionPane.showMessageDialog(parentFrame, movieContents,movieTitle,JOptionPane.INFORMATION_MESSAGE);
	}
}