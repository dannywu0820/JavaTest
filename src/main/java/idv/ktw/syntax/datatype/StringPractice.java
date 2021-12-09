package idv.ktw.syntax.datatype;

public class StringPractice {
	public static void main() {
		StringPractice.initializeString();
		StringPractice.iterateString();
		StringPractice.testStringPool();
		StringPractice.testConcat();
	}
	
	private static void initializeString() {
		String str = "Danny";
		char[] char_str = {'D', 'a', 'n', 'n', 'y'};
		String name = new String(char_str);
		char_str = str.toCharArray();
	}
	
	private static void iterateString() {
		String str = "Danny";
		for(int i = 0; i < str.length(); i++) {
			System.out.printf("%c ", str.charAt(i));
		}
		System.out.printf("%n");
	}
	
	private static void testStringPool() {
		char[] name = {'A', 'n', 'd', 'y'};
		String name1 = new String(name); // create new instance
		String name2 = new String(name);
		System.out.println(name1 == name2);
		name1 = "Danny"; // this is String Literal stored in String Pool
		name2 = "Danny";
		System.out.println(name1 == name2);
		String name3 = "Ja" + "va";
		//String s = "Ja";
		//String s1 = (new StringBuilder()).append(s).append("va").toString();
		//since string concat with + will produce new string instances, avoid to use in loops and recursion
		String name4 = "Java";
		System.out.println(name3 == name4);
	}
	
	private static void testConcat() {
		// follow up: how to profile memory usage?
		String text = "";
		for(int i = 1; i < 100; i++) {
		    text = text + i + '+';
		}
		System.out.println(text + 100);
		
		StringBuilder builder = new StringBuilder();
        for (int i = 1; i < 100; i++) {
            builder.append(i).append('+');
        }
        System.out.println(builder.append(100).toString());
	}
}
