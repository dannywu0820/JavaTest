package idv.ktw.thread;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ParallelStreamPractice {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		String absolutePath = "C:\\Users\\Danny_Wu.PFT\\Downloads\\ProgExam\\";
		String filePrefix = "data";
		int numOfThreads = 10;
		List<String> filePaths = new ArrayList<>();
		
		for(int i = 0; i < numOfThreads; i++) {
			filePaths.add(absolutePath + filePrefix + i);
		}
		
		filePaths.parallelStream()
			.map(f -> {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return Paths.get(f);
			})
			.flatMap(ThrowingFunction.wrap(Files::lines))
			.map(str -> str.split(","))
			.collect(Collectors.toMap(ele -> Integer.valueOf(ele[0]), ele -> Integer.valueOf(ele[1]), (v1,v2) -> v1 + v2))
			.forEach((k,v) -> System.out.printf("%d=%d%n", k, v));
		
		long time = System.currentTimeMillis() - start;
		System.out.println("Time: " + time);
	}
}

@FunctionalInterface
interface ThrowingFunction<T,R> extends Function<T,R> {

    @Override
    public default R apply(T t) {
        try {
            return throwingApply(t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static<T,R> Function<T,R> wrap(ThrowingFunction<T,R> f) {
        return f;
    }

    R throwingApply(T t) throws Exception;
}
