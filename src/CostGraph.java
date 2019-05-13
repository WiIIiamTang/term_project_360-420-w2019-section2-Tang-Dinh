import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;

public class CostGraph{
	public static void main(String[] arg){
		String filename = "graphdata/Cost.txt";
		File file = new File(filename);
		Scanner inputFile = new Scanner(file);
		
		
		int N = 30;
		
		double[] Y = new double[N];
        double[] X = new double[N];

		int currentRow = 0;
        try
        {
            Scanner inputFile = new Scanner(file);
            while (inputFile.hasNextLine() && currentRow < N)
            {
                row = inputFile.nextLine();
				column = row.split(",");
			
				Y[currentRow] = Double.parseDouble(column[currentRow]);
				X[currentRow] = Double.parseDouble(column[currentRow][1]);

                currentRow++;
            }
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("File not found.");
        }


        ////////////////////////////////////////////////////////////////////////
        //6.  Now we can plot!
        ////////////////////////////////////////////////////////////////////////
        // Create a PlotPanel (you can use it like a JPanel)
        Plot2DPanel plot = new Plot2DPanel();

        // Define the legend position
        plot.addLegend("SOUTH");

        // Add a line plot to the PlotPanel
        plot.addLinePlot("Cost", X, Y);
		
        plot.setAxisLabel(0,"Iteration");
        plot.getAxis(0).setLabelPosition(0.5,-0.1);
        plot.setAxisLabel(1,"Cost");
        BaseLabel title = new BaseLabel("Cost Function", Color.BLACK, 0.5, 1.1);
        title.setFont(new Font("Courier", Font.BOLD, 14));
        plot.addPlotable(title);

        JFrame frame = new JFrame("Output of Cost.java");
        frame.setSize(525, 525);
        frame.setContentPane(plot);
        frame.setVisible(true);
	}
	
	public static void printArray(double[][] array){
		for(int i = 0; i < array.length; i++){
			for(int j = 0; j < array[i].length; j++){
				System.out.print(array[i][j]);
				System.out.print(",");
			}
			System.out.println();
		}
	}
}