package idv.ktw.syntax.thread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadLocalPractice {
	public static void main(String[] args) {
		UserContext first = new UserContext(1);
		UserContext second = new UserContext(2);
		
		new Thread(first).start();
		new Thread(second).start();
	}
}

class Context {
	private String userName;
	
	public Context(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String value) {
		this.userName = value;
	}
}

class UserContext implements Runnable {
	//public static Map<Integer, Context> mappingIdContext = new ConcurrentHashMap<>();
	private static ThreadLocal<Context> mappingIdContext = new ThreadLocal<>();
	private Integer userId;

	UserContext(Integer userId) {
		this.userId = userId;
	}
	
	@Override
	public void run() {
		String userName = "user".concat(userId.toString());
		UserContext.mappingIdContext.set(new Context(userName));
		
		//UserContext.mappingIdContext.forEach((k, v) -> System.out.printf("k: %d, v: %s%n", k, v));
		System.out.println(UserContext.mappingIdContext.get());
	}
}