package idv.ktw.thread;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class GuavaPractice {
	public static void main(String[] args) {
		Stopwatch timer = Stopwatch.createStarted(); 
		String absolutePath = "C:\\Users\\Danny_Wu.PFT\\eclipse-workspace\\test\\src\\main\\java\\idv\\ktw\\thread\\ProgExam\\";
		String filePrefix = "data";
		MyService s = new MyService(4);
		CountDownLatch count = new CountDownLatch(10);
		for(int i = 0; i < 10; i++) {
			//Callable<?> t = new TaskCalc(absolutePath + filePrefix + Integer.toString(i));
			Callable<?> t = new TaskTest(Integer.toString(i), count);
			s.start(t);
		}
		//s.reduce();
		try {
			count.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		timer.stop();
		System.out.println("[" + Thread.currentThread().getName() + "] Total Elapsed Time: " + timer);
	}
}

abstract class TaskTimed<T> implements Callable<T> {
	private Stopwatch timer;

	abstract protected T execute();
	
	@Override
	public T call() {
		timer = Stopwatch.createStarted();
		T result = this.execute();
		timer.stop();
		System.out.println("[" + Thread.currentThread().getName() + "] Elapsed Time: " + timer);
		return result;
	}  
}

class TaskTest<String> extends TaskTimed<String> {
	private String data;
	private CountDownLatch count;
	
	TaskTest(String data, CountDownLatch count) {
		this.data = data;
		this.count = count;
	}

	@Override
	public String execute() { 
		try {
			int seconds = ThreadLocalRandom.current().nextInt(1, 5);
			Thread.sleep(seconds * 1000);
			System.out.println(Thread.currentThread().getName() + " sleep for " + seconds + " seconds");
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			this.count.countDown();
		}
		return this.data;
	}
}

class TaskCalc<Map> extends TaskTimed<Map> {
	private Path filePath;
	
	TaskCalc(String path) {
		filePath = Paths.get(path);
	}

	@Override
	public Map execute() {
		Map result = (Map) new HashMap<Integer, Integer>();
		try {
			result = (Map) Files.lines(this.filePath)
				.map(str -> str.split(","))
				.collect(Collectors.toMap(ele -> Integer.valueOf(ele[0]), ele -> Integer.valueOf(ele[1]), (v1,v2) -> v1 + v2));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Thread.sleep(1000);
		
		return result;
	}
}

class MyService {
	private ExecutorService pool;
	private ListeningExecutorService service;
	private List<ListenableFuture<Object>> futures;
	private FutureCallback<Object> myCallback = new FutureCallback() {
		@Override
		public void onSuccess(@Nullable Object result) {
			System.out.println("[" + Thread.currentThread().getName() + "] onSuccess");
			//((Map) result).forEach((k,v) -> System.out.printf("%d=%d%n", k, v));
		}

		@Override
		public void onFailure(Throwable t) {
			System.out.println("[" + Thread.currentThread().getName() + "] onFailure");
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
	
	public void reduce() {
		try {
			String outputPath = "C:\\Users\\Danny_Wu.PFT\\Desktop\\answer_guava";
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
			
			List<Object> l = Futures.allAsList(this.futures).get();
			l.stream()
	        .flatMap(m -> ((Map<Integer, Integer>) m).entrySet().stream())
	        .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingInt(Map.Entry::getValue)))
	        .entrySet().stream()
			.map(e -> e.getKey() + "=" + e.getValue())
			.forEach(str -> {
				try {
					writer.write(str + "\r\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			writer.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
