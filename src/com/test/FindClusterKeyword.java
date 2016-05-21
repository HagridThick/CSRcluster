package com.test;


import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;



public class FindClusterKeyword {

	// 找到每个簇的关键词，给每个簇打上标签，找到这个簇中的热点语句
	public static void conclusionKeyWords(String filefloderpath, String outputresult)
			throws IOException {
		File file = new File(filefloderpath);
		File[] tempList = file.listFiles();
		System.out.println("该目录下对象个数：" + tempList.length);
		BufferedWriter bufWriter = new BufferedWriter(new FileWriter(
				outputresult));
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				 System.out.println("文     件："+tempList[i]);
				bufWriter.write("-----------" + tempList[i] + "---------");
				bufWriter.newLine();
				File file2 = new File(tempList[i].getPath());
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file2), "UTF-8");
				BufferedReader bufferedReader = new BufferedReader(read);
				String line = null;

				HashMap<String, Integer> map = new HashMap<String, Integer>();

				while ((line = bufferedReader.readLine()) != null) {
					line=stringremoveNumberAndSign(line);
					StringReader sr = new StringReader(line);				
					IKSegmenter ik = new IKSegmenter(sr, true);
					Lexeme lex = ik.next();
					lex.getLexemeText();
					String temp = null;
					while ((lex = ik.next()) != null) {
						temp = lex.getLexemeText();
						System.out.println(temp);
						if (temp.length() >= 2) {
							if (map.containsKey(temp))
								map.put(temp, map.get(temp) + 1);
							else
								map.put(temp, 1);
						}
					}
				}
				ArrayList<Map.Entry<String, Integer>> infoIds = new ArrayList<Map.Entry<String, Integer>>(
						map.entrySet());
				Collections.sort(infoIds,
						new Comparator<Map.Entry<String, Integer>>() {
							public int compare(Map.Entry<String, Integer> o1,
									Map.Entry<String, Integer> o2) {
								return (o2.getValue() - o1.getValue());
							}
						});
				 for (int j = 0; j < infoIds.size(); j++) {
					String id = infoIds.get(j).toString();
					bufWriter.write(id + " ");
				}
			}
			bufWriter.newLine();
			/*
			 * if (tempList[i].isDirectory()) {
			 * System.out.println("文件夹："+tempList[i]); }
			 */
		}
		bufWriter.close();
	}
	
	private static String stringremoveNumberAndSign(String inputstring) {

		String regex1 = "(([0-9]{4}\\s+){4}[0-9]{3})|([a-zA-Z0-9]{7,})|([0-9]{4}|[0-9]{2})(-|\\/|\\.)[0-9]{1,2}(-|\\/|\\.)[0-9]{1,2}|[0-9]{4}年[0-9]{1,2}月[0-9]{1,2}(日|号|)|[0-9]{1,2}月[0-9]{1,2}(日|号|)|(([0-9]+\\.)+)";// 匹配纯7位以上英文或数字，日期，IP地址的正则表达式
		String regex2 = "\\p{P}";// 标点符号
		String regex3 = "\\s+|\\n";// 空格
		String outstring = null;
		outstring = inputstring.replaceAll(regex1, "");
		outstring = outstring.replaceAll(regex2, " ");
		outstring = outstring.replaceAll(regex3, " ");
		return outstring;
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		FindClusterKeyword fckw = new FindClusterKeyword();
//		fckw.conclusionKeyWords("5", "new簇关键词提取5");
//		fckw.conclusionKeyWords("5", "new簇关键词提取51");
		fckw.conclusionKeyWords("10", "new簇关键词提取101");
//		fckw.conclusionKeyWords("10", "new簇关键词提取10");
//		fckw.conclusionKeyWords("15", "new簇关键词提取15");
//		fckw.conclusionKeyWords("20", "new簇关键词提取20");
//		fckw.conclusionKeyWords("25", "new簇关键词提取25");
//		fckw.conclusionKeyWords("30", "new簇关键词提取30");
//		
		
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("d", 2);
		map.put("c", 1);
		map.put("b", 1);
		map.put("a", 3);

		ArrayList<Map.Entry<String, Integer>> infoIds = new ArrayList<Map.Entry<String, Integer>>(
				map.entrySet());

		// 排序前
		for (int i = 0; i < infoIds.size(); i++) {
			String id = infoIds.get(i).toString();
			System.out.println(id);
		} // d 2 //c 1 //b1 //a 3

		// 排序
		Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1,
					Map.Entry<String, Integer> o2) {
				return (o2.getValue() - o1.getValue()); // return(o1.getKey()).toString().compareTo(o2.getKey());
			}
		});

		// 排序后
		for (int i = 0; i < infoIds.size(); i++) {
			String id = infoIds.get(i).toString();
			System.out.println(id);
		}

	}

}

