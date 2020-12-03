package application;

import java.io.File;
import java.util.regex.Pattern;

public class Test {
	
	File gf = new File(getClass().getResource("No_Image.png").toExternalForm());
	public static void main(String[] args) {
//		Test po = new Test();
//		System.out.println(po.gf.canWrite());
		String po = "47Am7ar";
		for(int a=0;a<5;a++) {
			if(Pattern.matches("[0-9]", String.valueOf(po.charAt(a)))) {continue;}
			else {po = po.substring(a, po.length());break;}
		}
//		String b="";
//		String h = "amar".substring(1, 4);
		System.out.println(po);
//		System.out.println(h[h.length-1]);
//		for(String hs : h) {
//			if(hs.equals(h[0])) {
//				continue;
//			}
//			b += ","+hs;
//		}
//		System.out.println(b);
	}
}
