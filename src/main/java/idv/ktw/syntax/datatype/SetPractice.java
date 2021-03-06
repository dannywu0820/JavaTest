package idv.ktw.syntax.datatype;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SetPractice {
	public static void main(String[] args) {
		demoHashSet();
	}
	
	static void demoHashSet() {
		Set<Worker> hashset = new HashSet<>();
		hashset.add(new Worker("Danny", "E"));
		hashset.add(new Worker("Jason", "PE"));
		hashset.add(new Worker("Frank", "SM"));
		hashset.add(new Worker("Danny", "E"));
		
		System.out.println(hashset.toString());
	}
}

class Worker {
	private String name;
	private String title;
	
	Worker(String name, String title) {
		this.name = name;
		this.title = title;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.name, this.title);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Worker other = (Worker) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        return true;
	}
	
	@Override
	public String toString() {
		return String.format("name: %s, title: %s", this.name, this.title);
	}
}
