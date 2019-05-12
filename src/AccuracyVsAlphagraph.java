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
    //An instance of a dataloader object is created first.
    Dataloader data = new Dataloader();

    //We'll be using the wine dataset.
    data.makeArrays(args[0]);
    data.shuffleData();
    data.trainTestSplit(0.70);

    //the dataloader object now has the training/test arrays ready. lets get them:
    double [][] x_train, x_test;
    double [] y_train, y_test;

    x_train = data.returnXTrainArray();
    x_test = data.returnXTestArray();
    y_train = data.returnYTrainArray();
    y_test = data.returnYTestArray();

    //Now we can scale them to z scores using the feature scaler:
    FeatureScaling.standardScaler(x_train, x_test);

    //Now we can do logistic regression stuff.
    //make a new classifier object with the arrays that we have:
    LogisticRegression classifier = new LogisticRegression(x_train, x_test, y_train, y_test);


    double[] predictionsOnTestSet;
    double[] accuracy = new double[1000];
    double[] stepSize = new double[1000];
    int count = 0;

    for(double alpha = 0.00001; alpha < 0.1; alpha += 0.0001)
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
