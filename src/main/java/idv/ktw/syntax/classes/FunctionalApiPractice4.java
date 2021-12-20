package idv.ktw.syntax.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/*
 * One of the terminal operation .collect needs a Collector instance and the instance has
 * 1.supplier
 * 2.accumulator
 * 3.combiner
 * 4.finisher
 */

public class FunctionalApiPractice4 {

	public static void main(String[] args) {
		List<P> people = new ArrayList<>();
		people.add(new P("D", 30, Gender.MALE));
		people.add(new P("C", 40, Gender.MALE));
		people.add(new P("B", 50, Gender.FEMALE));
		people.add(new P("A", 60, Gender.FEMALE));
		
		demoCollectWithCollector(people);
		demoCollectWithLambda(people);
		demoCollectWithAnonymousClass(people);
		demoCollectWithMethodReference(people);
		
	}

	static void demoCollectWithCollector(List<P> people) {
		List<P> males = people.stream()
				.filter(person -> person.getGender() == Gender.MALE)
				.collect(Collectors.toList()); // Collector instance
				//.collect(Collectors.toSet());
		
		males.forEach(System.out::println);
	}
	
	static Supplier<ArrayList<P>> supplier = new Supplier<ArrayList<P>>() {
	    @Override
	    public ArrayList<P> get() {
	        return new ArrayList<>();
	    }
	};

	static BiConsumer<ArrayList<P>, P> accumulator = new BiConsumer<ArrayList<P>, P>() {
	    @Override
	    public void accept(ArrayList<P> c, P e) {
	        c.add(e);
	    }
	};

	static BiConsumer<ArrayList<P>, ArrayList<P>> combiner = new BiConsumer<ArrayList<P>, ArrayList<P>>() {
	    @Override
	    public void accept(ArrayList<P> c1, ArrayList<P> c2) {
	        c1.addAll(c2);
	    }
	};
	
	static void demoCollectWithLambda(List<P> people) {
		List<P> males = people.stream()
				.filter(person -> person.getAge() > 40)
				.collect(
                     () -> new ArrayList<>(), // supplier()
                     (maleLt, person) -> maleLt.add(person), // accumulator()
                     (maleLt1, maleLt2) -> maleLt1.addAll(maleLt2) // combiner()
                );
		
		males.forEach(System.out::println);
	}
	
	static void demoCollectWithAnonymousClass(List<P> people) {
		List<P> males = people.stream()
				.collect(
                     supplier,
                     accumulator,
                     combiner
                );
		
		males.forEach(System.out::println);
	}
	
	static void demoCollectWithMethodReference(List<P> people) {
		List<P> males = people.stream()
				.collect(
                     ArrayList::new,
                     ArrayList::add,
                     ArrayList::addAll
                );
		
		males.forEach(System.out::println);
	}
	
	static void demoCollectWithFinisher(List<P> people) {
		// finisher()
	}
	
	static void demoCollectWithGroupingBy(List<P> people) {
		Map<Gender, List<P>> males3 = people.stream()
							.collect(Collectors.groupingBy(P::getGender));

		Map<Gender, Integer> males4 = people.stream()
							.collect(
								Collectors.groupingBy(
									P::getGender,
									Collectors.reducing(0, P::getAge, Integer::sum)
								)
							);
	}
	
	
	
}
