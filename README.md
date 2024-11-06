# Exponential Smoothing & Moving Average Models in Java

## Overview

This repository provides an implementation of time series forecasting methods, specifically Exponential Smoothing and Moving Average models, written in Java. These models are essential in statistical forecasting, widely used in fields such as finance, economics, and inventory management. By smoothing out short-term fluctuations, these models capture the underlying trend of a time series, aiding in effective forecasting and decision-making.

## Key Features

This repository includes a range of implementations for both exponential smoothing and moving average models, as well as supporting utilities for statistical calculations, data transformations, and stationarity testing.

### Java Implementation

Below is a summary of the core classes included in this implementation:

#### Statistical Calculations
- **[Stats.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/util/Stats.java)**: Contains utility functions for common statistical operations necessary for time series preprocessing and analysis.

#### Transformation Algorithms
- **[BoxCox.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/transform/BoxCox.java)**: Applies the Box-Cox transformation, a power transformation technique for stabilizing variance and making data more normal distribution-like.
- **[Transform.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/transform/Transform.java)**: Provides a suite of transformation methods to preprocess time series data.

#### Moving Average Algorithms
- **[SimpleMovingAverage.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/movingaverage/SimpleMovingAverage.java)**: Implements the Simple Moving Average (SMA), a straightforward technique that averages data points over a defined window, commonly used in trend analysis.
- **[CumulativeMovingAverage.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/movingaverage/CumulativeMovingAverage.java)**: Calculates the Cumulative Moving Average (CMA), which updates averages progressively over the entire dataset.
- **[ExponentialMovingAverage.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/movingaverage/ExponentialMovingAverage.java)**: Implements the Exponential Moving Average (EMA), a method that gives more weight to recent observations, making it responsive to recent changes.

#### Stationarity Tests
- **[AugmentedDickeyFuller.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/tests/AugmentedDickeyFuller.java)**: Implements the Augmented Dickey-Fuller (ADF) test to check for stationarity in a time series, which is crucial for model selection and performance.

#### Exponential Smoothing Algorithms
- **[TripleExponentialSmoothing.java](https://github.com/navdeep-G/timeseries-java/tree/master/src/main/java/algos/expsmoothing/TripleExpSmoothing.java)**: Implements Triple Exponential Smoothing, also known as Holt-Winters seasonal method, which models level, trend, and seasonality in a time series.
- **[DoubleExponentialSmoothing.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/algos/expsmoothing/DoubleExpSmoothing.java)**: Implements Double Exponential Smoothing, which accounts for trends in the data by incorporating two smoothing parameters.
- **[SingleExponentialSmoothing.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/algos/expsmoothing/SingleExpSmoothing.java)**: Implements Single Exponential Smoothing, ideal for data without a trend or seasonal component, where only one smoothing parameter is applied.

---

For questions or contributions, feel free to submit an issue or pull request in the GitHub repository.
