
public class SampleWithGCCall {
	
	static int maxNumber = 10003;
	static int nrOfFigures = 2;
	
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
		long[][] lastFigures;
		int[] numberOfLastFigures;
		int[] cyclingPosition;
		
		public EndingFiguresProcessor(int numberLength, int nrOfFIgures){
			lastFigures = new long[numberLength][numberLength];
			numberOfLastFigures = new int[numberLength];
			cyclingPosition = new int[numberLength];
		}
		
		long lastFigures(int number, int nrOfFigures, int startingValue, long modolovalue){
			buildEndings(number, 1, modolovalue, 1);
			int actualPosition = number < cyclingPosition[number] ? 
					number : cyclingPosition[number] + ((number-cyclingPosition[number] - 1)% (numberOfLastFigures[number]-cyclingPosition[number]));
			System.gc(); //!!!
			return lastFigures[number][actualPosition];
		}

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
