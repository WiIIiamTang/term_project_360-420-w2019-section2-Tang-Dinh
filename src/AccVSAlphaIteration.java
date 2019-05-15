import ai.preprocessing.Dataloader;
import ai.preprocessing.FeatureScaling;
import ai.models.LogisticRegression;
import ai.metrics.ModelEvaluator;


/**
** Pls don't run this, it took jason the whole weekend just for this thing to finish.
** Computes and prints out values for the accuracy heatmap based on alpha/iterations.
*/


public class AccVSAlphaIteration
{
  public static void main (String[] args)
  {

  Dataloader data = new Dataloader();
	int time = 100;
	double[] accuracy = new double [time];
	double sum = 0.;
	double acc = 0.;

  //iterations from 1000 to 49000

  //alpha from 0.001 to 0.099

  //measure the accuracy each time, average of 100 times.

	for(int iteration = 1000; iteration < 50000; iteration += 5000){

		for(double alpha = 0.001; alpha < 0.1; alpha += 0.001){

			for (int i = 0; i < accuracy.length; i++){


				data.makeArrays("dataset/dataset_wine1.txt");
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

				classifier.fit(alpha,5000,0,false); //alpha, maxiterations, regularizationParameter, randomize intial weights or not, check for tolerance level


				double[] predictionsOnTrainSet = classifier.predictTrainSet(0.5);
				double[] predictionsOnTestSet = classifier.predictTestSet(0.5);

				accuracy[i] = ModelEvaluator.getAccuracy(y_test, predictionsOnTestSet);
				sum += accuracy[i];
			}

			acc = sum/accuracy.length;

			System.out.printf("%.5f", acc);
			System.out.print(",");
			System.out.printf("%.3f", alpha);
			System.out.print(",");
			System.out.println(iteration);

			sum = 0.;
			acc = 0.;
			accuracy = new double [time];
		}
	}
  }
}
