package main;

import loading.Loader;
import models.SolutionSES;

import java.io.IOException;
import java.util.List;

public class Application {
    public static void main(String[] args) throws IOException {
        List<String[]> data = Loader.run();

        SES ses = new SES(data);
        
        DES des = new DES(data);
        
        des.run();
        
        System.out.println("The error of the best solution: "+des.getBestSolution().getError());
        System.out.println("The smoothing coeffiecent: "+des.getBestSolution().getSmoothingCoefficient());
        System.out.println("The trend smoothing coffiecent: " + des.getBestSolution().getTrendCoefficient());
        
    }
}
