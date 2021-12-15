package idv.ktw.syntax.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComparablePractice {
	public static void main(String[] args) {
		List people = new ArrayList<>();
		// List<Person> people = new ArrayList<>(); // compile error
		people.add(new Person("Danny", "Wu", 3));
		people.add(new Person("J", "Kuan", 2));
		people.add(new Person("Luke", "Ting", 1));
		Collections.sort(people);
		Collections.sort(people, new MyComparator());
		
		for(Person p: (List<Person>)people) {
			System.out.println(p);
		}
	}
}

class Person implements Comparable<Person> {
	private String firstName;
	private String lastName;
	private Integer zipCode;
	
	Person(String firstName, String lastName, Integer zipCode) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.zipCode = zipCode;
	}
	
	@Override
	public String toString() {
		return String.format("Person(%s, %s, %s)", firstName, lastName, zipCode);
	}
	
	@Override
	public int compareTo(Person other) {
		return this.firstName.length() - other.firstName.length();
	}
}

class MyComparator implements Comparator<Person> {
	@Override
	public int compare(Person p1, Person p2) {
		return -p1.compareTo(p2);
	}
}
