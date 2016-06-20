package main;

import loading.Loader;
import models.RowDES;
import models.SolutionSES;

import java.io.IOException;
import java.util.List;

public class Application {
    public static void main(String[] args) throws IOException {
        List<String[]> data = Loader.run();

        //SES
        SES ses = new SES(data);
        ses.createOriginal();
        SolutionSES solution = ses.smoothWithBestCoefficient();
        System.out.println("The best smoothing factor: "+solution.getCoefficient());
        System.out.println("The error measure with the best smoothing factor: "+solution.getSquaredError());
        System.out.println("Forecasted values: ");
        solution.getForecastedValues().forEach(System.out::println);
        ses.draw();

        System.out.println("\n--------------\n");

        //DES
        DES des = new DES(data);
        des.createOriginal();
        des.run();
        System.out.println("The error of the best solution: "+des.getBestSolution().getError());
        System.out.println("The smoothing coeffiecent: "+des.getBestSolution().getSmoothingCoefficient());
        System.out.println("The trend smoothing coffiecent: " + des.getBestSolution().getTrendCoefficient());
        System.out.println("The forecasted values: ");
        List<RowDES> rows = des.getBestSolution().getData();
        for(int i=36; i<48; i++){
            System.out.println(rows.get(i).getForecast());
        }

    }
}
