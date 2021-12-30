package idv.ktw.test;

import static org.assertj.core.api.Assertions.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MyNumberTest {
	private MyNumber n = null;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		System.out.println("@BeforeClass");
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		System.out.println("@AfterClass");
	}
	
	@Before
	public void setUp() {
		System.out.println("@Before");
	}
	
	@After
	public void tearDown() {
		System.out.println("@After");
		n = null;
	}	
	
	@Test
	public void testSizeShouldReturn6() {
		int[] ary = {1,2,3,4,5,6};
		n = new MyNumber(ary);
		
		int actual = n.size();
		int expected = 6;
		
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	public void testWhenSizeIsNot6() {
		int[] ary = {1,2,3,4};
		n = new MyNumber(ary);
		
		Throwable thrown = catchThrowable(() -> n.checkNumber());
		
		assertThat(thrown).isInstanceOf(MyNumber.IllegalNumberException.class);
	}

	@Test
	public void testWhenElementIsOutOfRange() {
		int[] ary = {1,1,1,1,1,51};
		n = new MyNumber(ary);
		
Throwable thrown = catchThrowable(() -> n.checkNumber());
		
		assertThat(thrown).isInstanceOf(MyNumber.IllegalNumberException.class);
	}
	
	@Test
	public void testWhenDuplicatedElements() {
		int[] ary = {1,2,3,4,5,5};
		n = new MyNumber(ary);
		
		Throwable thrown = catchThrowable(() -> n.checkNumber());
		
		assertThat(thrown).isInstanceOf(MyNumber.IllegalDuplicateException.class);
	}
}
