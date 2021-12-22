package idv.ktw.syntax.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolPractice {
	static Executor poolSingle;
	static ExecutorService poolMultiple;
	static ThreadPoolExecutor threadPoolExecutor;
	static ThreadPoolExecutor poolCached;
	static ScheduledExecutorService poolScheduled;
	
	public static void main(String[] args) {
		demoCreationOfThreadPools();
		demoExecution();
	}
	
	static void demoCreationOfThreadPools() {	
		fromExecutors();
		fromForkJoinPool();
		fromGuava();
	}
	
	static void fromExecutors() {
		// corePoolSize = 1, maximumPoolSize = 1, keepAliveTime = 0 sec
		// ideal for creating an event loop
		poolSingle = Executors.newSingleThreadExecutor();
		poolMultiple = Executors.newFixedThreadPool(2);
		threadPoolExecutor = (ThreadPoolExecutor) poolMultiple;
		// corePoolSize = 0, maximumPoolSize = Integer.MAX_VALUE, keepAliveTime = 60 sec
		poolCached = (ThreadPoolExecutor) Executors.newCachedThreadPool(); 
		poolScheduled = Executors.newScheduledThreadPool(5);
	}
	
	/*
	 * It solves a common problem of spawning multiple tasks in recursive algorithms. 
	 * We'll run out of threads quickly by using a simple ThreadPoolExecutor, 
	 * as every task or subtask requires its own thread to run.
	 * The benefit of the fork/join framework is that it does not create a new thread 
	 * for each task or subtask.
	 * */
	static void fromForkJoinPool() {
		
	}
	
	/*
	 * Google's implementations of ExecutorService.
	 * The implementing classes are not accessible for direct instantiation or subclassing, 
	 * so the only entry point for creating their instances is the MoreExecutors helper class.
	 */
	static void fromGuava() {
		
	}
	
	static void demoExecution() {
		Runnable x = new Runnable() {
			@Override
			public void run() {
				System.out.println("Hello World");
			}	
		};
		poolSingle.execute(x);
		poolSingle.execute(() -> System.out.println("Hello World"));
		
		Callable<String> y = new Callable() {
			@Override
			public String call() throws Exception {
				return "Hello World";
			}
			
		};
		Future<String> taskResult = poolMultiple.submit(y);
		poolMultiple.submit(x);
		try {
			System.out.println(taskResult.get());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < 3; i++) {
			threadPoolExecutor.submit(() -> {
				Thread.sleep(1000);
				return null;
			});
		}
		
		System.out.println(threadPoolExecutor.getPoolSize());
		System.out.println(threadPoolExecutor.getQueue().size());
		
		for(int i = 0; i < 3; i++) {
			poolCached.submit(() -> {
				Thread.sleep(1000);
				return null;
			});
		}
		
		System.out.println(((ThreadPoolExecutor)poolCached).getPoolSize());
		System.out.println(((ThreadPoolExecutor)poolCached).getQueue().size());
		// Follow Up: poolScheduled and poolMultiple refer to the same queue?
		
		// schedule method allows us to run a task once after a specified delay.
		// scheduleAtFixedRate method allows us to run a task after a specified initial delay and then run it repeatedly with a certain period. The period argument is the time measured between the starting times of the tasks, so the execution rate is fixed.
		// scheduleWithFixedDelay method is similar to scheduleAtFixedRate in that it repeatedly runs the given task, but the specified delay is measured between the end of the previous task and the start of the next. The execution rate may vary depending on the time it takes to run any given task.
		poolScheduled.schedule(x, 1000, TimeUnit.MILLISECONDS);
		
		CountDownLatch lock = new CountDownLatch(3);
		ScheduledFuture<?> future = poolScheduled.scheduleAtFixedRate(() -> {
			System.out.println("Hello World");
		    lock.countDown();
		}, 1000, 2000, TimeUnit.MILLISECONDS);
		
		try {
			lock.await(10 * 1000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		future.cancel(true);
	}
}
