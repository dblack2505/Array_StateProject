import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Class State
 * 
 * @author Dalton Black
 * @version 2/4/2021
 *
 */
public class State {
	static NumberFormat formatter = new DecimalFormat("#0.0000");
	private static State[] a;
	private static int nElems;
	private static int max;

	private String state;
	private String capital;
	private String region;
	private int houseSeats;
	private int population;
	private int covidCase;
	private int covidDeath;
	private int houseIncome;
	private double violentCrime;

	/**
	 * Constructor to create the 'a' array and initialize it with max size. 
	 * @param maxSize - int max size of array
	 */
	public State(int maxSize) {
		this.max = maxSize;
		a = new State[max];
	}

	/**
	 * Constructor to set excel object values. 
	 * @param dataState - String state name
	 * @param dataCapital - String state capital
	 * @param dataRegion - String region
	 * @param dataSeats - int house seats
	 * @param dataPop - int population of state
	 * @param dataCovidC - int covid cases
	 * @param dataCovidD - int covid deaths
	 * @param dataIncome - int median household income
	 * @param dataCrime - double violent crime rate
	 */
	public State(String dataState, String dataCapital, String dataRegion, int dataSeats, int dataPop, int dataCovidC,
			int dataCovidD, int dataIncome, double dataCrime) {
		this.state = dataState;
		this.capital = dataCapital;
		this.region = dataRegion;
		this.houseSeats = dataSeats;
		this.population = dataPop;
		this.covidCase = dataCovidC;
		this.covidDeath = dataCovidD;
		this.houseIncome = dataIncome;
		this.violentCrime = dataCrime;
	}

	/**
	 * Below are the multiple getter functions used to get objects from the excel data and to do calculations. 
	 * @return State
	 */
	public String getState() {
		return this.state; 
	}
	public String getCapital() {
		return this.capital; 
	}
	public String getRegion() {
		return this.region; 
	}
	public int getHouseSeats() {
		return this.houseSeats; 
	}
	public int getPopulation() {
		return this.population; 
	}
	public int getCovidCase() {
		return this.covidCase; 
	}
	public int getCovidDeath() {
		return this.covidDeath; 
	}
	public int getHouseIncome() {
		return this.houseIncome; 
	}
	public double getViolentCrime() {
		return this.violentCrime; 
	}
	public double getCFR() {
		return caseRateAlg(covidDeath, covidCase);
	}
	public double getCaseRate() {
		return caseRateAlg(covidCase, population);
	}
	public double getDeathRate() {
		return caseRateAlg(covidDeath, population);
	}
	
	/**
	 * Method to pass arr[] with excel objects to 'a' array in this class.
	 * @param arr -  array with objects that is assigned to a
	 * @param nElem - int number of elements
	 */
	public void insert(State arr[], int nElem) {
		this.nElems = nElem;
		this.a = arr;
	}

	/**
	 * Displays certain rows and columns of data from the excel and some calculations on the data.
	 */
	public void display() {
		System.out.println("Name\t\t\t MHI\t VCR\t CFR\t\t Case Rate\t Death Rate");
		System.out.println("---------------------------------------------------------------------------------------");
		for (int i = 0; i < nElems; i++) {
			System.out.printf("%-8s" + "\t\t" + a[i].getHouseIncome() + "\t" + a[i].getViolentCrime()
					+ "\t " + formatter.format(a[i].getCFR()) + "\t  " + formatter.format(a[i].getCaseRate()) + "\t " + 
					formatter.format(a[i].getDeathRate()) + "\n", a[i].getState());
		}
	}

	/**
	 * Method to calculate the Case Fatality Rate.
	 * @param death - int covid deaths
	 * @param cases - int covid cases
	 * @return double CFR
	 */
	public static double cfrAlg(int death, int cases) {
		double cfr = (double) death / (double) cases;
		return cfr;
	}

	/**
	 * Method to calculate the Case Rate.
	 * @param cases - int covid cases
	 * @param population - int population of state
	 * @return double Case Rate
	 */
	public static double caseRateAlg(int cases, int population) {
		double caseRate = (double) cases / (double) population;
		return caseRate * 100000;
	}

	/**
	 * Method to calculate the Death Rate.
	 * @param death - int covid deaths
	 * @param population - int state population
	 * @return double Death Rate
	 */
	public static double deathRateAlg(int death, int population) {
		double deathRate = (double) death / (double) population;
		return deathRate * 100000;
	}

	/**
	 * Method to sort by State Name using Bubble sort.
	 */
	public static void nameSort() {
		State temp;
		for(int outer = 0; outer < nElems - 1; outer++) {
			for (int inner = nElems - 1; inner > outer; inner--) {
				if (a[inner].getState().compareTo(a[inner-1].getState()) < 0) {
					temp = a[inner];
					a[inner] = a[inner-1];
					a[inner-1] = temp;
				}
			}
		}
	}

	/**
	 * Method to sort by Case Fatality Rate using Selection Sort.
	 */
	public static void cfrSort() {
		for (int outer = 0; outer < nElems - 1; outer++) {
			int lowest = outer;
			for (int inner = outer + 1; inner < nElems; inner++) {
				if (a[inner].getCFR() < a[lowest].getCFR()) {
					lowest = inner;
				}
			}
			if (lowest != outer) {
				State temp = a[lowest];
				a[lowest] = a[outer];
				a[outer] = temp;
			}
		}
	}

	/**
	 * Method to sort by Median House Income using Insertion sort.
	 */
	public static void mhiSort() {
		int inner;
		int outer = 1;
		while (outer < nElems) {
			State temp = a[outer];
			inner = outer - 1;
			while (inner >= 0 && a[inner].getHouseIncome() > temp.getHouseIncome()) {
				a[inner + 1] = a[inner];
				inner--;
			}
			a[inner + 1] = temp;
			outer++;
		}
	}
	
	/**
	 * Method is used to determine whether to perform a Binary or Sequential search. The method creates a new temp
	 * State array (arrStateSort) that is assigned to 'a' array. Then, the new array is passed to tempStateSort() which sorts the 
	 * data. The array is passed back and a check of whether 'a' array equals 'arrStateSort'. If it does, True is 
	 * passed to main which then calls binarySearch(), otherwise sequentialSearch() is called.
	 * @return - boolean
	 */
	public boolean testSort() {
		State[] arrStateSort = new State[max];
		for (int j = 0; j < nElems; j++) {
			arrStateSort[j] = a[j];
		}
		tempStateSort(arrStateSort);
		int i = 0;
		while (i < nElems) {
			if (!a[i].getState().equals(arrStateSort[i].getState()))
				break;
			i++;
		}
		if (i == nElems)
			return true;
		else
			return false;
	}
	
	/**
	 * The binarySearch() method searches for the user's input using binary search and displays the appropriate 
	 * response based on the findings. 
	 * @param target - String userInput
	 */
	public void binarySearch(String target) {
		boolean binarySearch = false;
		int mid = 0;
		System.out.println("Binary search\n");
		int lowerBound = 0;
		int upperBound = nElems - 1;

		while (lowerBound <= upperBound) {
			mid = (lowerBound + upperBound) / 2;
			if (a[mid].getState().equals(target)) {
				binarySearch = true;
				mid = mid;
				break;
			} else if (a[mid].getState().compareTo(target) > 0) {
				upperBound = mid - 1;
			} else {
				lowerBound = mid + 1;
			}
		}
		if (binarySearch) {
			System.out.println("Name: \t\t" + a[mid].getState() + "\n" + "MHI: \t\t" + a[mid].getHouseIncome() + "\n" + "VCR: "
					+ "\t\t" + a[mid].getViolentCrime() + "\n" + "CFR: \t\t" + formatter.format(a[mid].getCFR()) + "\n" +
					"Case Rate: \t" + formatter.format(a[mid].getCaseRate()) +  "\n" + "Death Rate: \t" + 
					formatter.format(a[mid].getDeathRate()) + "\n");
		}
		else {
			System.out.println("Could not find " + target + ".\n\n");
		}
	}

	/**
	 * The sequentialSearch() method searches for the user's input using sequential search and displays the 
	 * appropriate response based on the findings. 
	 * @param target - String userInput
	 */
	public void sequentialSearch(String target) {
		System.out.println("Sequential search\n");
		int i = 0;
		while (i < nElems) {
			if (a[i].getState().equals(target))
				break;
			i++;
		}
		if (i == nElems) {
			System.out.println("Could not find " + target + ".\n");
		}
		else {
			System.out.println("Name: \t\t" + a[i].getState() + "\n" + "MHI: \t\t" + a[i].getHouseIncome() + "\n" + "VCR: "
					+ "\t\t" + a[i].getViolentCrime() + "\n" + "CFR: \t\t" + formatter.format(a[i].getCFR()) + "\n" +
					"Case Rate: \t" + formatter.format(a[i].getCaseRate()) +  "\n" + "Death Rate: \t" + 
					formatter.format(a[i].getDeathRate()) + "\n");
		}
	}


	/**
	 * Method to call sorting functions and assign temp arrays to those sorts. Then, there are 4 methods calls
	 * to boxCalc() that pass the temp arrays. There, the Spearman's correlation is applied and returned to this method
	 * that prints the results in a table.
	 */
	public static void spearmanRho() {
		State[] temp1 = new State[max];
		State[] temp2 = new State[max];
		State[] temp3 = new State[max];
		State[] temp4 = new State[max];

		mhiSort();
		for (int i = 0; i < nElems; i++) {
			temp1[i] = a[i];
		}
		caseRateSort();
		for (int j = 0; j < nElems; j++) {
			temp2[j] = a[j];
		}
		vcrSort();
		for (int k = 0; k < nElems; k++) {
			temp3[k] = a[k];
		}
		deathRateSort();
		for (int m = 0; m < nElems; m++) {
			temp4[m] = a[m];
		}
		double b1 = boxCalc(temp1, temp2);
		double b2 = boxCalc(temp2, temp3);
		double b3 = boxCalc(temp3, temp4);
		double b4 = boxCalc(temp1, temp4);

		for (int n = 0; n < 33; n++) {
			System.out.print("-");
		}
		System.out.println("");
		System.out.println("| \t    |    MHI  |  VCR    |");
		for (int n = 0; n < 33; n++) {
			System.out.print("-");
		}
		System.out.println("");
		System.out.println("| Case Rate | " + formatter.format(b1) + " | " + formatter.format(b2) + "  |");
		for (int n = 0; n < 33; n++) {
			System.out.print("-");
		}
		System.out.println("");
		System.out.println("| Case Rate | " + formatter.format(b4) + " | " + formatter.format(b3) + "  |");
		for (int n = 0; n < 33; n++) {
			System.out.print("-");
		}
		System.out.println("");
	}

	/**
	 * Method performs Spearman's Correlation algorithm on data.
	 * @param temp1 - State array of either sorted MHI, VCR, Case Rate, or Death Rate data
	 * @param temp2 - State array of either sorted MHI, VCR, Case Rate, or Death Rate data
	 * @return double Spearman's calculations
	 */
	public static double boxCalc(State[] temp1, State[] temp2) {
		int dSum = 0;
		double pSquare = 0.0;

		for (int i = 0; i <nElems; i++) {
			int j = 0;
			while (j < nElems) {
				if (temp1[i].getState().equals(temp2[j].getState()))
					break;
				j++;
			}
			dSum += Math.pow((j - i), 2);
		}
		pSquare = 1 - ((6 * dSum) / (nElems * (Math.pow(nElems, 2) - 1)));
		return pSquare;
	}

	/**
	 * Method to sort by Case Rate using Insertion sort.
	 */
	public static void caseRateSort() {
		int inner;
		int outer = 1;
		while (outer < nElems) {
			State temp = a[outer];
			inner = outer - 1;
			while (inner >= 0 && a[inner].getCaseRate() > temp.getCaseRate()) {
				a[inner + 1] = a[inner];
				inner--;
			}
			a[inner + 1] = temp;
			outer++;
		}
	}

	/**
	 * Method to sort by Violent Crime Rate using Insertion sort.
	 */
	public static void vcrSort() {
		int inner;
		int outer = 1;
		while (outer < nElems) {
			State temp = a[outer];
			inner = outer - 1;
			while (inner >= 0 && a[inner].getViolentCrime() > temp.getViolentCrime()) {
				a[inner + 1] = a[inner];
				inner--;
			}
			a[inner + 1] = temp;
			outer++;
		}
	}

	/**
	 * Method to sort by Death Rate using Insertion sort.
	 */
	public static void deathRateSort() {
		int inner;
		int outer = 1;
		while (outer < nElems) {
			State temp = a[outer];
			inner = outer - 1;
			while (inner >= 0 && a[inner].getDeathRate() > temp.getDeathRate()) {
				a[inner + 1] = a[inner];
				inner--;
			}
			a[inner + 1] = temp;
			outer++;
		}
	}

	/**
	 * Method to sort array 'arrStateSort' alphabetically using Insertion Sort.
	 * @param arrStateSort - State array
	 */
	public void tempStateSort(State[] arrStateSort) {
		int inner;
		int outer = 1;
		while (outer < nElems) {
			State temp = arrStateSort[outer];
			inner = outer - 1;
			while (inner >= 0 && arrStateSort[inner].getState().compareTo(temp.getState()) > 0) {
				arrStateSort[inner + 1] = arrStateSort[inner];
				inner--;
			}
			arrStateSort[inner + 1] = temp;
			outer++;
		}
	}
	
}