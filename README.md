# Time Series

An implementation of common time series calculations & algorithms in Java & H2O.

### Statistical Calculations:
- [Mean](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/util/Stats.java#L11)
- [Variance](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/util/Stats.java#L23)
- [Standard Deviation](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/util/Stats.java#L37)
- [Minimum](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/util/Stats.java#L49)
- [Maximum](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/util/Stats.java#L54)
- [Autocovariance](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/util/Stats.java#L59) 
- [Autocorrelation](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/util/Stats.java#L76)
- [Autocorrelation Function](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/util/Stats.java#L83)
- [Partial Autocorrelation Function](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/util/Stats.java#L94)

### Transformation Algorithms:
- [Box Cox Transformation](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/transform/BoxCox.java)
- [Log & Root Tranformations](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/transform/Transform.java)

### Moving Average Algorithms:
- [Simple Moving Average](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/movingaverage/SimpleMovingAverage.java)
- [Cumulative Moving Average](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/movingaverage/CumulativeMovingAverage.java)
- [Exponential Moving Average](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/movingaverage/ExponentialMovingAverage.java)

### Stationarity Tests:
- [Augmented Dickey Fuller Test](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/tests/AugmentedDickeyFuller.java) 

### Forecasting Algorithms:
- [Triple Exponential Smoothing](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/algos/TripleExpSmoothing.java)
- [Double Exponential Smoothing](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/algos/DoubleExpSmoothing.java)
- [Single Exponential Smoothing](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/algos/SingleExpSmoothing.java)

### Previous calculations conducted on an H2O Frame:
- Progress towards this is as follows:
  - Statistical Calculations:
    - [StatsFrame.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/util/StatsFrame.java)
  - Transformation Algorithms: 
    - [TransformFrame.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/transform/TransformFrame.java)
    - [BoxCoxFrame.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/transform/BoxCoxFrame.java)
  - Moving Average Algorithms:
    - [SimpleMovingAverageFrame.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/movingaverage/SimpleMovingAverageFrame.java) 
    - [CumulativeMovingAverageFrame.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/movingaverage/CumulativeMovingAverageFrame.java)
    - [ExponentialMovingAverageFrame .java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/movingaverage/ExponentialMovingAverageFrame.java)
