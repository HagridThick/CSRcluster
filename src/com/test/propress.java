package com.test;


import java.util.ArrayList;
import java.util.List;


public class propress {

	private static String sourcefile = "resources/testFiles/test.txt";
	private static String getsourcefile = "resources/testFiles/getsource.txt";
	private static String getfile = "resources/testFiles/get.txt";
	private static String nofile = "resources/testFiles/3月工单编号.txt";
	private static String getno = "resources/testFiles/8Wno.txt";
	
	private static void removeNumberAndSign(){
		String source = IOHandle.getStringFromFile(sourcefile);
		List<String> noList = IOHandle.getListFromFile(nofile);
		List<String> getSource =new ArrayList<String>();
		List<String> get =new ArrayList<String>();
		List<String> no =new ArrayList<String>();
		String regex1 = "(([0-9]{4}\\s+){4}[0-9]{3})|([a-zA-Z0-9]{7,})|([0-9]{4}|[0-9]{2})(-|\\/|\\.)[0-9]{1,2}(-|\\/|\\.)[0-9]{1,2}|[0-9]{4}年[0-9]{1,2}月[0-9]{1,2}(日|号|)|[0-9]{1,2}月[0-9]{1,2}(日|号|)|(([0-9]+\\.)+)";// 匹配纯7位以上英文或数字，日期，IP地址的正则表达式 
		String regex2 = "\\p{P}";// 标点符号
		String regex3 = "\\s+|\\n";//空格
		String string=null;
		String[] arr = source.split("\\r\\n");
		int i = 0;
		for(String str:arr){
			string = str;
			string = string.replaceAll(regex1, "");
			string = string.replaceAll(regex2, " ");
			string = string.replaceAll(regex3, " ");
			if(string.trim().length()>0){
//				System.out.println(string);
				getSource.add(str.replaceAll("\\s+|\\n\\r|\\r\\n|\\n", " "));
				get.add(string);
				no.add(noList.get(i));
			}
			i++;
		}
		IOHandle.writeListToFile(getSource, getsourcefile);
		IOHandle.writeListToFile(get, getfile);
		IOHandle.writeListToFile(no, getno);
		
	}
	public static void main(String[] args) {
		removeNumberAndSign();
	}

}
