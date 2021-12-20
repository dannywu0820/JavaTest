package idv.ktw.syntax.classes;

public class Grade implements Cloneable {
	private int chinese;
	private int english;
	private int math;
	
	Grade(int chinese, int english, int math) {
		this.chinese = chinese;
		this.english = english;
		this.math = math;
	}
	
	public int getChinese() {
		return this.chinese;
	}
	
	public int getEnglishChinese() {
		return this.english;
	}
	
	public int getMath() {
		return this.math;
	}
	
	@Override
    public Grade clone() throws CloneNotSupportedException {
        Grade newGrade = new Grade(this.chinese, this.english, this.math);
        return newGrade;
    }
}
