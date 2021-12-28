package idv.ktw.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import org.checkerframework.checker.nullness.qual.Nullable;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class GuavaPractice {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		MyService s = new MyService(1);
		for(int i = 0; i < 5; i++) {
			Callable<?> t = new TaskTest(Integer.toString(i));
			s.start(t);
		}
		long time = System.currentTimeMillis() - start;
		System.out.println("Time: " + time);
	}
}

class TaskTest implements Callable<String> {
	private String data;
	
	TaskTest(String data) {
		this.data = data;
	}

	@Override
	public String call() throws Exception { 
		int seconds = ThreadLocalRandom.current().nextInt(1, 5);
		Thread.sleep(seconds * 1000);
		System.out.println(Thread.currentThread().getName() + " sleep for " + seconds + " seconds");
		return this.data;
	}
}

class MyService {
	private ExecutorService pool;
	private ListeningExecutorService service;
	private List<ListenableFuture<Object>> futures;
	private FutureCallback<Object> myCallback = new FutureCallback() {
		@Override
		public void onSuccess(@Nullable Object result) {
			System.out.println("success, result=" + result);
		}

		@Override
		public void onFailure(Throwable t) {
			t.printStackTrace();			
		}
	};
	
	MyService(int concurrency) {
		this.pool = Executors.newFixedThreadPool(concurrency);
		this.service = MoreExecutors.listeningDecorator(this.pool);
		this.futures = new ArrayList<>();
	}
	
	public void start(Callable<?> task) {
		ListenableFuture<Object> temp = (ListenableFuture<Object>) this.service.submit(task);
		Futures.addCallback(temp, this.myCallback, this.service);
		this.futures.add(temp);
	}
}
