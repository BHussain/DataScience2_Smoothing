package main;

import loading.Loader;
import models.Solution;

import java.io.IOException;
import java.util.List;

public class Application {
    public static void main(String[] args) throws IOException {
        List<String[]> data = Loader.run();

        SES ses = new SES(data);

        ses.createOriginal();
        Solution solution = ses.smoothWithBestCoefficient();
        ses.draw();

        System.out.println(ses.squaredError());
        System.out.println("\nBest coefficient: " + solution.getCoefficient()
                + "\nSquared Error: " + solution.getSquaredError());
    }
}
