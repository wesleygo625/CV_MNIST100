
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Algobowl {
	
	//method to receive input and convert it into an integer array
	public static int[] inValues(String fileName){
		try {
			//reads the input file
			Scanner scanner = new Scanner(new File("ALGOBOWL/all_inputs/inputs/" + fileName + ".txt"));
			//sets the length of the array to the number from the first line of the input file
			int numNums = Integer.parseInt(scanner.nextLine());
			int[] Nums = new int[numNums];
			//splits the second line into separate number strings and adds their values to the array
			String[] stringNums = scanner.nextLine().split(" ");
			for(int i = 0 ; i < stringNums.length ; i++) {
				Nums[i] = Integer.parseInt(stringNums[i]);
			}
			//returns the array of input values
			return Nums;
		}
		catch(FileNotFoundException e) {
			System.out.println("file not found");
			return null;
		}
	}
	
	//method to output the results to a new output file
	public static void outputToFile(int numSteps, ArrayList<int[]> steps, String outFile) {
		/*The following prints the output to the console instead of to a file, for debugging
		System.out.println(numSteps);
		for(int[] s : steps) {
			System.out.println(s[0] + " " + s[1]);
		}*/
		
		//generates and output file named after the corresponding input file
		File x = new File("ALGOBOWL/all_outputs/" + outFile + ".txt");
		try {
			FileWriter writer = new FileWriter("ALGOBOWL/all_outputs/" + outFile + ".txt");
			//writes the number of calculated steps to the first line of the output file
			writer.write(numSteps + "\n");
			//writes each number pair into new lines in the output file
			for(int i = 0 ; i < numSteps; i++) {
				writer.write(steps.get(i)[0] + " " + steps.get(i)[1]);
				//the last number pair will not print a newline
				if(i != numSteps - 1) {
					writer.write("\n");
				}
			}
			//closes the output file
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//method to run the algorithm on the input
	public static void runAlgorithm(int numValues, int[] values, String file) {
		int numSteps = 0;
		int targetNum;
		int currNum = 1;
		ArrayList<int[]> steps = new ArrayList<int[]>();
		ArrayList<Integer> calcedValues = new ArrayList<Integer>();
		calcedValues.add(1);
		
		//first loop iterates through the input list
		for(int i = 0; i < numValues; i++) {
			targetNum = values[i];
			
			//loop until the current value from the input list has been summed to 
			while(currNum != targetNum) {
				currNum = calcedValues.get(calcedValues.size() - 1);
				
				//loop through the calculated values and sum the selected value to the current number
				for(int j = calcedValues.size() - 1; j >= 0; j--) {
					if(currNum + calcedValues.get(j) <= targetNum) {
						calcedValues.add(currNum + calcedValues.get(j));
						
						//create an int array of size 2 containing the values for the step and add it to steps array list
						int[] step = new int[] {currNum, calcedValues.get(j)};
						steps.add(step);
						numSteps++;
						break;
					}
				}
			}
		}
		
		//call the output method
		outputToFile(numSteps, steps, file + "_OUT");
		
		return;
	}
	
	public static void main(String[] args) {
		int numValues;
		int[] values;
		String file = "input_group631";
		//open input file and get inputs
		int[] x = inValues(file);
		runAlgorithm(x.length, x, file);
		//return outputs
		
	}
	
}