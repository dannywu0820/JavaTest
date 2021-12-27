package idv.ktw.thread;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
//import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ThreadPractice {
	public static final int NUM_OF_THREADS = 10;
	//public static Map<Integer, Integer> hashTableGlobal = new HashMap<>();
	public static Map<Integer, Integer> hashTableGlobal = Collections.synchronizedMap(new HashMap<>());
	//public static Map<Integer, Integer> hashTableGlobal = new ConcurrentHashMap<>();
	public static String absolutePath = "C:\\Users\\Danny_Wu.PFT\\eclipse-workspace\\test\\src\\main\\java\\idv\\ktw\\thread\\ProgExam\\";
	public static String filePrefix = "data";
	public static String outputPath = "C:\\Users\\Danny_Wu.PFT\\Desktop\\answer_thread";
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		List<Task> tasks = new ArrayList<>();
		for(int i = 0; i < NUM_OF_THREADS; i++) {
			tasks.add(new MeasuredTask(i));
		}
		
		for(int i = 0; i < NUM_OF_THREADS; i++) {
			tasks.get(i).start();
		}
		
		for(int i = 0; i < NUM_OF_THREADS; i++) {
			tasks.get(i).join();
		}
		
		long time = System.currentTimeMillis() - start;
		System.out.println("[" + Thread.currentThread().getName() + "] Elapsed Time: " + time);
		
		write();
	}
	
	static void write() {
		List<Entry<Integer, Integer>> l = hashTableGlobal.entrySet()
				.stream()
				.sorted(Map.Entry.<Integer, Integer>comparingByKey())
				.collect(Collectors.toList());

		l.forEach(System.out::println);
		
		File file = new File(ThreadPractice.outputPath);
		BufferedWriter bf;
		try {
			bf = new BufferedWriter(new FileWriter(file));
			
			for(Entry<Integer, Integer> entry : l) {
		           bf.write(entry.getKey() + "=" + entry.getValue());
		           bf.newLine();
				}
			bf.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}

class Task implements Runnable {
	private Thread worker;
	private Path path;
	//private ThreadLocal<Map<Integer, Integer>> hashTableLocal;

	Task(int i) {
		this.worker = new Thread(this);
		this.path = Paths.get(ThreadPractice.absolutePath + ThreadPractice.filePrefix + i);
		/*this.hashTableLocal = ThreadLocal.withInitial(() -> {
			Map<Integer, Integer> m = new HashMap<>();
			return m;
		});*/
		System.out.println("[" + this.getClass().getName() + "] Thread Created");
	}
	
	@Override
	public void run() {
		try(Stream<String> lines = Files.lines(path)) {
			lines.forEach(line -> {
				String[] tokens = line.split(",");
				Integer key = new Integer(tokens[0]);
				Integer value = new Integer(tokens[1]);
				ThreadPractice.hashTableGlobal.putIfAbsent(key, 0);
				ThreadPractice.hashTableGlobal.put(key, ThreadPractice.hashTableGlobal.get(key) + value);
				/*Map<Integer, Integer> m = this.hashTableLocal.get();
				m.putIfAbsent(key, 0);
				m.put(key, m.get(key) + value);*/
			});
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		/*synchronized(ThreadPractice.hashTableGlobal) {
			this.hashTableLocal.get().forEach((k,v) -> {
				ThreadPractice.hashTableGlobal.merge(k, v, (oldValue, newValue) -> oldValue + newValue);
			});
		}*/
	}
	
	public void start() {
		System.out.println("[" + this.getClass().getName() + "] Before Start");
		this.worker.start();
		System.out.println("[" + this.getClass().getName() + "] After Start");
	}
	
	public void join() {
		System.out.println("[" + this.getClass().getName() + "] Before Join");
		try {
			this.worker.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("[" + this.getClass().getName() + "] After Join");
	}
}

class TestTask implements Runnable {

	@Override
	public void run() {
		try{			
			int boundedRandomValue = ThreadLocalRandom.current().nextInt(1, 5);
			Thread.sleep(boundedRandomValue * 1000);
			System.out.printf("%s sleep %d sec%n", Thread.currentThread().getName(), boundedRandomValue);
		}
		catch(InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}

class MeasuredTask extends Task {

	MeasuredTask(int i) {
		super(i);
	}

	@Override
	public void run() {
		long start = System.currentTimeMillis();
		super.run();
		long time = System.currentTimeMillis() - start;
		System.out.println("[" + Thread.currentThread().getName() + "] Elapsed Time: " + time);
	}
}
