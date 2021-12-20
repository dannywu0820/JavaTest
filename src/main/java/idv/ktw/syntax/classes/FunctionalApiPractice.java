package idv.ktw.syntax.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/*
 * Optional API
 */

public class FunctionalApiPractice {
	private static final long MEGABYTE = 1024L * 1024L;

    public static long bytesToMegabytes(long bytes) {
        return bytes / MEGABYTE;
    }
    
	public static void main(String[] args) throws Exception {
		demoWithoutOptional();
		demoWithOptional();
	}
	
	static void profile(Callable func) throws Exception {
		Runtime runtime = Runtime.getRuntime();
		long startTime = System.currentTimeMillis();
		
		func.call();
        
        runtime.gc();
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used memory is bytes: " + memory);
        System.out.println("Used memory is megabytes: " + bytesToMegabytes(memory));
		
		long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime);
	}
	
	// follow up: responsibility of dealing with null: api designer or clients that use api
	static void demoWithoutOptional() {
		String x = getNickNameStr("Nick");
		if(x == null) {
			x = "default value";
		}
	}
	
	static void demoWithOptional() {
		Optional<String> opt = getNickNameOpt("Nick");
		System.out.println(opt.orElse("default value"));
		System.out.println(opt.orElseGet(() -> "default value by supplier"));
		System.out.println(opt.orElseThrow(() -> new NoSuchElementException()));
		String x = opt.get();		
		System.out.println(x);
	}
	
	static String getNickNameStr(String name) {
		Map<String, String> m = new HashMap<> ();
		m.put("Danny", "Dog");
		m.put("Jason", "Cat");
		m.put("Frank", "Fox");
		
		return m.get(name);
	}
	
	static Optional<String> getNickNameOpt(String name) {
		Map<String, String> m = new HashMap<> ();
		m.put("Danny", "Dog");
		m.put("Jason", "Cat");
		m.put("Frank", "Fox");
		
		/*
		 * i.e., String nickName = m.get(name); 
		 * return nickName == null ? Optional.empty() : Optional.of(nickName);
		 * there are 3 methods to create an Optional instance
		 * Optional.empty(): return an empty Optional instance
		 * Optional.of(): throw NullPointerException if the input is Null 
		 * */
		return Optional.ofNullable(m.get(name));
	}
}

class P {
	private String name;
	private Integer age;
	private Gender gender;
	
	
	P(String name, Integer age, Gender gender) {
		this.name = name;
		this.age = age;
		this.gender = gender;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Integer getAge() {
		return this.age;
	}
	
	public Gender getGender() {
		return this.gender;
	}
}

enum Gender {
	MALE, FEMALE;
}

class T1 implements Callable {

	public Object call() throws Exception {
		long total = 0;
        for (int i = 0; i < 10000000; i++) {
            total += i;
        }
		return total;
	}
	
}

class T2 implements Callable {

	public Object call() throws Exception {
		List<P> list = new ArrayList<P>();
        for (int i = 0; i <= 100000; i++) {
            list.add(new P("Jim", i, Gender.MALE));
        }
        return list;
	}	
	
}

class T3 implements Callable {

	public Object call() throws Exception {
		List<P> people = new ArrayList<>();
		for(int i = 0; i < 10000000; i++) {
			people.add(new P("Danny", i, Gender.MALE));
		}
		
		List<String> names = new ArrayList<>();
		for(P p : people) {
		    if(p.getAge() > 15) {
		        names.add(p.getName().toUpperCase());
		    }
		}
		
		return names;
	}	
	
}

class T4 implements Callable {

	public Object call() throws Exception {
		List<P> people = new ArrayList<>();
		for(int i = 0; i < 10000000; i++) {
			people.add(new P("Danny", i, Gender.MALE));
		}
		
		List<String> names = people.stream()
                .filter(person -> person.getAge() > 15)
                .map(person -> person.getName().toUpperCase())
                .collect(Collectors.toList());
		
		return names;
	}	
	
}
