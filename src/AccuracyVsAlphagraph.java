import ai.preprocessing.Dataloader;
import ai.preprocessing.FeatureScaling;
import ai.models.LogisticRegression;
import ai.metrics.ModelEvaluator;
import org.math.plot.*;
import org.math.plot.plotObjects.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;



public class AccuracyVsAlphagraph
{
  public static void main (String[] args)
  {
    //The regular logistic regression stuff is done:
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


    //Now we change the alpha while we measure the accuracy each time.
    double[] predictionsOnTestSet;
    double[] accuracy = new double[100];
    double[] stepSize = new double[100];
    int count = 0;

    for(double alpha = 0.0001; alpha < 0.1; alpha += 0.001)
    {
      classifier.resetWeights();

      classifier.fit(alpha,1000, false); //alpha, maxiterations, randomize intial weights or not, check for tolerance level

      predictionsOnTestSet = classifier.predictTestSet(0.5);

      accuracy[count] = ModelEvaluator.getAccuracy(y_test, predictionsOnTestSet);
      stepSize[count] = alpha;

      count++;
    }

    //Create plot
    Plot2DPanel panel = new Plot2DPanel();

    panel.addLinePlot("Accuracy vs. alpha", stepSize, accuracy);
    panel.setAxisLabel(0, "alpha");
    panel.getAxis(0).setLabelPosition(0.5,-0.1);
    panel.setAxisLabel(1, "Accuracy");
    panel.getAxis(1).setLabelPosition(-0.15,0.5);

    BaseLabel title = new BaseLabel("Accuracy vs. alpha(step size)", Color.BLUE, 0.5, 1.1);
    title.setFont(new Font("Courier", Font.BOLD, 16));
    panel.addPlotable(title);

    JFrame frame = new JFrame("Output of AccuracyVsAlphagraph.java");
    frame.setSize(525, 525);
    frame.setContentPane(panel);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




  }

}
