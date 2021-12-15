package idv.ktw.syntax.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComparablePractice {
	public static void main(String[] args) {
		List people = new ArrayList<>();
		// List<Person> people = new ArrayList<>(); // compile error
		people.add(new Person("Danny", "Wu", 3));
		people.add(new Person("Jason", "Kuan", 2));
		people.add(new Person("Frank", "Ting", 1));
		Collections.sort(people);
	}
}

class Person {
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
}
