package idv.ktw.syntax.classes;

public class LambdaPractice2 {
	public static void main(String[] args) {
		// use Runnable.toString
		new Hello().r1.run();
		new Hello().r2.run();
		
		// use HelloWithLambda.toString
		new HelloWithLambda().r1.run();
		new HelloWithLambda().r2.run();
	}
}

class Hello {
	Runnable r1 = new Runnable() {
		public void run() {
			System.out.println(this);
		}
	};
	
	Runnable r2 = new Runnable() {
		public void run() {
			System.out.println(toString());
		}
	};
	
	public String toString() {
		return "Hello World";
	}
}

class HelloWithLambda {
	Runnable r1 = () -> System.out.println(this);;
	Runnable r2 = () -> System.out.println(toString());
	
	public String toString() {
		return "Hello World";
	}
}
