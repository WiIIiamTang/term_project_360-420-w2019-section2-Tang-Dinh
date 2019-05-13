import ai.preprocessing.Dataloader;
import ai.preprocessing.FeatureScaling;
import ai.models.LogisticRegression;
import ai.metrics.ModelEvaluator;
import org.math.plot.*;
import org.math.plot.plotObjects.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;



public class ROCgraph
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
    //We'll fit this using gradient descent:
    classifier.fit(0.001,1000, false); //alpha, maxiterations, randomize intial weights or not, check for tolerance level

    double[] rocVars;
    double[] predictionsOnTestSet;
    double[] recall = new double[100];
    double[] fpr = new double[100];
    int count = 0;

    for(double threshold = 0.0; threshold < 1.0; threshold += 0.01)
    {
      predictionsOnTestSet = classifier.predictTestSet(threshold);

      rocVars = getROCvariables(y_test, predictionsOnTestSet);

      recall[count] = rocVars[0];
      fpr[count] = rocVars[1];

      count++;
    }

    //Create plot
    Plot2DPanel roc = new Plot2DPanel();

    roc.addLinePlot("ROC curve", fpr, recall);
    roc.setAxisLabel(0, "False Positive Rate");
    roc.getAxis(0).setLabelPosition(0.5,-0.1);
    roc.setAxisLabel(1, "Recall");
    roc.getAxis(1).setLabelPosition(-0.15,0.5);

    BaseLabel title = new BaseLabel("ROC curve for Logistic Regression", Color.BLUE, 0.5, 1.1);
    title.setFont(new Font("Courier", Font.BOLD, 16));
    roc.addPlotable(title);

    JFrame frame = new JFrame("Output of ROCgraph.java");
    frame.setSize(525, 525);
    frame.setContentPane(roc);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




  }

  public static double[] getROCvariables(double[] yLabels, double[] predictedYLabels)
  {
    int tp = 0; //true positives
    int fp = 0; //false positives
    int actualZero = 0;
    int actualOne = 0;
    int predictedOne = 0;

    double[] rocVar = new double[2];

    for(int i = 0; i < predictedYLabels.length; i++)
    {
      //true positive
      if ((yLabels[i] == predictedYLabels[i]) && (yLabels[i] == 1))
      {
        tp++;
        actualOne++;
      }
      //true negatives
      else if ((yLabels[i] == predictedYLabels[i]) && (yLabels[i] == 0))
      {
        actualZero++;
      }
      //false positives
      else if ((yLabels[i] == 0) && (predictedYLabels[i] == 1))
      {
        fp++;
        actualZero++;
      }
      //false negatives
      else if ((yLabels[i] == 1) && (predictedYLabels[i] == 0))
      {
        actualOne++;
      }
    }

    rocVar[0] = (double)tp/actualOne;
    rocVar[1] = (double)fp/actualZero;

    return rocVar;

  }
}
