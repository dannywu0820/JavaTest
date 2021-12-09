package idv.ktw.syntax.datatype;

import java.util.Arrays;

class People {
	String name;
	int age;
	
	People(String name, int age) {
		this.name = name;
		this.age = age;
	}
}

public class ArrayPractice {
	private static void initializeArray() {
		// String method_name = ArrayPractice.class.getEnclosingMethod().getName();
		// method_name = (method_name.equals(null) ? "null" : method_name);
		// System.out.printf("[%s]%n", method_name);
		
		int[] ary_empty = {};
		int[] ary_1d = new int[5];
		int[][] ary_2d = new int[5][5];
		int[][] ary_2d_2 = new int[5][];
		// int[][] array_2d_3 = new int[][3]; // compile error
		
		System.out.printf("%s%n", Arrays.toString(ary_empty));
		System.out.printf("%s%n", Arrays.toString(ary_1d));
		System.out.printf("%s%n", Arrays.deepToString(ary_2d));
		System.out.printf("%s%n", Arrays.deepToString(ary_2d_2));
	}
	
	private static void iterateArray() {
		String[] array = new String[5];
		System.out.printf("%s%n", array.getClass().getName());
		
		for(int i = 0; i < array.length; i++) {
			System.out.printf("%s%n", array[i]);
		}
		
		Arrays.fill(array, "hello world");
		for(String element: array) {
			System.out.printf("%s%n", element);
		}
		
		String[][] array_2d = new String[3][3]; // new String[3][];
		for(String[] row: array_2d) {
			System.out.printf("%s%n", row);
			for(String value: row) {
				System.out.printf("%s%n", value);
			}
		}
		
	}
	
	private static void copyArray() {
		// shallow copy of an object, need to implement the interface Cloneable
		People deity = new People("Danny", 30);
		People clone = deity;
		System.out.printf("%s,%s%n", clone.name, clone.age);
		deity.name = "Anny";
		deity.age = 20;
		System.out.printf("%s,%s%n", clone.name, clone.age);
		
		// shallow copy of an array of objects
		People i[] = new People[3];
		Arrays.fill(i, new People("Anny", 20));
		
		People i2[] = i.clone();
		System.out.printf("%s,%s%n", i2[1].name, i2[1].age);
		i[1].name = "Danny";
		i[1].age = 30;
		System.out.printf("%s,%s%n", i2[1].name, i2[1].age);
		
		People i3[] = Arrays.copyOf(i, i.length);
		System.out.printf("%s,%s%n", i3[1].name, i3[1].age);
		i[1].name = "Henry";
		i[1].age = 40;
		System.out.printf("%s,%s%n", i3[1].name, i3[1].age);
		
		// deep copy of an array of objects
		People[] i4 = new People[i.length];
		for(int j = 0; j < i.length; j++) {
			People temp = new People(i[j].name, i[j].age);
			i4[j] = temp;
		}
		System.out.printf("%s,%s%n", i4[1].name, i4[1].age);
		i[1].name = "Fuck";
		i[1].age = 50;
		System.out.printf("%s,%s%n", i4[1].name, i4[1].age);
	}
	
	public static void main() {
		// this.initialzeArray(); // cannot use this in a static context
		ArrayPractice.initializeArray();
		ArrayPractice.iterateArray();
		ArrayPractice.copyArray();
	}
}
