
public class SolutionWithJDoc {
	/** the last therm of the sum.*/
	static int maxNumber = 25003;
	/** number of figures to be used.*/
	static int nrOfFigures = 10;
	
	/** calculated value for the modulo to get the last {@link nrOfFigures} figures.*/
	static long modolovalue = (long) Math.pow(10, nrOfFigures);
	
	/** The end solution.
	 * NOTE: used primitive long - maximum ammount of {@link nrOfFigures} - 18
	 * Enough for our test.*/
	static long solution = 0;
	
	
	public static void main(String[] args) throws InterruptedException {
		// create a Ending Figures Processor
		EndingFiguresProcessor ef = new EndingFiguresProcessor(maxNumber, nrOfFigures);
		
		// for each Term in the sum, calculate the last {@link nrOfFigures} Figures.
		for (int i = 1; i<= maxNumber - 1; i++ ){
			if ((i%modolovalue) % 10 == 0) continue;
			
			long l = ef.lastFigures(i, nrOfFigures, 1, modolovalue);
			
			solution += l;
			solution %= modolovalue;
		}
		System.out.println("solution is:" +solution);
	}

	
	/**
	 * Creates the last Figures for a specific call.
	 */
	private static class EndingFiguresProcessor{
		// the last figures array \
		// lastFigures[i] contains the last Figures for i, based on other input
		long[][] lastFigures;
		// the ending values 
		int[] numberOfLastFigures;
		// the position from which cycling starts
		int[] cyclingPosition;
		
		public EndingFiguresProcessor(int numberLength, int nrOfFIgures){
			lastFigures = new long[numberLength][numberLength];
			numberOfLastFigures = new int[numberLength];
			cyclingPosition = new int[numberLength];
		}
		
		/**
		 * Return the last {@link nrOfFigures} figures for a 
		 * {@link number} to {@link number} call.
		 * 
		 * @param number - the number used to multiply.
		 * @param nrOfFigures - the number of Figures we wish to have.
		 * @param startingValue - the value from which we start/current value. // TODO: Delete this; not necessary here
		 * @param modolovalue - the value used for dividing
		 * 
		 * @return the last {@link nrOfFigures} figures for a 
		 * {@link number} to {@link number} call
		 */
		long lastFigures(int number, int nrOfFigures, int startingValue, long modolovalue){
			buildEndings(number, 1, modolovalue, 1);
			
			int actualPosition = number < cyclingPosition[number] ? 
					number : cyclingPosition[number] + ((number-cyclingPosition[number] - 1)% (numberOfLastFigures[number]-cyclingPosition[number]));
			
			return lastFigures[number][actualPosition];
		}

		/**
		 * Return the last {@link nrOfFigures} figures for all the 
		 * {@link number} to {@link number} call. This is done recursively.
		 * 
		 * @param number - the number used to multiply.
		 * @param startingValue - the value from which we start/current value. 
		 * @param modolovalue - the value used for dividing
		 * @param iterationLevel - the level of the recursive iteration.
		 * 
		 * @return the last {@link nrOfFigures} figures for a 
		 * {@link number} to {@link number} call
		 */
		private void buildEndings(int number, long startingValue, long modolovalue, 
				int iterationLevel) {
			long actualValue = startingValue * number % modolovalue;
			if (endingExistsFor(actualValue, number) || iterationLevel > number){
				return;
			}
			lastFigures[number][numberOfLastFigures[number]] = actualValue;
			numberOfLastFigures[number]++;
			buildEndings(number, actualValue, modolovalue, ++iterationLevel);
		}

		/**
		 * Checks if the actual value exists in the current lastFigures array.
		 * 
		 * @param actualValue the value to be checked.
		 * @param number the number for which the actualValue is checked.
		 * @return <code>true</code> if the actual value exists in the current lastFigures array.
		 */
		private boolean endingExistsFor(long actualValue, int number) {
			for (int i =0 ;i<=numberOfLastFigures[number]; i++){
				if (lastFigures[number][i] == actualValue){
					cyclingPosition[number] = i;
					return true;
				}
			}
			return false;
		}
	}

}
