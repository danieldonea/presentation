
public class SolutionWithMemoryDeAllocation {
	
	static int maxNumber = 15003;
	static int nrOfFigures = 10;
	
	//to solve for more than 10
	static long modolovalue = (long) Math.pow(10, nrOfFigures);
	
	
	static long solution = 0;
	
	public static void main(String[] args) throws InterruptedException {
		EndingFiguresProcessor ef = new EndingFiguresProcessor(maxNumber, nrOfFigures);
		for (int i = 1; i<= maxNumber - 1; i++ ){
			if ((i%modolovalue) % 10 == 0) continue;
			long l = ef.last10(i, nrOfFigures, 1, modolovalue);
			solution += l;
			solution %= modolovalue;
		}
		System.out.println("solution is:" +solution);
	}
	
	private static class EndingFiguresProcessor{
		long[] lastFigures;
		int numberOfLastFigures;
		int cyclingPosition = 0;
		
		public EndingFiguresProcessor(int numberLength, int nrOfFIgures){
			lastFigures = new long[numberLength];
			numberOfLastFigures = 0;
			cyclingPosition = 0;
		}
		
		long last10(int number, int nrOfFigures, int startingValue, long modolovalue){
			lastFigures = new long[number+1];
			numberOfLastFigures = 0;
			cyclingPosition = 0;
			buildEndings(number, 1, modolovalue, 1);
			int actualPosition = number < cyclingPosition ? 
					number : cyclingPosition + ((number-cyclingPosition - 1)% (numberOfLastFigures-cyclingPosition));
					//(number-startPos[number])%(values[number]-startPos[number] + 1) + startPos[number] - 1;
			return lastFigures[actualPosition];
		}

		private void buildEndings(int number, long startingValue, long modolovalue, 
				int iterationLevel) {
			long actualValue = startingValue * number % modolovalue;
			if (endingExistsFor(actualValue) || iterationLevel > number){
				return;
			}
			lastFigures[numberOfLastFigures] = actualValue;
			numberOfLastFigures++;
			buildEndings(number, actualValue, modolovalue, ++iterationLevel);
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
