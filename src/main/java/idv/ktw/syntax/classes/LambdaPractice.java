package idv.ktw.syntax.classes;

import java.util.Arrays;
import java.util.Comparator;

public class LambdaPractice {
	public static void main(String[] args) {
		String[] names = {"Joe", "Frank", "Jason", "Danny"};
		
		m1(names);
		m2(names);
		m3(names);
		m4(names);
		runByMethodReference(names);
		runByMethodReference2(names);
	}
	
	static void m1(String[] strs) {
		Arrays.sort(strs, new Comparator<String>() {
			public int compare(String s1, String s2) {
				return s1.length() - s2.length();
			}
		});
		
		for(String str: strs) System.out.printf("%s, ", str);
		System.out.printf("%n");
	}
	
	static void m2(String[] strs) {
		Comparator<String> byLength = new Comparator<String>() { 
		    public int compare(String name1, String name2) {
		        return name1.length() - name2.length();
		    }
		};
		Arrays.sort(strs, byLength);
		
		for(String str: strs) System.out.printf("%s, ", str);
		System.out.printf("%n");
	}
	
	static void m3(String[] strs) {
		Arrays.sort(strs, (str1, str2) -> str1.length() - str2.length());
		
		for(String str: strs) System.out.printf("%s, ", str);
		System.out.printf("%n");
	}
	
	static void m4(String[] strs) {
		Arrays.sort(strs, (str1, str2) -> StringOrder.byLexicography(str1, str2));
		
		for(String str: strs) System.out.printf("%s, ", str);
		System.out.printf("%n");
	}
	
	static void runByMethodReference(String[] strs) {
		Arrays.sort(strs, StringOrder::byLength);
		
		for(String str: strs) System.out.printf("%s, ", str);
		System.out.printf("%n");
	}
	
	static void runByMethodReference2(String[] strs) {
		Arrays.sort(strs, String::compareTo);
		
		for(String str: strs) System.out.printf("%s, ", str);
		System.out.printf("%n");
	}
}

class StringOrder {
    public static int byLength(String s1, String s2) {
        return s1.length() - s2.length();
    }
 
    public static int byLexicography(String s1, String s2) {
        return s1.compareTo(s2);
    }
 
    public static int byLexicographyIgnoreCase(String s1, String s2) {
        return s1.compareToIgnoreCase(s2);
    }
}

@FunctionalInterface
interface Function<P, R> {
    //R call(P p);
    R call(P p1, P p2);
}
