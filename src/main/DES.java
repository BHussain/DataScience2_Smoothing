package main;

import com.sun.javafx.css.CalculatedValue;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.ArrayList;
import java.util.List;

public class DES {
    private List<String[]> data;
    private List<Double> smoothedValues;
    private List<Double> trendValues;
    private List<Double> forecastedValues;
    private DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

    public DES(List<String[]> data){
        this.data = data;
    }

    public void smooth(double coefficient, double trendSmoothingFactor){
        smoothedValues = new ArrayList<>();
        trendValues = new ArrayList<>();

        calculateTrend(trendSmoothingFactor);
        calculateSmoothing(coefficient);

        for(int i = 0; i < data.size(); i++){
            dataSet.setValue(smoothedValues.get(i), "Smoothed", data.get(i)[0]);
        }

        for(int i = 3; i < data.size(); i++){
            double value = trendValues.get(i-1)+smoothedValues.get(i-1);
            forecastedValues.add(i-2, value);
        }
    }

    private void calculateTrend(double trendSmoothingFactor){
        for(int i=0; i<data.size();i++){
            if(trendValues.isEmpty()){
                trendValues.add(i, Double.parseDouble(data.get(2)[1])-Double.parseDouble(data.get(1)[1]));
            }

            double value = ((smoothedValues.get(i) - smoothedValues.get(i-1)) * trendSmoothingFactor)
                    + (1-trendSmoothingFactor*(trendValues.get(i-1)));

            trendValues.add(i, value);
        }
    }

    private void calculateSmoothing(double coefficient){
        double total = 0.0;
        for(int i = 0; i<12; i++){
            total += Double.parseDouble(data.get(i)[1]);
        }

        double average = total/12;

        // coefficient = a
        for(int i = 1; i<data.size(); i++){
            if(smoothedValues.isEmpty()){
                smoothedValues.add(i-1, average);
            }
            double value = (coefficient * Double.parseDouble(data.get(i-1)[1]))
                    + (1-coefficient) * (smoothedValues.get(i-1)+trendValues.get(i-1));

            smoothedValues.add(i, value);
        }
    }

    public void createOriginal(){
        for(int i = 0; i < data.size(); i++) {
            dataSet.setValue(Double.parseDouble(data.get(i)[1]), "Original", data.get(i)[0]);
        }
    }

    public void draw(){
        JFreeChart chart = ChartFactory.createLineChart("Smoothing", "Months", "Demand", dataSet);
        ChartFrame frame = new ChartFrame("Line Chart", chart);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
