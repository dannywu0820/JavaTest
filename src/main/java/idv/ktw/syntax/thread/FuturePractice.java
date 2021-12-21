package idv.ktw.syntax.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/*
 * Future is suitable for the following scenarios:
 * 1. computational intensive processes (mathematical and scientific calculations)
 * 2. manipulating large data structures (big data)
 * 3. remote method calls (downloading files, HTML scrapping, web services)
 */

public class FuturePractice {
	public static void main(String[] args) {
		//demoGetFuture();
		//demoCancelFuture();
		demoParallel();
	}
	
	static void demoGetFuture() {
		Future<Integer> future = new SquareCalculator().calculateSquare(25);
		
		try {
			while(!future.isDone()) {
				System.out.println("Calculating...");
				Thread.sleep(300);
			}
			
			Integer result = future.get(); 
			System.out.println(result);
		} 
		catch(InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	static void demoCancelFuture() {
		Future<Integer> future = new SquareCalculator().calculateSquare(25);
		int retryCount = 0;
		int retryMax = 2;
		
		while(retryCount < retryMax) {
			try {
				Integer result = future.get(1, TimeUnit.SECONDS);
				System.out.println(result);
				break;
			}
			catch(InterruptedException | ExecutionException | TimeoutException e) {
				e.printStackTrace();
				retryCount += 1;
				System.out.printf("Retry: %d%n", retryCount);
			}
		}
		
		future.cancel(true);
		if(future.isCancelled()) {
			System.out.println("Future is canceled");
		}
		else {
			System.out.println("Future is done before cancel");
		}
	}
	
	static void demoParallel() {
		SquareCalculator squareCalculator = new SquareCalculator(2);

		Future<Integer> future1 = squareCalculator.calculateSquare(5);
		Future<Integer> future2 = squareCalculator.calculateSquare(1);

		try {
			while (!(future1.isDone() && future2.isDone())) {
			    System.out.println(
			      String.format(
			        "future1 is %s and future2 is %s", 
			        future1.isDone() ? "done" : "not done", 
			        future2.isDone() ? "done" : "not done"
			      )
			    );
				Thread.sleep(300);	
			}
			
			Integer result1 = future1.get();
			Integer result2 = future2.get();
			System.out.println(result1 + " and " + result2);
			
			squareCalculator.shutdown();
		}
		catch(InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}		
	}
}

class SquareCalculator {
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	public static final int SLEEP_SECONDS = 3;
	
	SquareCalculator() {
		
	}

	SquareCalculator(int numOfThreads) {
		executor = Executors.newFixedThreadPool(numOfThreads);
	}
	
	public Future<Integer> calculateSquare(Integer value) {
		return executor.submit(() -> {
			Thread.sleep(value * 1000);
			return value * value;
		});
	}
	
	public void shutdown() {
		this.executor.shutdown();
	}
}
