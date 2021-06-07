import java.io.BufferedReader;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * COP 3530: Project 1 - Array Searches and Sorts
 * <p>
 * This program reads in data from an excel sheet and provides a menu of options to choose from. Such
 * options include printing states reports, sorting, and Spearman's rho matrix.
 * <p>
 * 
 * @author Dalton Black
 * @version 2/4/2021
 *
 */
public class Project1 {

	/**
	 * Below is the main function.
	 * <p>
	 * It takes the excel file name as input, reads the data into an array, and provides a switch menu to
	 * choose different data displays/manipulations.  
	 * @param args
	 */

	public static void main(String[] args) {
		System.out.println("COP3530 Project 1");
		System.out.println("Instructor: Xudong Liu\n");
		System.out.println("Array Searches and Sorts");

		int nElem = 0;
		int maxSize = 50;
		boolean testSort = false;
		State state = new State(maxSize);
		State[] arr;
		arr = new State [maxSize];
		String[] data; //excel data
		Scanner input = new Scanner(System.in);
		String line = "";
		String path = null;

		System.out.print("Enter the file name: ");
		path = input.nextLine();
		System.out.println("");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {	
			br.readLine();
			while((line = br.readLine()) != null) {
				data = line.split(",");
				int seats = Integer.parseInt(data[3]);
				int popu = Integer.parseInt(data[4]);
				int covidC = Integer.parseInt(data[5]);
				int covidD = Integer.parseInt(data[6]);
				int houseIncome = Integer.parseInt(data[7]);
				double violentC = Double.parseDouble(data[8]);
				arr[nElem] = new State(data[0],data[1],data[2],seats,popu,covidC,covidD,houseIncome,violentC);
				nElem++;
			}
			System.out.println("There were " + nElem + " records read.\n");
			state.insert(arr, nElem);
		} catch (IOException e) {
			e.printStackTrace();
		}

		int userIn = 0;
		while(userIn != 7) {
			System.out.println("1. Print a States report");
			System.out.println("2. Sort by Name");
			System.out.println("3. Sort by Case Fatality Rate");
			System.out.println("4. Sort by Median Household Income");
			System.out.println("5. Find and print a given State");
			System.out.println("6. Print Spearman’s rho matrix");
			System.out.println("7. Quit");
			System.out.print("Please enter in your choice: ");

			try {
				userIn = input.nextInt();
				System.out.println("");
			} catch (Exception e) {
				input.next();
			}

			switch(userIn) {
			case 1:
				state.display();
				break;
			case 2: 
				state.nameSort();
				break;
			case 3: 
				state.cfrSort();
				break;
			case 4:
				state.mhiSort();
				break;
			case 5:
				Scanner in = new Scanner(System.in);
				String userInState;
				System.out.print("Please enter a state to search for: "); 
				userInState = in.nextLine();
				if (state.testSort()) 
					state.binarySearch(userInState);
				else
					state.sequentialSearch(userInState);
				break;
			case 6:
				state.spearmanRho();
				break;
			case 7: 
				System.out.println("Have a great day!");
				break;
			default:
				System.out.println("Invalid choice. Please enter 1-7\n");
				break;
			}
		}
		input.close();
	}

}
