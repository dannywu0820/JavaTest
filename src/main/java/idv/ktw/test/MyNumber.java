package idv.ktw.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;

class MyNumber {
	private List<Integer> number;
	
	MyNumber(JsonNode n) {
		this.number = new ArrayList<Integer>();
		for(JsonNode ele: n) {
			this.number.add(ele.asInt());
		}
		
		this.checkNumber();
	}
	
	public List<Integer> getNumber() {
		return this.number;
	}
	
	public int size() {
		return this.number.size();
	}
	
	public int get(int index) {
		return this.number.get(index);
	}
	
	private void checkNumber() {
		int size = this.number.size();
		if (size != 6) throw new IllegalNumberException(); 
		
		for(int i = 0; i < size; i++) {
			if (this.number.get(i) > 49 || this.number.get(i) < 1) throw new IllegalNumberException();  
		}
		
		Set<Integer> s = new HashSet<Integer>();
		for(Integer value: this.number) {
			if (s.contains(value)) throw new IllegalDuplicateException();
			s.add(value);
		}
	}
	
	class IllegalNumberException extends RuntimeException {}
	class IllegalDuplicateException extends RuntimeException{}
}
