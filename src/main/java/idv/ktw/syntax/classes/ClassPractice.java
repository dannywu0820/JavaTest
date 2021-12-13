package idv.ktw.syntax.classes;

public class ClassPractice {
	public static void main(String[] args) {
		demoInheritance();
		demoPolymorphism();
	}
	
	static void demoInheritance() {
		// SwordsMan m = new SwordsMan("Danny", 1, 100); // undefined constructor for SwordsMan
		SwordsMan m = new SwordsMan();
		m.setName("Danny");
		m.setLevel(1);
		System.out.printf("%s: %d %s%n", m.getName(), m.getLevel(), m.getBlood());
	}
	
	static void demoPolymorphism() {
		Role r1 = new Magician();
		Magician m1 = (Magician) r1;
		
		Role r2 = new Magician();
		// SwordsMan s1 = (SwordsMan) r2; // java.lang.ClassCastException
		// Magician m4 = (Magician) new Role(); 
		
		SwordsMan s2 = new SwordsMan();
		Role r = s2;
		SwordsMan s3 = (SwordsMan) r; // SwordsMan sm2 = r; // compile error
		// Magician mg = (Magician) r; // java.lang.ClassCastException
		
		showBlood(r1);
		
		showFight(r1);
		showFight(m1);
		showFight(s3);
	}
	
	static void showBlood(Role role) {
		System.out.printf("%s, %d%n", role.getName(), role.getBlood());
	}
	
	static void showFight(Role role) {
		System.out.printf("%s\n", role.getName());
		role.fight();
	}
}

abstract class Role {
	private String name;
	private int level;
	private int blood;
	
	private String secret = "secret";
	protected String secret2 = "secret2";

	/*Role(String name, int level, int blood) {
		this.name = name;
		this.level = level;
		this.blood = blood;
	}*/
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getBlood() {
		return blood;
	}
	
	public void setBlood(int blood) {
		this.blood = blood;
	}
	
	public String getSecret() {
		return secret;
	}
	
	public String toString() {
		return String.format("%s, %d, %d, %s", this.getName(), this,getLevel(), this.getSecret(), this.secret2);
	}
	
	public abstract void fight();
}

class SwordsMan extends Role {
	public String getSecret() {
		return super.getSecret();
	}
	
	public String getSecret2() {
		return this.secret2;
	}
	
	public String toString() {
		return "SwordsMan: " + super.toString();
	} 
	
	@Override
	public void fight() {
		System.out.println("SwordsMan Attack");
	}
}

class Magician extends Role {
	public void fight() {
		System.out.println("Magician Attack");
	}
}