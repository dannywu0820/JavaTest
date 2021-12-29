package idv.ktw.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MyNumberTest {
	private MyNumber n = null;
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
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
	public void testWhenSizeIsNot6() {
		int[] ary = {1,2,3,4};
		n = new MyNumber(ary);
		
		thrown.expect(MyNumber.IllegalNumberException.class);
		
		n.checkNumber();
	}

	@Test
	public void testWhenElementIsOutOfRange() {
		int[] ary = {1,1,1,1,1,51};
		n = new MyNumber(ary);
		
		thrown.expect(MyNumber.IllegalNumberException.class);
		
		n.checkNumber();
	}
	
	@Test
	public void testWhenDuplicatedElements() {
		int[] ary = {1,2,3,4,5,5};
		n = new MyNumber(ary);
		
		thrown.expect(MyNumber.IllegalDuplicateException.class);
		
		n.checkNumber();
	}
}
