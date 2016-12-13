public class SolutionWithoutRecursion {
	
	static int maxNumber = 15003;
	static int nrOfFigures = 4;
	
	//to solve for more than 10
	static long modolovalue = (long) Math.pow(10, nrOfFigures);
	
	
	static long solution = 0;
	
	public static void main(String[] args) throws InterruptedException {
		EndingFiguresProcessor ef = new EndingFiguresProcessor(maxNumber, nrOfFigures);
		for (int i = 1; i<= maxNumber - 1; i++ ){
			if ((i%modolovalue) % 10 == 0) continue;
			long l = ef.lastFigures(i, nrOfFigures, 1, modolovalue);
			solution += l;
			solution %= modolovalue;
		}
		System.out.println("solution is:" +solution);
	}

	
	
	private static class EndingFiguresProcessor{
		long[] lastFigures;
		int numberOfLastFigures = 0;
		int cyclingPosition = 0;
		
		public EndingFiguresProcessor(int numberLength, int nrOfFIgures){
		}
		
		long lastFigures(int number, int nrOfFigures, int startingValue, long modolovalue){
			lastFigures = new long[number+1];
			numberOfLastFigures = 0;
			cyclingPosition = 0;
			long actualValue = startingValue * number % modolovalue;
			
			int iterationLevel = 1;
			while (!endingExistsFor(actualValue) && !(iterationLevel > number)){
				lastFigures[numberOfLastFigures] = actualValue;
				numberOfLastFigures++;
				iterationLevel++;
				
				actualValue = actualValue * number % modolovalue;
			};
			
			int actualPosition = number < cyclingPosition ? 
					number : cyclingPosition + ((number-cyclingPosition - 1)% (numberOfLastFigures-cyclingPosition));
					//(number-startPos[number])%(values[number]-startPos[number] + 1) + startPos[number] - 1;
			return lastFigures[actualPosition];
		}

		private boolean endingExistsFor(long actualValue) {
			for (int i =0 ;i<=numberOfLastFigures; i++){
				if (lastFigures[i] == actualValue){
					cyclingPosition = i;
					return true;
				}
			}
			return false;
		}

	}

}
