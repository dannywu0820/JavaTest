package idv.ktw.syntax.others;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class GenericPractice {
	public static void main(String[] args) {
		demoGenericClasses();
		demoGenericInterfaces();
		demoGenericMethods();
	}
	
	static void demoGenericClasses() {
		Point<Integer> p1 = new Point<Integer> ();
		Point<Float> p2 = new Point<Float> ();
		//Point<int> p3 = new Point<int> (); // primitives are not allowed to use as parameterized types
		
		MyCollection<Integer> ary = new MyCollection<Integer>();
		ary.add(1);
		//ary.add("str"); // can identify wrong type during compile time
	}
	
	static void demoGenericInterfaces() {
		InfoImpl ele = new InfoImpl("str");
		System.out.println(ele.getInfo());
		
		String[] words = {"B", "X", "A", "M", "F", "W", "O"};
        Arrays.sort(words, new ReversedStringOrder());
        for(String word : words) {
            System.out.println(word);
        }
	}
	
	static void demoGenericMethods() {
		ArrayList<String> arrLt = GenericPractice.<String>asList("B", "X", "A", "M", "F", "W", "O");
		String item = GenericPractice.asList("B", "X", "A", "M", "F", "W", "O").get(5).toUpperCase();
		ArrayList<Student> arrLt2 = GenericPractice.asList(new Student());
		String item2 = GenericPractice.asList(new Student(), new Student()).get(0).getName();
		
		String[] words = {"A", "X", "B", "Y"};
		// public static <T> T[] copyOf(T[] original, int newLength)
		String[] newWords = Arrays.copyOf(words, words.length * 2);
		
		GenericPractice.staticMethod(13.5F);
        GenericPractice.<String>staticMethod("Java is good!");
        
        GenericPractice obj = new GenericPractice();
        obj.otherMethod((byte)23);
        obj.<Integer>otherMethod(new Integer(123));
	}
	
	public static <T> ArrayList<T> asList(T... a) {
        ArrayList<T> arrLt = new ArrayList<>();
        for(T t : a) {
            arrLt.add(t);
        }
        return arrLt;
    }
	
    static <T> void staticMethod(T a){
        System.out.println(a.getClass().getName() + " = " + a);
    }
    
    <T> void otherMethod(T a){
        System.out.println(a.getClass().getName() + " = " + a);
    }
}

class Student {
	private String name;
	
	public String getName() {
		return this.name;
	}
}

//generic classes
class Point<T> {
	private T x;
	
	Point() {};
	
	Point(T value) {
		this.setX(value);
	}
	
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

//generic interfaces
interface Info<T> {
	public abstract T getInfo();
	public abstract void setInfo(T info);
}

class InfoImpl implements Info<String> {
	private String var;
	
	InfoImpl(String var){
		this.setInfo(var);
	}
	
	@Override
	public String getInfo() {
		return this.var;
	}
	
	@Override
	public void setInfo(String value) {
		this.var = value;
	}
}

/*interface Comparator<T> {
	public abstract int compare(T obj1, T obj2);
}*/

class ReversedStringOrder implements Comparator<String> {
    @Override
    public int compare(String s1, String s2) {
        return -s1.compareTo(s2);
    }
}

//define generic classes to implement generic interfaces
abstract class InfoImle2<T> implements Info<T> {}
class InfoImle3<U, V, T> implements Info<T> {
	private U u;
	private V v;
	private T t;
	
	@Override
	public T getInfo() {
		return this.t;
	}
	
	@Override
	public void setInfo(T info) {
		this.t = info;
	}
}