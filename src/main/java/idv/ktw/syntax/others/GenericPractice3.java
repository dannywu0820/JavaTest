package idv.ktw.syntax.others;

import java.util.Arrays;
import java.util.Comparator;

public class GenericPractice3 {
	public static void main(String[] args) {
		demoCovariance();
		demoContravariance();
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
	 
	static void demoContravariance() {
		Node<Fruit> fruit = new Node<>(new Fruit(), null);
		Node<? super Orange> node = fruit;
		Node<? super Apple> node2 = fruit;
		
		Comparator<Fruit> byPrice = new Comparator<Fruit>() {
            @Override
            public int compare(Fruit fruit1, Fruit fruit2) {
                return fruit1.price - fruit2.price;
            }
        };
        
        Basket<Apple> apples = new Basket<>(
                new Apple(25, 150), new Apple(20, 100));
        apples.sort(byPrice);
        printlnForEach(apples);
        
        Basket<Orange> Oranges = new Basket<>(
                new Orange(50, 300), new Orange(30, 200));
        Oranges.sort(byPrice);
        printlnForEach(Oranges);
	}
	
	private static void printlnForEach(Basket<? extends Fruit> basket) {
        for(Fruit fruit : basket.things) {
            System.out.printf("%s(%d, %d) ", fruit.getClass().getName(), 
                    fruit.price, fruit.weight);
        }
        System.out.println();
    }
	
	//PECS: Producer Extends(Covariance) Consumer Super(Contravariance), Both get and set(Invariance)
	static void demoProducerExtends() {
		Plate<? extends Fruit> p = new Plate<Apple>(new Apple());
		
		p.setItem(new Object());
		p.setItem(new Food());
		p.setItem(new Fruit());
		p.setItem(new Apple());
		p.setItem(null);
		
		Object fruit1 = p.getItem();
		Food fruit2 = p.getItem();
		Fruit fruit3 = p.getItem();
		Apple fruit4 = p.getItem();
	}
	
	static void demoConsumerSuper() {
		Plate<? super Fruit> p = new Plate<Food>(new Food());
		
		p.setItem(new Object());
		p.setItem(new Food());
		p.setItem(new Fruit());
		p.setItem(new Apple());
		p.setItem(null);
		
		Object fruit1 = p.getItem();
		Food fruit2 = p.getItem();
		Fruit fruit3 = p.getItem();
		Apple fruit4 = p.getItem();
	}
}

class Plate<T> {
	private T item;
	Plate(T item) { this.item = item; }
	public T getItem() { return this.item; };
	public void setItem(T newItem) { this.item = newItem; };
}

class Food {};

class Fruit extends Food {
	int price;
    int weight;
    Fruit() {}
    Fruit(int price, int weight) {
        this.price = price;
        this.weight = weight;
    }
};
class Meat extends Food {};

class Apple extends Fruit {
	Apple() {}
    Apple(int price, int weight) {
         super(price, weight);
    }
    @Override
    public String toString() {
        return "Apple";
    }
};
class Orange extends Fruit {
	Orange() {}
    Orange(int price, int weight) {
         super(price, weight);
    }
    @Override
    public String toString() {
        return "Orange";
    }
};
class Beef extends Meat {};
class Pork extends Meat {};

class Node<T> {
    public T value;
    public Node<T> next;
    
    public Node(T value, Node<T> next) {
        this.value = value;
        this.next = next;
    }
}

class Basket<T> {
    public T[] things;

    public Basket(T... things) {
        this.things = things;
    }

    public void sort(Comparator<? super T> comparator) {
        Arrays.sort(things, comparator);
    }
}