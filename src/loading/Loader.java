package loading;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class which loads in the data set and offers it as a dataset to the rest of the application.
 * 
 * @author Basit
 *
 */
public class Loader {

	public static List<String[]> run() throws IOException{
		String line = "";
	 	String splitter = ";";
	 	BufferedReader inputStream = null;
	 	List<String[]> dataSet = new ArrayList<>();
		try {
				inputStream = new BufferedReader(new FileReader("SwordForecasting.csv"));
			 	for(int i=0;i<38;i++){
			 		if(i!=0){
			 			line = inputStream.readLine();
			 			String[] data = line.split(splitter);
			 			dataSet.add(data);
			 		}
			 	}
		    } finally {
		        if (inputStream != null) {
		            inputStream.close();
		        }
		    }
		return dataSet;
	}
	
	public static void main(String[] args) throws IOException{
		List<String[]> result = Loader.run();
		for(String[] point:result){
			for(String value:point){
				System.out.println(value);
			}
			
		}
	}
}
