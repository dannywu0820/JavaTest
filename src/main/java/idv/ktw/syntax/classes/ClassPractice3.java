package idv.ktw.syntax.classes;

public class ClassPractice3 {
	public static void main() {
		Outer.m();
	}
}

class Outer {
	String nonStaticMember = "Outer.nonStaticMember";
	static String staticMember = "Outer.staticMember";
	
	// anonymous inner class
	public static void m() {
		Object o1 = new Object() {
		    public String toString() {
		        return "Hahaha";
		    }
		};
		Object o2 = new Object();
		System.out.println(o1);
		System.out.println(o2);
	}
	
	private class Inner {
		public void main() {
			System.out.println(nonStaticMember);
			System.out.println(staticMember);
		}
	}
	
	static class Static {
		public void main() {
			// System.out.println(nonStaticMember);
			System.out.println(staticMember);
		}
	}
}
