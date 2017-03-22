package example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// 要验证的字符串
	    String str = ".//*[text()="+"?"+"]//parent::*//td//input";
	    // 邮箱验证规则
//	    String regEx = "*?*";
//	    // 编译正则表达式
//	    Pattern pattern = Pattern.compile(regEx);
//	    // 忽略大小写的写法
//	    // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
//	    Matcher matcher = pattern.matcher(str);
//	    // 字符串是否与正则表达式相匹配
//	    boolean rs = matcher.matches();
//	    System.out.println(rs);
	    String ddd="";
	    if(str.contains("?")){
	    		System.out.println("in");
	    		 ddd=str.replace("?", "abc");
	    }
	    String abc="stabckd";
	    String bcd=abc.replaceAll("s", "SS");
	    
	    System.out.println(str);
	    System.out.println(bcd);
	    System.out.println(abc);
	    System.out.println(ddd);
	    

	}

}
