import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.utils import shuffle
from sklearn.preprocessing import StandardScaler
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import confusion_matrix
from sklearn.metrics import accuracy_score

#ok so heres a better version in python it actually works on stuff
#you can test it on wine, breast cancer, marks
iterations = 100
total_sum = 0

for i in range(0,iterations):
    #dataset = pd.read_csv("dataset/breast-cancer-wisconsin-copy.csv")
    dataset = pd.read_csv("dataset/dataset_wine1_copy.csv")
    #dataset = pd.read_csv("dataset/dataset_wine1_copy.csv")
    X = dataset.iloc[:, :-1].values
    y = dataset.iloc[:, -1]

    X, y = shuffle(X, y)

    #print(X)

    #make arrays for train, test
    X_train, X_test, y_train, y_test = train_test_split(X,y, test_size = 0.30, random_state = 0)

    #Scale
    sc_X = StandardScaler().fit(X_train)
    X_train = sc_X.transform(X_train)
    X_test = sc_X.transform(X_test)

    #evaluate
    classifier = LogisticRegression(solver ='lbfgs')
    classifier.fit(X_train,y_train)

    y_pred = classifier.predict(X_test)

    cm = confusion_matrix(y_test,y_pred)

    accuracy = accuracy_score(y_test,y_pred)
    sklearn_parameters = classifier.coef_

    total_sum += accuracy


######################################################
##print out the results
average_acc = total_sum/iterations
#print("\nParameters = ",sklearn_parameters)
print("Average test accuracy = ", average_acc)

#print("confustion matrix\n", cm)
