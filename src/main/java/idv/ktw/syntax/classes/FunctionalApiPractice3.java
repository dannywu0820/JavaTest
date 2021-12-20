package idv.ktw.syntax.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * Stream API(Lazy Evaluation, Pipelining)
 */

public class FunctionalApiPractice3 {
	private static String[] ary1D = { "a", "b", "c", "d", "e", "f", "aa", "aaa", "a" };
	private static String[][] ary2D = { {"a", "b"}, {"c", "d"}, {"e", "f"}, {"aa", "aaa"} };
	private static String[][][] ary3D = { { {"a"}, {"b"} }, { {"c"}, {"d"} }, { {"e"}, {"f"} }, { {"aa"}, {"aaa"} } };
	private static List<StudentClass> classes = createClasses(); 
	
	public static void main(String[] args) {
		demoIntermediateOperations();
		demoTerminalOperations();
		demoWithMoreComplexStructure();
	}
	
	static List<StudentClass> createClasses() {
		List<Grade> grades = new ArrayList<> ();
		for(int i = 0; i < 2; i++) {
			grades.add(new Grade(100 - i*10, 100 - i*20, 100 - i*30));
		}
		
		List<Student> studentsInA = new ArrayList<> ();
		for(Integer i = 1; i < 3; i++) {
			studentsInA.add(new Student("s" + i.toString(), grades));
		}
		List<Student> studentsInB = studentsInA.stream().map(s -> s.clone()).collect(Collectors.toList());
		
		StudentClass classA = new StudentClass("A", studentsInA);
		StudentClass classB = new StudentClass("B", studentsInB);
		List<StudentClass> classes = new ArrayList<StudentClass> ();
		classes.add(classA);
		classes.add(classB);
		
		return classes;
	}
	
	static void demoIntermediateOperations() {
		Stream<String> streamAry1D = Stream.of(ary1D);
		// streamAry1D.forEach(System.out::println);
		// java.lang.IllegalStateException: stream has already been operated upon or closed
		// streamAry1D.map(str -> str.toUpperCase() + "*").forEach(System.out::println);
		// streamAry1D.map(str -> str.toUpperCase() + "*").forEach(System.out::println);
		// streamAry1D.skip(2).forEach(System.out::println);
		// streamAry1D.limit(2).forEach(System.out::println);
		// streamAry1D.distinct().forEach(System.out::println);
		// streamAry1D.sorted().forEach(System.out::println);
		
		Stream<String[]> streamAry2D = Stream.of(ary2D);
		// Stream<String> afterFlatMap = streamAry2D.flatMap(ele -> Stream.of(ele));
		// afterFlatMap.forEach(System.out::println);
		// Stream<String[]> afterMap = streamAry2D.map(ele -> ele);
		// afterMap.forEach(ele -> System.out.printf("%s, %s%n", ele[0], ele[1]));
		
		Stream<String[][]> streamAry3D = Stream.of(ary3D);
		streamAry3D.flatMap(ele -> Stream.of(ele)).flatMap(ele -> Stream.of(ele)).forEach(System.out::println);
	}
	
	static void demoTerminalOperations() {
		Stream<String> streamAry1D = Stream.of(ary1D);
		// streamAry1D.forEach(System.out::println);
		// Optional<String> result = streamAry1D.reduce((accumulated, ele) -> accumulated + ele);
		// List<String> result = streamAry1D.collect(Collectors.toList());
		// long result = streamAry1D.count();
		// Optional<String> result = streamAry1D.max((s1, s2) -> s1.length() - s2.length());
		// boolean result = streamAry1D.allMatch(ele -> ele.length() == 3);
		// boolean result = streamAry1D.anyMatch(ele -> ele.length() == 3);
		boolean result = streamAry1D.noneMatch(ele -> ele.length() == 3);
		System.out.println(result);
	}
	
	static void demoWithMoreComplexStructure() {
		classes.stream()
		.flatMap(cls -> cls.getStudents().stream())
		.flatMap(stu -> stu.getGrades().stream())
		.forEach(System.out::println);
		
		List<String> good = new ArrayList<String>();
		classes.stream().forEach(cls -> {
			cls.getStudents().stream()
							.filter(s -> s.getGrades().get(0).getChinese() > 97)
							.map(Student::getName)
							.forEach(name -> good.add(name));
		});
		System.out.println(good);
		
		classes.stream()
				.flatMap(cls -> cls.getStudents().stream())
				.filter(s -> s.getGrades().get(0).getChinese() > 97)
				.map(Student::getName)
				.forEach(System.out::println);
	}
}
