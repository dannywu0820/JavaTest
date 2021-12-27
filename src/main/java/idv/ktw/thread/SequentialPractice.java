package idv.ktw.thread;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SequentialPractice {
	public static void main(String[] args) {
		String absolutePath = "C:\\Users\\Danny_Wu.PFT\\eclipse-workspace\\test\\src\\main\\java\\idv\\ktw\\thread\\ProgExam\\";
		//String absolutePath = "C:\\Users\\Danny_Wu.PFT\\Downloads\\ProgExam\\";
		String filePrefix = "data";
		String outputPath = "C:\\Users\\Danny_Wu.PFT\\Desktop\\answer";
		
		Map<Integer, Integer> hashTable=  new HashMap<>();
		try {
			for(int i = 0; i < 10; i++) {
				try(Stream<String> lines = Files.lines(Paths.get(absolutePath + filePrefix + i))) {
					lines.forEach(line -> {
						String[] tokens = line.split(",");
						Integer key = new Integer(tokens[0]);
						Integer value = new Integer(tokens[1]);
						hashTable.putIfAbsent(key, 0);
						hashTable.put(key, hashTable.get(key) + value);
					});
				}	 
			}
	  
			List<Entry<Integer, Integer>> l = hashTable.entrySet()
														.stream()
														.sorted(Map.Entry.<Integer, Integer>comparingByKey())
														.collect(Collectors.toList());

			l.forEach(System.out::println);
			
			File file = new File(outputPath);
			BufferedWriter bf = new BufferedWriter(new FileWriter(file));
			
			for(Entry<Integer, Integer> entry : l) {
               bf.write(entry.getKey() + "=" + entry.getValue());
               bf.newLine();
			}
			bf.flush();	
		} 
		catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
}
