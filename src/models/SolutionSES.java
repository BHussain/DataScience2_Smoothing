package models;

import java.util.List;

public class SolutionSES {
    double coefficient;
    double squaredError;
    List<Double> smoothedValues;
    List<Double> forecastedValues;

    public SolutionSES(double coefficient, double squaredError, List<Double> smoothedValues){
        this.coefficient = coefficient;
        this.squaredError = squaredError;
        this.smoothedValues = smoothedValues;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }


    public double getSquaredError() {
        return squaredError;
    }

    public void setSquaredError(double squaredError) {
        this.squaredError = squaredError;
    }

    public List<Double> getSmoothedValues() {
        return smoothedValues;
    }

    public void setSmoothedValues(List<Double> smoothedValues) {
        this.smoothedValues = smoothedValues;
    }

    public List<Double> getForecastedValues() {
        return forecastedValues;
    }

    public void setForecastedValues(List<Double> forecastedValues) {
        this.forecastedValues = forecastedValues;
    }
}
