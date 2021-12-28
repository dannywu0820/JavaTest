package idv.ktw.thread;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParallelStreamPractice {
	public static void main(String[] args) {
		try {
			long start = System.currentTimeMillis();
			List<List<String[]>> resultRead = read();
			Map<Integer, Integer> resultProcess = process(resultRead);
			resultProcess.forEach((k,v) -> System.out.printf("%d=%d%n", k, v));
			long time = System.currentTimeMillis() - start;
			System.out.println("Time: " + time);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static List<List<String[]>> read() throws IOException {
		String absolutePath = "C:\\Users\\Danny_Wu.PFT\\Downloads\\ProgExam\\";
		String filePrefix = "data";
		List<List<String[]>> streams = new ArrayList<>();
		
		for(int i = 0; i < 10; i++) {
			Path path = Paths.get(absolutePath + filePrefix + i);
			try(Stream<String> lines = Files.lines(path)) {
				List<String[]> a = lines.map(str -> str.split(",")).collect(Collectors.toList());
				streams.add(a);
			}
		}		
		
		return streams;
	}
	
	static Map<Integer, Integer> process(List<List<String[]>> s) {
		Map<Integer, Integer> m = (Map<Integer, Integer>)s.stream()
			.flatMap(ele -> ele.stream())
			.collect(Collectors.toMap(ele -> Integer.valueOf(ele[0]), ele -> Integer.valueOf(ele[1]), (v1,v2) -> v1+v2));
		
		return m;
	}
}
