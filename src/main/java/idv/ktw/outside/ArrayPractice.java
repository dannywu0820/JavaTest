package idv.ktw.outside;

import java.util.Arrays;

public class ArrayPractice {
	public static void main() {
		int[] array_id = new int[10];
		int[][] array_2d = new int[5][5];
		int[][] array_2d_2 = new int[3][];
		// int[][] array_2d_3 = new int[][3];
		
		for(int i = 0; i < array_2d.length; i++) {
			for(int j = 0; j < array_2d[i].length; j++) {
				array_2d[i][j] = i * j; 
			}
		}
		
		for(int[] row: array_2d) {
			for(int value: row) {
				value = -1;
			}
		}
		
		String[] array = {};
		Arrays.fill(array, "sss");
		for(String val: array) {
			System.out.printf("%s", val);
		}
		
		String[][] array_2d_3 = new String[3][];
		for(String val: array) {
			System.out.printf("%s", val);
		}
		
		System.out.println(Arrays.toString(array));
		System.out.println(Arrays.toString(array_id));
		System.out.println(Arrays.deepToString(array_2d));
	}
}
