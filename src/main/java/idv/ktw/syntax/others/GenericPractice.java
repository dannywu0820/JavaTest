package idv.ktw.syntax.others;

import java.util.Arrays;

public class GenericPractice {
	public static void main(String[] args) {
		demoGenericClasses();
	}
	
	static void demoGenericClasses() {
		Point<Integer> p1 = new Point<Integer> ();
		Point<Float> p2 = new Point<Float> ();
		//Point<int> p3 = new Point<int> (); // primitives are not allowed to use as parameterized types
		
		MyCollection<Integer> ary = new MyCollection<Integer>();
		ary.add(1);
		//ary.add("str"); // can identify wrong type during compile time
	}
}

//generic classes
class Point<T> {
	private T x;
	
	public T getX() {
		return this.x;
	}
	
	public void setX(T value) {
		this.x = value;
	}
}

class MyCollection<E> {
	private Object[] list;
    private int next;
   
    public MyCollection(int capacity) {
        list = new Object[capacity];
    }

    public MyCollection() {
        this(16);
    }

    public void add(E e) {
        if(next == list.length) {
            list = Arrays.copyOf(list, list.length * 2);
        }
        list[next++] = e;
    }
    
    public E get(int index) {
        return (E) list[index];
    }
    
    public int size() {
        return next;
    }
}
class MyNumber<N> {}

class Map<K, V> {
	private K key;
	private V value;
}