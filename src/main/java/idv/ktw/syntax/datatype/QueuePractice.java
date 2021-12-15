package idv.ktw.syntax.datatype;

import java.util.LinkedList;
import java.util.Queue;

public class QueuePractice {
	public static void main(String[] args) {
		demoLinkedList();
		test();
	}
	
	static void test() {
		Queue<Integer> q = new LinkedList<>();
		
		// insert
		q.offer(1);
		q.add(1);
		
		// retrieve and remove
		System.out.println(q.poll());
		System.out.println(q.remove());
		
		// retrieve but doesn't remove
		System.out.println(q.peek());
		System.out.println(q.element());
	}
	
	static void demoLinkedList() {
		Queue<Request> requests = new LinkedList<>();
		offerRequestTo(requests);
        process(requests);
	}
	
	static void offerRequestTo(Queue<Request> requests) {
		for (int i = 1; i < 6; i++) {
        	boolean result = requests.offer(
            	() -> System.out.printf("處理資料 %f%n", Math.random())
            );
        	System.out.println(result);
		}
	}

    static void process(Queue<Request> requests) {
        while(requests.peek() != null) {
            Request request = requests.poll();
            request.execute();
        }
    }
}

interface Request {
	void execute();
}
