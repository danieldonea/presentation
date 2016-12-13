package concurrentSolution;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SolutionOnThread {
	
	static int maxNumber = 15003;
	static int nrOfFigures = 4;
	
	//to solve for more than 10
	static long modolovalue = (long) Math.pow(10, nrOfFigures);
	
	public static void main(String[] args) throws InterruptedException {
		long startTime = System.nanoTime();
		EndingFiguresProcessor ef = new EndingFiguresProcessor(maxNumber, nrOfFigures, 
				SolutionAttributes.getNextNumber());
		EndingFiguresProcessor ef2 = new EndingFiguresProcessor(maxNumber, nrOfFigures, 
				SolutionAttributes.getNextNumber()); 
		EndingFiguresProcessor ef3 = new EndingFiguresProcessor(maxNumber, nrOfFigures,
				SolutionAttributes.getNextNumber());
		EndingFiguresProcessor ef4 = new EndingFiguresProcessor(maxNumber, nrOfFigures,
				SolutionAttributes.getNextNumber());
		EndingFiguresProcessor ef5 = new EndingFiguresProcessor(maxNumber, nrOfFigures,
				SolutionAttributes.getNextNumber());
		/*EndingFiguresProcessor ef6 = new EndingFiguresProcessor(maxNumber, nrOfFigures,
				SolutionAttributes.getNextNumber());
		EndingFiguresProcessor ef7 = new EndingFiguresProcessor(maxNumber, nrOfFigures,
				SolutionAttributes.getNextNumber());
		EndingFiguresProcessor ef8 = new EndingFiguresProcessor(maxNumber, nrOfFigures,
				SolutionAttributes.getNextNumber()); */

		Thread t = new Thread(ef);
	    t.start();
	    
	    Thread t2 = new Thread(ef2);
	    t2.start(); 
	    
	    Thread t3 = new Thread(ef3);
	    t3.start();
	    
	    Thread t4 = new Thread(ef4);
	    t4.start();
	    
	    Thread t5 = new Thread(ef5);
	    t5.start();
	    /*
	    Thread t6 = new Thread(ef6);
	    t6.start();
	    
	    Thread t7 = new Thread(ef7);
	    t7.start();
	    
	    Thread t8 = new Thread(ef8);
	    t8.start();*/
	    
		while(maxNumber != SolutionAttributes.presentNumber.get()){
			//System.out.println(solutionAttributes.presentNumber.get());
		}
		System.out.println("solution is:" + SolutionAttributes.solution);
		long stopTime = System.nanoTime();
		System.out.println(stopTime - startTime);
	}

	private static class SolutionAttributes{
		private static AtomicLong solution = new AtomicLong(0);
		private static AtomicInteger presentNumber = new AtomicInteger(0);
		
		synchronized static int getNextNumber(){
				return presentNumber.getAndAdd(1);
		}
		
		synchronized static void updateSolution(long addValue){
			solution.set((solution.get() + addValue) % modolovalue);
	}
	}
	
	private static class EndingFiguresProcessor implements Runnable {
		long[] lastFigures;
		int numberOfLastFigures = 0;
		int cyclingPosition = 0;
		int maxNumber;
		
		int presentNumber;
		
		public EndingFiguresProcessor(int maxNumber, int nrOfFigures, 
				int presentNumber) {
			this.maxNumber = maxNumber;
			this.presentNumber = presentNumber;
		}

		long lastFigures(int number, int nrOfFigures, int startingValue, long modolovalue){
			lastFigures = new long[number+1];
			numberOfLastFigures = 0;
			cyclingPosition = 0;
			long actualValue = startingValue * number % modolovalue;
			
			int iterationLevel = 1;
			while (!endingExistsFor(actualValue) && !(iterationLevel > number )){
				lastFigures[numberOfLastFigures] = actualValue;
				numberOfLastFigures++;
				iterationLevel++;
				
				actualValue = actualValue * number % modolovalue;
			};
			
			int actualPosition = number < cyclingPosition ? 
					number: cyclingPosition + ((number-cyclingPosition - 1)% (numberOfLastFigures-cyclingPosition));
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

		@Override
		public void run() {
			while (presentNumber < maxNumber){
				
				if ((presentNumber%modolovalue) % 10 == 0){
					presentNumber = SolutionAttributes.getNextNumber();;
					continue;
				}
				long l = lastFigures(presentNumber, nrOfFigures, 1, modolovalue);
				
				SolutionAttributes.updateSolution(l);
				presentNumber =  SolutionAttributes.getNextNumber();
			}
		}
		
	}

}
