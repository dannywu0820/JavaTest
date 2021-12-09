package idv.ktw.test;

import java.util.ArrayList;
import java.util.List;

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
		for(int i = 0; i < this.number.size(); i++) {
			if (this.number.get(i) > 49 || this.number.get(i) < 1) throw new IllegalNumberException(); 
		}
	}
	
	class IllegalNumberException extends RuntimeException {}
}
