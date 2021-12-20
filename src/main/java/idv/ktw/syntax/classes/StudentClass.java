package idv.ktw.syntax.classes;

import java.util.List;

public class StudentClass {
	private String name;
	private List<Student> students;
	
	StudentClass(String name, List<Student> students) {
		this.name = name;
		this.students = students;
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<Student> getStudents() {
		return this.students;
	}
}
