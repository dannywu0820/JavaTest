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
