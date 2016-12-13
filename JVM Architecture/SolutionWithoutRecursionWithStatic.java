public class SolutionWithoutRecursionWithStatic {
	
	static int maxNumber = 15003;
	static int nrOfFigures = 4;
	
	//to solve for more than 10
	static long modolovalue = (long) Math.pow(10, nrOfFigures);
	
	
	static long solution = 0;
	
	public static void main(String[] args) throws InterruptedException {
		for (int i = 1; i<= maxNumber - 1; i++ ){
			if ((i%modolovalue) % 10 == 0) continue;
			long l = EndingFigures.last10(i, nrOfFigures, 1, modolovalue);
			solution += l;
			solution %= modolovalue;
		}
		System.out.println("solution is:" +solution);
	}

	
	
	private static class EndingFigures{
		static long[] endings;
		static int values = 0;
		static int startPos = 0;
		
		static long last10(int number, int nrOfFigures, int startingValue, long modolovalue){
			endings = new long[number+1];
			values = 0;
			startPos = 0;
			long actualValue = startingValue * number % modolovalue;
			
			int iterationLevel = 1;
			while (!endingExistsFor(actualValue) && !(iterationLevel > number)){
				endings[values] = actualValue;
				values++;
				iterationLevel++;
				
				actualValue = actualValue * number % modolovalue;
			};
			
			int actualPosition = number < startPos ? 
					number : startPos + ((number-startPos - 1)% (values-startPos));
					//(number-startPos[number])%(values[number]-startPos[number] + 1) + startPos[number] - 1;
			return endings[actualPosition];
		}

		static private boolean endingExistsFor(long actualValue) {
			for (int i =0 ;i<=values; i++){
				if (endings[i] == actualValue){
					startPos = i;
					return true;
				}
			}
			return false;
		}
	}

}

