package idx.ktw.operators;

import idv.ktw.syntax.classes.ClassPractice;
import idv.ktw.syntax.classes.ClassPractice2;
import idv.ktw.syntax.datatype.ArrayPractice;
import idv.ktw.syntax.datatype.StringPractice;

public class OperatorsPractice {
	public static void main() {
		System.out.println( "Hello World!" );
        KlsInSamePackage k1 = new KlsInSamePackage();
        k1.num = 1;
        k1.str = "hello";
        
        System.out.printf( "%s, %d%n", k1.str, k1.num );
        
        //PublicKls p1 = new PublicKls();
        //p1.call();
        
        int num1 = 10;
        int num2 = 10;
        System.out.printf("a == b %b%n", (num1 == num2));
        // System.out.printf("a.equals(b) %b%n", (num1.equals(num2)));
        String a = "10";
        String b = "10";
        System.out.printf("a == b %b%n", (a == b));
        System.out.printf("a.equals(b) %b%n", (a.equals(b)));
        int[] ary1 = {10};
        int[] ary2 = {10};
        System.out.printf("ary1 == ary2 %b%n", (ary1 == ary2));
        System.out.printf("ary1.equals(ary2) %b%n", (ary1.equals(ary2)));
	}
}

class KlsInSamePackage
{
	String str;
	int num;
}
