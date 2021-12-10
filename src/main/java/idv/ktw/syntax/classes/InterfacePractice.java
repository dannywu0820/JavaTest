package idv.ktw.syntax.classes;

public class InterfacePractice {
	public static void main() {
		testPolymorphism();
	}
	
	private static void testPolymorphism() {
		Swimmable swimmer1 = new Human();
		Swimmable swimmer2 = new Shark();
		
		// Shark shark = swimmer2;
		Shark shark = (Shark) swimmer2;
		Fish fish = (Fish) swimmer2;
		Fish fish2 = (Fish) swimmer1;
		fish.swim();
		//fish2.swim(); // java.lang.ClassCastException
	}
	
	// Polymorphism in Inheritance
	
	public static void doSwim(Fish fish) {
	    fish.swim();
	}
	public static void doSwim(Human human) {
	    human.swim();
	}
	public static void doSwim(Submarine submarine) {
	    submarine.swim();
	}
	
	// Polymorphism in Interface
	
	public static void doSwim(Swimmable swimmer) {
		swimmer.swim();
	}

}

interface Swimmable {
	public abstract void swim();
} 

class Human implements Swimmable {
	public void swim() {
		System.out.println("Human Swim.");
	}
}

abstract class Fish implements Swimmable {
	@Override
	public abstract void swim();
}

class Shark extends Fish {
	public void swim() {
		System.out.println("Shark Swim.");
	}
}

class Submarine implements Swimmable {
	public void swim() {
		System.out.println("Submarine Swim.");
	}
}