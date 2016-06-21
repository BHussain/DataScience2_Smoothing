package main;

import models.Solution;
import models.SolutionSES;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.ArrayList;
import java.util.List;

public class SES {
    private List<String[]> data;
    private List<Double> smoothedValues = new ArrayList<>();
    private DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

    public SES(List<String[]> data){
        this.data = data;
    }

     public void createOriginal(){
        for(int i = 0; i < data.size(); i++) {
            dataSet.setValue(Double.parseDouble(data.get(i)[1]), "Original", data.get(i)[0]);
        }
    }

    public void smooth(double coefficient){
        smoothedValues.clear();

        double total = 0.0;
        int i;
        for(i=0; i<12; i++){
            total += Double.parseDouble(data.get(i)[1]);
        }

        double average = total/12;
        smoothedValues.add(0, average);

        // coefficient = a
        for(i = 1; i < data.size()+1; i++){
            double value = (coefficient * Double.parseDouble(data.get(i-1)[1]))
                    + (1-coefficient) * smoothedValues.get(i-1);

            smoothedValues.add(i, value);
        }
    }

    public SolutionSES smoothWithBestCoefficient(){
        SolutionSES solution = calculateBestSolution();
        List<Double> bestSolutionValues = solution.getSmoothedValues();
        for(int i = 0; i<data.size(); i++){
            dataSet.setValue(bestSolutionValues.get(i), "Smoothed", data.get(i)[0]);
        }

        List<Double> forecastedValues = new ArrayList<>();
        for(int i = 0; i<12; i++){
            double value = bestSolutionValues.get(bestSolutionValues.size() - 1);
            dataSet.setValue(value, "Smoothed", bestSolutionValues.size() + i + "");
            forecastedValues.add(value);
        }

        solution.setForecastedValues(forecastedValues);
        return solution;
    }

    public double squaredError(){
        double error = 0.0;
        for(int i= 0;i < data.size(); i++){
            error += Math.pow(smoothedValues.get(i) - Double.parseDouble(data.get(i)[1]), 2);
        }

        return Math.sqrt(error/(data.size()-1));
    }

    public SolutionSES calculateBestSolution(){
        SolutionSES bestSolution = null;
        for(double i = 0.7; i<=1.0; i+=0.1){
            smooth(i);
            double squaredError = squaredError();
            if(bestSolution==null || bestSolution.getSquaredError()>squaredError){
                bestSolution = new SolutionSES(i, squaredError, new ArrayList<>(smoothedValues));
            }
        }

        return bestSolution;
    }

    public void draw(){
        JFreeChart chart = ChartFactory.createLineChart("Smoothing", "Months", "Demand", dataSet);
        ChartFrame frame = new ChartFrame("Line Chart", chart);
        frame.setSize(1200, 800);
        frame.setVisible(true);
    }
}
