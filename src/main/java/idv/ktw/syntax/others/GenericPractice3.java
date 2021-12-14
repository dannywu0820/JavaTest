package idv.ktw.syntax.others;

public class GenericPractice3 {
	public static void main(String[] args) {
		demoCovariance();
	}
	
	static void demoCovariance() {
		Apple a = new Apple();
		Fruit f = a;
		
		Plate<Apple> plateToPlaceApple = new Plate<>(new Apple());
		//Plate<Fruit> plateToPlaceFruit = plateToPlaceApple; 
		Plate<? extends Fruit> plateToPlaceFruit = plateToPlaceApple;
		
		Object o = plateToPlaceFruit.getItem();
		plateToPlaceFruit.setItem(null);
		//Apple apple = plateToPlaceFruit.getItem();
		//plateToPlaceFruit.setItem(new Apple());
		
		//<?> is the same as <? extends Object>
		Plate<Object> plateToPlaceObject = new Plate<>(new Object());
		Plate<?> plateToPlaceJavaObject = plateToPlaceObject;
	}
}

class Plate<T> {
	private T item;
	Plate(T item) { this.item = item; }
	public T getItem() { return this.item; };
	public void setItem(T newItem) { this.item = newItem; };
}

class Food {};

class Fruit extends Food {};
class Meat extends Food {};

class Apple extends Fruit {};
class Orange extends Fruit {};
class Beef extends Meat {};
class Pork extends Meat {};