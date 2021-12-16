package idv.ktw.test;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonIgnoreProperties(value = { "countSpecial", "countNormal" })
class RewardResult {
	public static int total = 100_000_000;
	private Integer countSpecial;
	private Integer countNormal;
	private String no;
	private List<Integer> numbers;
	private Prize prize;
	
	RewardResult(String no, List<Integer> numbers){
		this.countSpecial = 0;
		this.countNormal = 0;
		this.no = no;
		this.numbers = numbers;
		this.prize = Prize.NONE;
	}
	
	public int getCountSpecial() {
		return this.countSpecial;
	}
	
	public void setCountSpecial(int value) {
		this.countSpecial = value;
	}
	
	public int getCountNormal() {
		return this.countNormal;
	}
	
	public void setCountNormal(int value) {
		this.countNormal = value;
	}
	
	public void transform() {
		if (countSpecial == 1) {
			switch (countNormal) {
				case 5:
					prize = Prize.SECOND;
					break;
				case 4:
					prize = Prize.FOURTH;
					break;
				case 3:
					prize = Prize.SIXTH;
					break;
				case 2:
					prize = Prize.SEVENTH;
					break;
				default:
					break;
			}
		}
		else {
			switch (countNormal) {
				case 6:
					prize = Prize.FIRST;
					break;
				case 5:
					prize = Prize.THIRD;
					break;
				case 4:
					prize = Prize.FIFTH;
					break;
				case 3:
					prize = Prize.EIGHTH;
					break;
				default:
					break;
			}
		}
	}
	
	public String toString() {
		return String.format("CN: %d, CS: %d, No: %s, Prize: %s, Money: %d, Numbers: %s",
				this.countNormal,
				this.countSpecial,
				this.no, 
				this.prize.getName(), 
				this.prize.getMoney(), 
				this.numbers.toString()
				);
	}
}
