package com.sun.demo.util;

import java.io.UnsupportedEncodingException;

/**
 * 十六进制转汉字
 */
public class CodeUtil {

	public static String enUnicode(String content) {// 将汉字转换为16进制数
		StringBuilder enUnicode = new StringBuilder();
		for (int i = 0; i < content.length(); i++) {
			enUnicode.append("%").append(Integer.toHexString(content.charAt(i)).toUpperCase());
		}
		return enUnicode.toString();
	}

	
	public static String deUnicode(String content){
		if("".equals(content) || content == null)return "";
		String[] contents = content.split("%");
		StringBuilder sb = new StringBuilder();
		for (String string : contents) {
			if ("".equals(string))
				continue;
			sb.append(String.valueOf((char) Integer.valueOf(string, 16).intValue()));
		}
		String str = "";
		try {
			 str = new String(sb.toString().getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String content = "%E7%89%A7%E7%A5%9E%E8%AE%B0";
		String[] contents = content.split("%");
		StringBuilder sb = new StringBuilder();
		for(String string : contents){
			if ("".equals(string))
				continue;
			int num = change(string.toLowerCase());
			String str = String.valueOf((char)num);
			sb.append(str);
		}
		String str = null;
		str = new String(sb.toString().getBytes("ISO-8859-1"), "UTF-8");
		System.out.println(str);
		
		System.out.println(deUnicode(content));
		
		String str1 = "牧神记";
		str1 = new String(str1.getBytes("UTF-8"), "ISO-8859-1");
		System.out.println(enUnicode(str1));
	}
	
	//16进制转10进制
	public static int change(String word){
		String[] nums = word.split("");
		int sum = 0;
		for (int i = 0; i < nums.length; i++) {
			if("a".equals(nums[i])){
				sum = (sum+10)*16;
			}else if("b".equals(nums[i])){
				sum = (sum+11)*16;
			}else if("c".equals(nums[i])){
				sum = (sum+12)*16;
			}else if("d".equals(nums[i])){
				sum = (sum+13)*16;
			}else if("e".equals(nums[i])){
				sum = (sum+14)*16;
			}else if("f".equals(nums[i])){
				sum = (sum+15)*16;
			}else{
				sum = (sum+Integer.valueOf(nums[i]))*16;
			}
		}
		return sum/16;
	}
}
