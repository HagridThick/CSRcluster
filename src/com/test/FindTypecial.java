package com.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class FindTypecial {

	static private int typecialnumber = 5;

	@SuppressWarnings("null")
	public static void fineTypecialQuestion(String filefloderpath,
			String keywordspath, String outputpath) throws IOException {

		File file = new File(keywordspath);
		InputStreamReader read = new InputStreamReader(
				new FileInputStream(file));
		BufferedReader bufferedReader = new BufferedReader(read);
		String line = null;
		String[] temp = null;
		String[] temp2 = null;
		String temp3[] = { "0", "0", "0","0","0" };
		int count = 0;

		HashMap<Integer, String[]> map = new HashMap<Integer, String[]>();

		while ((line = bufferedReader.readLine()) != null) {
			line = bufferedReader.readLine();
			temp = line.split(" ");
			count = count + 1;
			for (int j = 0; j < typecialnumber && j<temp.length; j++) {
				temp2 = temp[j].split("=");
				temp3[j] = temp2[0];
			}
			map.put(count, temp3.clone());		
		}
		
		File file2 = new File(filefloderpath);
		File[] tempList = file2.listFiles();
		System.out.println("该目录下对象个数：" + tempList.length);
		BufferedWriter bufWriter = new BufferedWriter(
				new FileWriter(outputpath));
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				bufWriter.write("-----------" + tempList[i] + "-----关键词:----");
				String temp4[]=map.get(i+1);
				for(int t=0;t< typecialnumber ;t++){
					bufWriter.write(temp4[t]+"  ");
				}
				bufWriter.newLine();
				// System.out.println(tempList[i]);
				File file3 = new File(tempList[i].getPath());
				InputStreamReader read2 = new InputStreamReader(
						new FileInputStream(file3),"UTF-8");
				// System.out.println(tempList[i]);
				BufferedReader bufferedReader2 = new BufferedReader(read2);
				String line2 = null;
				while ((line2 = bufferedReader2.readLine()) != null) {
					if (new_containstr(line2, map.get(i + 1))) {
						bufWriter.write(line2);
						bufWriter.newLine();
						System.out.println(line2);
					}
				//	System.out.println(line2);
				}
			}
			bufWriter.newLine();
		}
		bufWriter.close();
	}

	public boolean containstr(String str, String[] s) {
		boolean flag = false;
		for (int i = 0; i < s.length; i++) {
			if (str.contains(s[i])) {
				flag = true;
				// System.out.println("字符串"+s[i]+"在"+"指定字符串str中!");
			} else {
				flag = false;
				break;
				// System.out.println("字符串"+s[i]+"不在"+"指定字符串str中!");
			}
		}
		return flag;
	}
	
	public static boolean new_containstr(String str,String[] s){
		int count =0;
		for (int i = 0; i < s.length; i++) {
			if (str.contains(s[i])) {
				count=count+1;
				// System.out.println("字符串"+s[i]+"在"+"指定字符串str中!");
			} 
		}
		if(count+2>= typecialnumber){
			return true;
		}
		else
			return false;
	}
	

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		FindTypecial ft = new FindTypecial();
		// String s = "123456";
		// String s2[] = {"4","2"};
		// System.out.println(ft.containstr(s, s2));
//		ft.fineTypecialQuestion("5", "new簇关键词提取5", "new热点句子5");
		ft.fineTypecialQuestion("10", "new簇关键词提取101", "new热点句子10");
//		ft.fineTypecialQuestion("10", "new簇关键词提取10", "new热点句子101");

	}

}
