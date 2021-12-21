package idv.ktw.syntax.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SynchronizedPractice {
	private int sum = 0;
	private int sum2 = 0;
	private static int staticSum = 0;
	
	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(3);
		SynchronizedPractice summation = new SynchronizedPractice();
		
		try {
			IntStream.range(0, 1000).forEach(count -> service.submit(summation::doSyncTaskStatic));
			service.awaitTermination(1000, TimeUnit.MILLISECONDS);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally {
			System.out.println(SynchronizedPractice.staticSum);
		}
	}
	
	public void calculate() {
		setSum(getSum() + 1);
	}
	
	public synchronized void calculateSync() {
		setSum(getSum() + 1);
	}
	
	public synchronized static void calculateStatic() {
		staticSum += 1;
	} 
	
	public void doSyncTask() {
		// "this" refers to the monitor object, which is passed into sync block
		// only one thread per monitor object can execute the code inside the block
		synchronized (this) { 
			setSum(getSum() + 1);
		}
		setSum2(getSum2() + 1);
		//this.sum2 += 1;
	}
	
	public void doSyncTaskStatic() {
		synchronized (SynchronizedPractice.class) { // let class become the monitor
			staticSum += 1;
		}
	}
	
	public int getSum() {
		return this.sum;
	}
	
	public void setSum(int value) {
		this.sum = value;
	}
	
	public int getSum2() {
		return this.sum2;
	}
	
	public void setSum2(int value) {
		this.sum2 = value;
	}
}
