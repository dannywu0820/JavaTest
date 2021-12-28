package idv.ktw.thread;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ParallelStreamPractice {
	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		
		String absolutePath = "C:\\Users\\Danny_Wu.PFT\\Downloads\\ProgExam\\";
		String filePrefix = "data";
		String outputPath = "C:\\Users\\Danny_Wu.PFT\\Desktop\\answer_parallelStream";
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
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
		
		long time = System.currentTimeMillis() - start;
		System.out.println("Time: " + time);
		writer.close();		
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
