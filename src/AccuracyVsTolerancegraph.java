import ai.preprocessing.Dataloader;
import ai.preprocessing.FeatureScaling;
import ai.models.LogisticRegression;
import ai.metrics.ModelEvaluator;
import org.math.plot.*;
import org.math.plot.plotObjects.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;



public class AccuracyVsTolerancegraph
{
  public static void main (String[] args)
  {
    //log regression stuff first
    Dataloader data = new Dataloader();

    data.makeArrays(args[0]);
    data.shuffleData();
    data.trainTestSplit(0.70);

    double [][] x_train, x_test;
    double [] y_train, y_test;

    x_train = data.returnXTrainArray();
    x_test = data.returnXTestArray();
    y_train = data.returnYTrainArray();
    y_test = data.returnYTestArray();


    FeatureScaling.standardScaler(x_train, x_test);

    LogisticRegression classifier = new LogisticRegression(x_train, x_test, y_train, y_test);

    //measure accuracy while changing the tolerance.
    //also tracks iterations.
    double[] predictionsOnTestSet;
    double[] accuracy = new double[100];
    double[] toleranceLevel = new double[100];
    double[] numIterations = new double[100];
    int count = 0;

    for(double tolerance = 0.00000001; tolerance < 0.0001; tolerance += 0.000001)
    {
      classifier.resetWeights();

      numIterations[count] = Math.log(classifier.fit(0.001,30000, false, tolerance)); //alpha, maxiterations, randomize intial weights or not, check for tolerance level

      predictionsOnTestSet = classifier.predictTestSet(0.5);

      accuracy[count] = ModelEvaluator.getAccuracy(y_test, predictionsOnTestSet);
      toleranceLevel[count] = tolerance;

      count++;
    }

    //Create plot
    Plot2DPanel panel = new Plot2DPanel();

    panel.addLinePlot("Accuracy vs. Tolerance Level", toleranceLevel, accuracy);
    panel.setAxisLabel(0, "Tolerance");
    panel.getAxis(0).setLabelPosition(0.5,-0.1);
    panel.setAxisLabel(1, "Accuracy");
    panel.getAxis(1).setLabelPosition(-0.15,0.5);

    panel.addBarPlot("Iterations vs. Tolerance Level", toleranceLevel, numIterations);

    BaseLabel title = new BaseLabel("Accuracy vs. Tolerance Level", Color.BLUE, 0.5, 1.1);
    title.setFont(new Font("Courier", Font.BOLD, 16));
    panel.addPlotable(title);

    JFrame frame = new JFrame("Output of AccuracyVsTolerancegraph.java");
    frame.setSize(525, 525);
    frame.setContentPane(panel);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




  }

}
