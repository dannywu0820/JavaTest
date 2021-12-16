package idv.ktw.test;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = PrizeSerializer.class)
public enum Prize {
	FIRST("1st", (int)(RewardResult.total * 0.82)),
	SECOND("2nd", (int)(RewardResult.total * 0.065)),
	THIRD("3rd", (int)(RewardResult.total * 0.07)),
	FOURTH("4th", (int)(RewardResult.total * 0.045)),
	FIFTH("5th", 2_000),
	SIXTH("6th", 1_000),
	SEVENTH("7th", 400),
	EIGHTH("8th", 400),
	NONE("none", 0);
	
	private String name;
	private int money;
	
	Prize(String name, int money) {
		this.name = name;
		this.money = money;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getMoney() {
		return this.money;
	}
}
