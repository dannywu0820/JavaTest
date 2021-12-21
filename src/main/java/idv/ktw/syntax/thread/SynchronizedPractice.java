package idv.ktw.syntax.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SynchronizedPractice {
	private int sum = 0;
	
	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(3);
		SynchronizedPractice summation = new SynchronizedPractice();
		
		try {
			IntStream.range(0, 1000).forEach(count -> service.submit(summation::calculateSync));
			service.awaitTermination(1000, TimeUnit.MILLISECONDS);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally {
			System.out.println(summation.getSum());
		}
	}
	
	public void calculate() {
		setSum(getSum() + 1);
	}
	
	public synchronized void calculateSync() {
		setSum(getSum() + 1);
	}
	
	public int getSum() {
		return this.sum;
	}
	
	public void setSum(int value) {
		this.sum = value;
	}
}
