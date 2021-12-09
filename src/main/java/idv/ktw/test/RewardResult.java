package idv.ktw.test;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "countSpecial", "countNormal" })
class RewardResult {
	private Integer countSpecial;
	private Integer countNormal;
	private String no;
	private String prize;
	private Integer money;
	private List<Integer> numbers;
	
	RewardResult(String no, List<Integer> numbers){
		this.countSpecial = 0;
		this.countNormal = 0;
		this.no = no;
		this.prize = "None";
		this.money = 0;
		this.numbers = numbers;
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
	
	//transform from countSpecial, countNormal to no, prize, money
	public void transform() {
		if (countSpecial == 1) {
			if (countNormal == 5) {
				this.setReward("2nd", 0);
			}
			else if (countNormal == 4) {
				this.setReward("4th", 0);
			}
			else if (countNormal == 3) {
				this.setReward("6th", 1_000);
			}
			else if (countNormal == 2) {
				this.setReward("7th", 400);
			}
			else {
				this.setReward("None", 0);
			}
		}
		else {
			if (countNormal == 6) {
				this.setReward("1st", 0);
			}
			else if (countNormal == 5) {
				this.setReward("3rd", 0);
			}
			else if (countNormal == 4) {
				this.setReward("5th", 2_000);
			}
			else if (countNormal == 3) {
				this.setReward("8th", 400);
			}
			else {
				this.setReward("None", 0);
			}
		}
	}

	private void setReward(String prize, int money) {
		this.prize = prize;
		this.money = money;
	}
	
	public String toString() {
		// return String.format("cNormal: %d, cSpecial: %d", this.countNormal, this.countSpecial);
		return String.format("No: %s, Prize: %s, Money: %d, Numbers: %s", this.no, this.prize, this.money, this.numbers.toString());
	}
}
