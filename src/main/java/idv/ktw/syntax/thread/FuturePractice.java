package idv.ktw.syntax.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
 * Future is suitable for the following scenarios:
 * 1. computational intensive processes (mathematical and scientific calculations)
 * 2. manipulating large data structures (big data)
 * 3. remote method calls (downloading files, HTML scrapping, web services)
 */

public class FuturePractice {

}

class SquareCalculator {
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	public static final int SLEEP_SECONDS = 1;
	
	public Future<Integer> calculateSquare(Integer value) {
		return executor.submit(() -> {
			Thread.sleep(SLEEP_SECONDS * 1000);
			return value * value;
		});
	}
}
