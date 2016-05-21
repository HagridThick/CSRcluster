package com.test;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author joy
 * 正则表达式
 */
public class RegexUtil {

	/**
	 * 使用正则匹配目标字符串，返回是否匹配成功
	 * @param dealStr 目标字符串
	 * @param regexStr 正则字符串 
	 * @return 匹配结果
	 */
	public static boolean getResult(String dealStr,String regexStr){
		if(dealStr == null||regexStr ==null)
			return false;
		Pattern pattern = Pattern.compile(regexStr,Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
		Matcher matcher = pattern.matcher(dealStr);
		while(matcher.find()){
			return true;
		}
		return false;
	}
	/**
	 * 使用正则匹配目标字符串，返回第一次匹配结果且结果类型为String
	 * @param dealStr 目标字符串
	 * @param regexStr 正则字符串 
	 * @return 第一次匹配结果
	 */
	public static String getFirstString(String dealStr,String regexStr){
		if(dealStr == null||regexStr ==null )
			return "";
		Pattern pattern = Pattern.compile(regexStr,Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
		Matcher matcher = pattern.matcher(dealStr);
		while(matcher.find()){
			return matcher.group().trim();
		}
		return "";
	}
	/**
	 * 使用正则匹配目标字符串，返回所有匹配结果且结果类型为String
	 * @param dealStr 目标字符串
	 * @param regexStr 正则字符串 
	 * @return 所有匹配结果List
	 */
	public static List<String> getList(String dealStr,String regexStr){
		List<String> list = new ArrayList<String>();
		if(dealStr == null||regexStr ==null)
			return list;
		Pattern pattern = Pattern.compile(regexStr,Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
		Matcher matcher = pattern.matcher(dealStr);
		while(matcher.find()){
			list.add(matcher.group().trim());
		}
		return list;
	}
	/**
	 * 使用正则匹配目标字符串，返回所有匹配结果且结果类型为String[]
	 * @param dealStr 目标字符串
	 * @param regexStr 正则字符串 
	 * @param array 目标字符串在正则中的位置 数组
	 * @return 所有匹配结果List 一个字符串匹配多个结果
	 */
	public static List<String[]> getList(String dealStr,String regexStr,int[] array){
		List<String[]> list = new ArrayList<String[]>();
		if(dealStr == null||regexStr ==null ||array == null)
			return list;
		for(int i= 0;i<array.length;i++)
		{
			if(array[i]<1)
				return list;
		}
		Pattern pattern = Pattern.compile(regexStr,Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
		Matcher matcher = pattern.matcher(dealStr);
		while(matcher.find()){
			String [] ss = new String[array.length];
			for(int i = 0;i < array.length; i++){
				ss[i] = matcher.group(array[i]).trim();
			}
			list.add(ss);
		}
		return list;
	}
	public static String removeString(String dealStr,String regexStr){
		if(dealStr == null||regexStr ==null)
			return "";
		Pattern pattern = Pattern.compile(regexStr,Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
		Matcher matcher = pattern.matcher(dealStr);
		while(matcher.find()){
			dealStr = dealStr.replace(matcher.group(), " ");
		}
		return dealStr;
	}
	public static String replaceDBC2SBC(String input) {
	    Pattern pattern = Pattern.compile("[\u3000\uff01-\uff5f]{1}");

	    Matcher m = pattern.matcher(input);
	    StringBuffer s = new StringBuffer();
	    while (m.find()) {
	        char c = m.group(0).charAt(0);
	        char replacedChar = c == '　' ? ' ' : (char) (c - 0xfee0);
	        m.appendReplacement(s, String.valueOf(replacedChar));
	    }

	    m.appendTail(s);

	    return s.toString();
	}
	public static boolean findDBC(String input) {
	    Pattern pattern = Pattern.compile("[\u3000\uff01-\uff5f]{1}");
	    Matcher m = pattern.matcher(input);
	    while (m.find()) {
	    	return true;
	    }
	    return false;
	}
}
