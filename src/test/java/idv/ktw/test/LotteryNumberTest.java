package idv.ktw.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class LotteryNumberTest {
	private LotteryNumber n;
	private static String noNotImportant;
	private static List<Integer> numberNotImportant;
	private static int specialNumberNotImportant;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		noNotImportant = "";
		numberNotImportant = new ArrayList<>();
		for(int i = 1; i < 7; i++) numberNotImportant.add(i);
		specialNumberNotImportant = 8;
	}
	
	@After
	public void tearDown() {
		n = null;
	}
	
	@Test
	public void testNoWhenFormatIsNotDate() {
		n = new LotteryNumberBuilder()
				.no("20xxxxx")
				.number(numberNotImportant)
				.specialNumber(specialNumberNotImportant)
				.build();
		
		Throwable thrown = catchThrowable(() -> n.checkNo());
		
		assertThat(thrown).isInstanceOf(LotteryNumber.IllegalNoException.class);
	}
	
	@Test
	public void testNumberWhenSizeIsNot6() {
		List<Integer> l = new ArrayList<>();
		
		n = new LotteryNumberBuilder()
				.no(noNotImportant)
				.number(l)
				.specialNumber(specialNumberNotImportant)
				.build();
		
		Throwable thrown = catchThrowable(() -> n.checkNumber());
		
		assertThat(thrown).isInstanceOf(LotteryNumber.IllegalNumberException.class);
	}
	
	@Test
	public void testNumberWhenElementIsOutOfRange() {
		n = new LotteryNumberBuilder().build();
		
		Throwable thrown = catchThrowable(() -> n.checkNumber());
		
		assertThat(thrown).isInstanceOf(LotteryNumber.IllegalNumberException.class);
	}
	
	@Test
	public void testNumberWhenDuplicatedElements() {
		n = new LotteryNumberBuilder().build();
		
		Throwable thrown = catchThrowable(() -> n.checkNumber());
		
		assertThat(thrown).isInstanceOf(LotteryNumber.IllegalNumberException.class);
	}
	
	@Test
	public void testSpecialNumberWhenDuplicatedElements() {
		List<Integer> ary = new ArrayList<>();
		for(int i = 1; i < 7; i++) ary.add(i);
		
		int s = 1;
		n = new LotteryNumberBuilder()
				.no(noNotImportant)
				.number(ary)
				.specialNumber(s)
				.build();
		
		Throwable thrown = catchThrowable(() -> n.checkSpecialNumber());
		
		assertThat(thrown).isInstanceOf(LotteryNumber.IllegalDuplicateException.class);
	}

}

final class LotteryNumberBuilder {
	private String no;
	private List<Integer> number;
	private int specialNumber;
	
	public LotteryNumberBuilder no(String no) {
		this.no = no;
		return this;
	}
	
	public LotteryNumberBuilder number(List<Integer> number) {
		this.number = number;
		return this;
	}
	
	public LotteryNumberBuilder specialNumber(int specialNumber) {
		this.specialNumber = specialNumber;
		return this;
	}
	
	public LotteryNumber build() {
		LotteryNumber lottery = new LotteryNumber();
		lottery.setNo(no);
		lottery.setNumber(number);
		lottery.setSpecialNumber(specialNumber);
		return lottery;
	}
}
