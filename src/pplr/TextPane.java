package pplr;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class TextPane 
{
	public static void makeTextPane(double[][]xTrainArray, double[]yTestArray,double[][]xTestArray, double[]yTrainArray)
	{
		JFrame frame = new JFrame("Logistic Regression");
		JTextPane pane = new JTextPane();
		JLabel label1 = new JLabel();
		
		addColoredText(pane, "Data:\n", Color.BLACK);
		addColoredText(pane, "xTrainArray has " + xTrainArray.length + " rows; xTestArray has " + xTestArray.length + " rows\n", Color.RED);
		addColoredText(pane, "yTrainArray has " + yTrainArray.length + " rows; yTrainArray has " + yTestArray.length + " rows\n",Color.BLUE);
		label1.setText("<html>This will perform a logistic<br> regression on the dataset.txt,<br> make sure the last column <br>is the y-label</html>");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pane.setPreferredSize(new Dimension(200, 200));
        pane.setEditable(false);
        frame.getContentPane().add(pane, BorderLayout.CENTER);
        frame.getContentPane().add(label1,BorderLayout.WEST);
        frame.pack();
        frame.setVisible(true);
	}
	
	public static void addColoredText(JTextPane pane, String str, Color color)
	{
		StyledDocument sDoc = pane.getStyledDocument();
		Style style = pane.addStyle("Color Styel", null);
		StyleConstants.setForeground(style, color);
		
		try
		{
			sDoc.insertString(sDoc.getLength(),str,style);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}
