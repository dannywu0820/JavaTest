package idv.ktw.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import idv.ktw.test.MyNumber.IllegalDuplicateException;
import idv.ktw.test.MyNumber.IllegalNumberException;

@JsonDeserialize(builder = LotteryNumber.Builder.class)
public class LotteryNumber {
	private final String no;
	private final List<Integer> number;
	private final int specialNumber;
	
	private LotteryNumber(String no, List<Integer> number, int specialNumber) {
		this.no = no;
		this.number = number;
		this.specialNumber = specialNumber;
	}
	
	@JsonPOJOBuilder(withPrefix = "")
	public static final class Builder {
		private String no;
		private List<Integer> number;
		private int specialNumber;
		
		public Builder no(String no) {
			this.no = no;
			return this;
		}
		
		public Builder number(List<Integer> number) {
			this.number = number;
			return this;
		}
		
		public Builder specialNumber(int specialNumber) {
			this.specialNumber = specialNumber;
			return this;
		}
		
		public LotteryNumber build() {
			return new LotteryNumber(no, number, specialNumber);
		}
	}
	
	public String getNo() {
		return this.no;
	}
	
	public List<Integer> getNumber() {
		return this.number;
	}
	
	public int getSpecialNumber() {
		return this.specialNumber;
	}
	
	public boolean matches(int value) {
		return this.number.contains(value);
	}
	
	public boolean matchesSpecial(int value) {
		return this.specialNumber == value;
	}
	
	public String toString() {
		return String.format("no: %s, n: %s, sn: %s", this.no, this.number, this.specialNumber);
	}
	
	public void checkNo() {
		if (!this.no.matches("\\d{4}\\d{2}\\d{2}")) throw new IllegalNoException();
	}
	
	public void checkNumber() {
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
	
	public void checkSpecialNumber() {
		if (this.specialNumber > 49 || this.specialNumber < 1) throw new IllegalSpecialNumberException();
		if (this.number.contains(this.specialNumber)) throw new IllegalDuplicateException();
	}
	
	class IllegalNoException extends RuntimeException {}
	class IllegalNumberException extends RuntimeException {}
	class IllegalSpecialNumberException extends RuntimeException {}
	class IllegalDuplicateException extends RuntimeException{}
}
