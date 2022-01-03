package idv.ktw.syntax.thread;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import com.google.common.base.Stopwatch;

public class ParallelSortPractice {
	public static void main(String[] args) {	
		int[] arraySizes = {10000, 100000, 1000000, 10000000, 100000000};
        
        for(int arraySize : arraySizes ) {
            
            System.out.println("When Array size = "+arraySize);
            
            int[] intArray = new int[arraySize];
            Random random = new Random();
            
            for(int i=0; i < arraySize; i++)
                intArray[i] = random.nextInt(arraySize) + random.nextInt(arraySize);
            
            int[] forSequential = Arrays.copyOf(intArray, intArray.length);
            int[] forParallel = Arrays.copyOf(intArray, intArray.length);            
            
            
            Stopwatch timer = Stopwatch.createStarted();
            Arrays.sort(forSequential);
            timer.stop();
            System.out.println("[" + Thread.currentThread().getName() + "] Total Elapsed Time: " + timer);
            
            
            timer = Stopwatch.createStarted();
            Arrays.parallelSort(forParallel);
            timer.stop();
            System.out.println("[" + Thread.currentThread().getName() + "] Total Elapsed Time: " + timer);
            
            System.out.println("------------------------------");
        
        }
	}
}
