package idv.ktw.syntax.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * 4 types of instances of functional interface can be passed as parameter to lambda(vs. Method Reference)
 * 1.Consumer
 * 2.Supplier
 * 3.Predicate
 * 4.Function
 * */

public class FunctionalApiPractice2 {

	public static void main(String[] args) {
		List<P> people = new ArrayList<>();
		people.add(new P("D", 30, Gender.MALE));
		people.add(new P("C", 40, Gender.MALE));
		people.add(new P("B", 50, Gender.FEMALE));
		people.add(new P("A", 60, Gender.FEMALE));
		
		getMaleAverageWithoutStream(people);
		getMaleAverageWithStream(people);
	}
	
	static void getMaleAverageWithoutStream(List<P> people) {
		int sum = 0;
		int numOfMales = 0;
		for(P person: people) {
		    if(person.getGender() == Gender.MALE) {
		        sum += person.getAge();
		        numOfMales += 1;
		    }
		}
		int average = sum / numOfMales;
		System.out.println(average);
	}

	static void getMaleAverageWithStream(List<P> people) {
		int total = people.stream() // Stream<P>
								.filter(p -> p.getGender() == Gender.MALE) // Stream<P>, need a Predicate
								.map(P::getAge) // Stream<Integer>, need a Function
								.reduce((accumulator, age) -> accumulator + age) // Optional<Integer>, need a BinaryOperator
								.orElseGet(() -> -1); // Integer, need a Supplier
								
		long numOfMales = people.stream()
								.filter(p -> p.getGender() == Gender.MALE)
								.count();
		
		long average = total / numOfMales;
		System.out.println(average);
		
		//IntStream sum()、average()、max()、min()
		int max = people.stream()
						.filter(p -> p.getGender() == Gender.MALE)
						.mapToInt(P::getAge)
						.reduce(0, (currentMax, age) -> age > currentMax ? age : currentMax);
		people.stream().forEach(System.out::println); // void, need a Consumer
	}
	
}
