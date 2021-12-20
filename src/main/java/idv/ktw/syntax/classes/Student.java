package idv.ktw.syntax.classes;

import java.util.List;

public class Student implements Cloneable{
	private String name;
	private List<Grade> grades;
	
	Student(String name, List<Grade> grades) {
		this.name = name;
		this.grades = grades;
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<Grade> getGrades() {
		return this.grades;
	}
	
	public Student clone() {
		Student student = null;
		try {
			student = (Student) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return student;
	}
}
