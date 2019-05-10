import ai.preprocessing.Dataloader;
import ai.preprocessing.FeatureScaling;
import ai.models.LogisticRegression;
import ai.metrics.ModelEvaluator;


/**
** Machine learning program - Logistic Regression
** This shows how we can classify a dataset with logistic regression with a few machine learning tools we made
**
** authors: William Tang and Jason Dinh
** check the github(@WiIIiamTang) for full documentation on the classes
*/


public class AccVSAlpha&Iteration
{
  public static void main (String[] args)
  {
    //An instance of a dataloader object is created first.
    Dataloader data = new Dataloader();
	int time = 50;
	double[] accuracy = new double [time];
	double sum = 0.;
	double acc = 0.;
	
	for(int iteration = 1000; iteration < 50000; iteration += 5000){
		
		for(double alpha = 0.001; alpha < 0.100; alpha += 0.005){
			
			for (int i = 0; i < accuracy.length; i++){
					
				//We'll be using the wine dataset.
				data.makeArrays("dataset/dataset_wine1.txt");
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

				/////////////note
				//if you didnt scale the features(which is ok too) you don't even need to create more arrays in your main class.
				//just use the data.return* methods to get your arrays

				//We'll fit this using gradient descent:
				classifier.fit(alpha,5000,0,false); //alpha, maxiterations, regularizationParameter, randomize intial weights or not, check for tolerance level

				//Now we can get the predictions with the trained model:
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

		/*
				//last step - lets get some way of evaluating the accuracy of the model:
				double acc1 = 0;
				double acc2 = 0;

				acc1 = ModelEvaluator.getAccuracy(y_train, predictionsOnTrainSet);
				System.out.println();
				acc2 = ModelEvaluator.getAccuracy(y_test, predictionsOnTestSet);

				//Print out the results
				System.out.println("Model finished with " + acc1 + " accuracy on the training set.");
				System.out.println("It got " + acc2 + " accuracy on the test set.");
				ModelEvaluator.confusionMatrix(y_test, predictionsOnTestSet);
		*/
		}
	}
  }
}
