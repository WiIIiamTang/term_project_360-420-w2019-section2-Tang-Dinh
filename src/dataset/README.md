# Read me before changing anything here

If you want to change the dataset, there's a couple things to keep in mind:

1. the ***last column*** of your data set is reserved for the y-array, or the class labels

2. When you use LogisticRegression it will predict for labels 0 and 1, so make sure the y-labels are properly defined beforehand. If not, you could also use the makeYLabels method.

3. make sure there are no missing values in the dataset


## Notes

- wine1vs2: 1 is wine 1, 0 is wine 2
- wine1vs3: 1 is wine 1, 0 is wine 3
- wine2vs3: 1 is wine 2, 0 is wine 3
