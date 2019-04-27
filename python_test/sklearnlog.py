import pandas as pd
import numpy as np
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import accuracy_score
from sklearn.utils import shuffle
from sklearn.preprocessing import StandardScaler


def load_data(path, header):
    wine = pd.read_csv(path, header = header)
    return wine

def get_data():
    data = load_data("breast-cancer-wisconsin.csv", None)
    data = data.replace('?', np.NaN)
    data.dropna(inplace = True)
    print(data.shape)

    x = data.iloc[:, :-1]
    y = data.iloc[:, -1]

    for i in x:
        x[i] = x[i].astype(float)

    x = shuffle(x)
    y = shuffle(y)

    split = (int) (0.8*len(x))
    other_split = len(x) - split
    x_train, y_train = x[:split], y[:split]
    x_test, y_test = x[-other_split:], y[-other_split:]


    print("x_train shape = ",x_train.shape)
    print("y_train shape = ",y_train.shape)
    print("x_test shape = ",x_test.shape)
    print("y_test shape = ",y_test.shape)

    return x_train, y_train, x_test, y_test


#first make the data
x_train, y_train, x_test, y_test = get_data()

scaler = StandardScaler().fit(x_train)
#scaler.transform(x_train)
#scaler.transform(x_test)

#now use sklearn to do logistic Regression
model = LogisticRegression(C=100)
model.fit(x_train, y_train)
predicted_classes = model.predict(x_test)
accuracy = accuracy_score(y_test,predicted_classes)
sklearn_parameters = model.coef_


								#this gives really bad results im really bad at python and sklearn

print("SkLearn Parameters = ",sklearn_parameters)
print("SkLearn Test accuracy = ",accuracy)
