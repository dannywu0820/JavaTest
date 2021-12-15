package idv.ktw.syntax.datatype;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapPractice {
	public static void main(String[] args) {
		// no ordering guaranteed
		Map<String, String> hashmap = new HashMap<>();
		// follow natural ordering, e.g., alphabetically for String
		Map<String, String> treemap1 = new TreeMap<>();
		// or customized ordering
		Map<String, String> treemap2 = new TreeMap<>((obj1, obj2) -> -obj1.compareTo(obj2));
		// follow insertion ordering
		Map<String, String> linkedmap = new LinkedHashMap<>();
	    
		testOrdering(hashmap);
		testOrdering(treemap1);
		testOrdering(treemap2);
		testOrdering(linkedmap);
		
		Map<Human, String> treemap = new TreeMap<>((obj1, obj2) -> obj1.age.compareTo(obj2.age));
		treemap.put(new Human("Danny", 30), "");
		treemap.put(new Human("Joe", 50), "");
		treemap.put(new Human("Frank", 40), "");
		treemap.put(new Human("Jason", 35), "");
		
		
		for(Map.Entry<Human, String> entry: treemap.entrySet()) {
	    	System.out.printf("k: %s, v: %s, h: %s%n", entry.getKey().name, entry.getValue(), entry.hashCode());
	    }
	}
	
	static void testOrdering(Map<String, String> map) {
		System.out.println(map.getClass().getName());
		
		map.put("Danny", "E");
	    map.put("Jason", "PE");
	    map.put("Frank", "SM");
	    map.put("Joe", "AVP");
	    
	    for(Map.Entry<String, String> entry: map.entrySet()) {
	    	System.out.printf("k: %s, v: %s, h: %s%n", entry.getKey(), entry.getValue(), entry.hashCode());
	    }
	}
}

class Human {
	public String name;
	public Integer age;
	
	Human(String name, Integer age) {
		this.name = name;
		this.age = age;
	}
}
