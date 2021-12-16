package idv.ktw.syntax.classes;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class IterablePractice {

	public static void main(String[] args) {
		List names = Arrays.asList("Justin", "Monica", "Irene");
        forEach(names);
        forEach(new HashSet(names)); 
        forEach(new ArrayDeque(names));
        
        // JDK8 provides the default method forEach
        names.forEach((n) -> System.out.println(n));
        names.forEach(System.out::println);
        
        new ChildClass().a();
	}
	
	public static <E> void forEach(Iterable<E> iterable) {
		/*for(Object o: iterable) {
			System.out.println(o);
		}*/
		for(Iterator<E> it = iterable.iterator(); it.hasNext();) {
			Object o = it.next();
			System.out.println(o);
		}
	}

}

@FunctionalInterface
interface Comparable<T> {
	int compareTo(T that);
	
	// mixin
	default boolean lessThan(T that) {
		return compareTo(that) < 0;
	}
}

interface MyComparable<T> extends Comparable<T> {
	// mark abstract method in parent class for documentation 
	//abstract int compareTo(T that);
	
	// implement abstract method as default method in parent class
	default int compareTo(T that) {
		return this.hashCode() - that.hashCode();
	}
	
	// redefine default method in parent class
	default boolean lessThan(T that) {
		return compareTo(that) <= 0;
	}
}

class ParentClass {
	public void a() {
		System.out.println("a in ParentClass");
	}
}

interface ParentInterface {
	default void a() {
		System.out.println("a in ParentInterface");
	}
}

class ChildClass extends ParentClass implements ParentInterface{
	@Override
	public void a() {
		ParentInterface.super.a();
		super.a();
		System.out.println("a in ChildClass");
	}
}
