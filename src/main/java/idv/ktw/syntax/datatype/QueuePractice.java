package idv.ktw.syntax.datatype;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class QueuePractice {
	public static void main(String[] args) {
		demoLinkedList();
		test();
		demoStack();
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
	
	static void demoStack() {
		Stack<String> stack = new Stack<>(5);
        stack.push("Justin");
        stack.push("Monica");
        stack.push("Irene");
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
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

class Stack<E> {
	private Deque<E> deque = new ArrayDeque();
	private int capacity;
	
	Stack(int capacity) {
		this.capacity = capacity;
	}
	
	public boolean push(E elem) {
        if(isFull()) {
            return false;
        }
        return deque.offerLast(elem);
    }

    private boolean isFull() {
        return deque.size() + 1 > capacity;
    }
    
    public E pop() {
        return deque.pollLast();
    }
    
    public E peek() {
        return deque.peekLast();
    }
    
    public int size() {
        return deque.size();
    }
}
