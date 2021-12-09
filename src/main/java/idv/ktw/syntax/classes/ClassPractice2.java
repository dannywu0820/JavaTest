package idv.ktw.syntax.classes;

public class ClassPractice2 {
	public static void main() {
		demoConstructor();
	}
	
	static void demoConstructor() {
		Other o = new Other();
	}
}

class Some {
	Some(int i){
		System.out.printf("Call Constructor of Some with %d\n", i);
	}
	
	public void nonStatic() {
		System.out.printf("nonStatic in Some");
	}
	
	public static void Static() {
		System.out.printf("Static in Some");
	}
}

class Other extends Some {
	Other(){
		super(10);
		System.out.printf("Call Constructor of Other\n");
	}
	
	@Override
	public void nonStatic() {
		System.out.printf("nonStatic in Other");
	}
	
	public static void Static() {
		System.out.printf("Static in Other");
	}
}
