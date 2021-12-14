package idv.ktw.syntax.others;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;

public class GenericPractice2 {
	public static void main(String[] args) {
		demoBoundedGenerics();
		
		String[] words = {"B", "X", "A", "M", "F", "W", "O"};
		GenericPractice2.sort(words);
		for(String word: words) {
			System.out.printf("%s, ", word);
		}
	}
	
	static void demoBoundedGenerics() {
		new CollectionGeneric<>(new ArrayList()).show();
		new CollectionGeneric<>(new ArrayDeque()).show();
		new CollectionGeneric<>(new Stack()).show();
		new CollectionGeneric<>(new Vector()).show();
		new CollectionGeneric<>(new LinkedList()).show();
		new CollectionGeneric<>(new LinkedHashSet()).show();
		new CollectionGeneric<>(new TreeSet()).show();
	}
	
	public static <T extends Comparable> void sort(T[] array) {
        sort(array, 0, array.length-1);
    }
    
    private static <T extends Comparable> void sort(T[] array, int left, int right) {
        if(left < right) { 
            int q = partition(array, left, right); 
            sort(array, left, q-1); 
            sort(array, q+1, right); 
        } 
    }
    
    private static <T extends Comparable> int partition(T[] array, int left, int right) {  
        int i = left - 1; 
        for(int j = left; j < right; j++) {
            if(array[j].compareTo(array[right]) <= 0) {
                i++; 
                swap(array, i, j); 
            } 
        }
        swap(array, i+1, right); 
        return i + 1; 
    } 

    private static <T> void swap(T[] array, int i, int j) {
        T t = array[i]; 
        array[i] = array[j]; 
        array[j] = t;
    }
}

//bounded generics
class CollectionGeneric <T extends Collection> {
	private T collection;
	
	CollectionGeneric(T collection) {
		this.collection = collection;
	}
	
	public void show() {
		System.out.println(this.collection.getClass().getName());
	}
}

//multiple-bounded generics
class MyClass {}
interface MyInterface {}

class BoundedGeneric1 <T extends MyClass> {};
class BoundedGeneric2 <T extends MyInterface> {};
class BoundedGeneric3 <T extends MyClass & MyInterface> {};
//class BoundedGeneric4 <T extends MyInterface & MyClass> {}; // classes before interfaces

//class Kls <T extends Number & Comparable>
//public class Some<T extends Iterable<T> & Comparable<T>>