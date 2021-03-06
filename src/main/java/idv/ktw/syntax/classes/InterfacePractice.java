package idv.ktw.syntax.classes;

public class InterfacePractice {
	public static void main() {
		testPolymorphism();
		testBird();
		testSwimPlayer();
	}
	
	private static void testPolymorphism() {
		Swimmable swimmer1 = new Human();
		Swimmable swimmer2 = new Shark();
		
		// Shark shark = swimmer2;
		Shark shark = (Shark) swimmer2;
		Fish fish = (Fish) swimmer2;
		// Fish fish2 = (Fish) swimmer1; // java.lang.ClassCastException
		fish.swim();
		//fish2.swim();
	}
	
	private static void testBird() {
		Bird b = new Bird();
		b.swim();
		b.fly();
	}
	
	private static void testSwimPlayer() {
		SwimPlayer p = new SwimPlayer();
		p.swimBreaststroke();
		p.swimButterflyStroke();;
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

interface Breaststroke extends Swimmable {
	public abstract void swimBreaststroke();
}

interface ButterflyStroke extends Swimmable {
	public abstract void swimButterflyStroke();
}

interface Flyable {
	public abstract void fly();
}

class Human implements Swimmable {
	public void swim() {
		System.out.println("Human Swim.");
	}
}

class SwimPlayer implements Breaststroke, ButterflyStroke {
	public void swim() {
		System.out.printf("%s Swim.%n", this.getClass().getName());
	}
	
	public void swimBreaststroke() {
		System.out.printf("%s SwimBreaststroke.%n", this.getClass().getName());
	}
	
	public void swimButterflyStroke() {
		System.out.printf("%s SwimButterflyStroke.%n", this.getClass().getName());
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

class Bird implements Swimmable, Flyable {
	public void swim() {
		System.out.printf("%s Swim.%n", this.getClass().getName());
	}
	
	public void fly() {
		System.out.printf("%s Fly.%n", this.getClass().getName());
	}
}