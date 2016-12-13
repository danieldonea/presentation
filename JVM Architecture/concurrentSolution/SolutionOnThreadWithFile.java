package concurrentSolution;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SolutionOnThreadWithFile {
	
	static int maxNumber = 15003;
	static int nrOfFigures = 6;
	
	//to solve for more than 10
	static long modolovalue = (long) Math.pow(10, nrOfFigures);
	
	static SolutionAttributes solutionAttributes = new SolutionAttributes();
	
	private static final String FILENAME = "F:\\jtest\\numberProcessing_u6.csv";
	static File file = new File(FILENAME);
	
	static FileWriter fw; 
	static BufferedWriter bw;
	
	static {
		try {
			//init writer
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
	
	
	public static void main(String[] args) throws InterruptedException {
		//  VISUAL VM CONNCECT
		//Thread.sleep(10000);
		
		EndingFiguresProcessor ef = new EndingFiguresProcessor(maxNumber, nrOfFigures, 
				SolutionAttributes.getNextNumber());
/*		EndingFigures ef2 = new EndingFigures(maxNumber, nrOfFigures, 
				SolutionAttributes.getNextNumber()); 
		EndingFigures ef3 = new EndingFigures(maxNumber, nrOfFigures,
				SolutionAttributes.getNextNumber());
		EndingFigures ef4 = new EndingFigures(maxNumber, nrOfFigures,
				SolutionAttributes.getNextNumber());
		EndingFigures ef5 = new EndingFigures(maxNumber, nrOfFigures,
				SolutionAttributes.getNextNumber());*/

		Thread t = new Thread(ef);
	    t.start();
	    
	/*  Thread t2 = new Thread(ef2);
	    t2.start(); 
	    
	    Thread t3 = new Thread(ef3);
	    t3.start();
	    
	    Thread t4 = new Thread(ef4);
	    t4.start();
	    
	    Thread t5 = new Thread(ef5);
	    t5.start();
	    */
	    
		while(maxNumber != SolutionAttributes.presentNumber.get()){
		}
	
		//close stream
		try {
			if (bw != null)
				bw.close();
			if (fw != null)
				fw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} 
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
		volatile Boolean complete;
		
		public EndingFiguresProcessor(int maxNumber, int nrOfFigures, 
				int presentNumber) {
			this.maxNumber = maxNumber;
			this.presentNumber = presentNumber;
			this.complete = complete;
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
			
			try {
				bw.write("\n" + number +  ",");
				bw.write(""+(numberOfLastFigures));
			} catch (IOException e) {
				e.printStackTrace();
			}
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
			complete = true;
		}
		
	}

}
