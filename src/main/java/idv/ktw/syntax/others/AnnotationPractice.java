package idv.ktw.syntax.others;

import java.lang.annotation.Target;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.util.ArrayList;
import java.util.List;

public class AnnotationPractice {
	public static void main(String[] args) {
		m1();
		List<@Email String> emails;
	}
	
	@Deprecated
	public static void m1() {
		System.out.println("This method is makred deprecated");
	}
	
	@SuppressWarnings(value = { "rawtypes", "unchecked" })
	public static void m2() {
		List list = new ArrayList();
	    list.add("Some");
	}
	
	public static void callM3() {
		List<String> list1 = new ArrayList<>();
		List<String> list2 = new ArrayList<>();
		Util.m3(list1, list2);
	}
	
	@MyAnnotation(timeout = 10, args = {"a", "b"})
	public static void useMyAnnotation() {
		
	}
}

class Util {
	@SafeVarargs
	public static <T> void m3(List<String>... varargs ) {
		
	}
	
	/*public static <T> void m3(List<String>... varargs) {
	    Object[] array = varargs;
	    List<Integer> tmpList = Arrays.asList(42);
	    array[0] = tmpList; // 語意不對，不過編譯器不會有警訊
	    String s = varargs[0].get(0); // 執行時期發生 ClassCastException
	}*/
}

@Documented
@Inherited
@Target({ElementType.METHOD})
@interface MyAnnotation {
	int timeout();
	String[] args();
	String[] argsWithDefault() default {};
	Class[] value() default { Object.class };
}

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface Email {}
