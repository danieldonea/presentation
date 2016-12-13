import java.math.BigInteger;


public class SolutionBigInteger {
	public static void main(String[] args) throws InterruptedException {
		BigInteger result = BigInteger.ZERO;
		for (int i = 1; i <= 15000; i++) {
			BigInteger b = new BigInteger(""+i);
			result = result.add(b.pow(i));
		}
		System.out.println(result);
	}
}

