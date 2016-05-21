package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IOHandle {

	/**
	 * string持久化到txt文件中 
	 * 
	 * @param string
	 *            分词后词实体
	 * @param filePath
	 *            存入的文件路径
	 * @throws IOException
	 */
	public static void writeStringToFile(String string, String filePath) {
		try {
			FileWriter fw = new FileWriter(filePath);
			fw.write(string);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * StringList持久化到txt文件中 
	 * 
	 * @param list
	 *            字符串list
	 * @param filePath
	 *            存入的文件路径
	 * @throws IOException
	 */
	public static void writeListToFile(List<String> list, String filePath) {
		StringBuilder sb = new StringBuilder();
		for(String str:list){
			sb.append(str+"\r\n");
		}
		writeStringToFile(sb.toString(),filePath);
	}
	/**
	 * StringList持久化到文件夹中
	 * @param list
	 * @param filefloderpath
	 */
	public static void writeListToFilefloder(List<String> list,String filePath,String filefloderpath){
		File file = new File(filefloderpath);
		if(!file.exists()&&!file.isDirectory()){
			file.mkdir();
		}
		
		StringBuilder sb = new StringBuilder();
		for(String str:list){
			sb.append(str+"\r\n");
		}
		writeStringToFile1(sb.toString(),filePath,filefloderpath);
	}

	
	/**
	 * 写入文件夹
	 * @param string
	 * @param filePath
	 * @param filefloderpath
	 */
	private static void writeStringToFile1(String string, String filePath, String filefloderpath) {
		// TODO Auto-generated method stub
		File file=new File(filefloderpath,filePath);
		try {
			FileOutputStream out=new FileOutputStream(file);
			try {
				FileWriter fw = new FileWriter(file);
				fw.write(string);
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	/**
	 * 从文件中一次性将文本以list形式读入
	 * @param filePath 源文件地址
	 * @return listString
	 */
	public static List<String> getListFromFile(String filePath) {
		List<String> list = new ArrayList<String>();
		String[] strArray = getStringFromFile(filePath).split("\\s+|\r\n|\n");
//		int number =1;
	//	String[] strArray = getStringFromFile(filePath).split("\\s+|\r\n|\r\n|\n");
		for(String str:strArray){
	//		number++;
		//	if(number%2==0){
				list.add(str);
			//}
		}
		return list;
	}
	/**
	 * 从文件中一次性将文本以list形式读入（以回车分隔）
	 * @param filePath 源文件地址
	 * @return listString
	 */
	public static List<String> getListFromFile1(String filePath) {
		List<String> list = new ArrayList<String>();
		String[] strArray = getStringFromFile(filePath).split("\r\n|\n");
		for(String str:strArray){
			list.add(str);
		}
		return list;
	}
	
	/**
	 * 从文件中一次性将文本以list形式读入,空格大于2个时才换行
	 * @param filePath 源文件地址
	 * @return listString
	 */
	public static List<String> getListFromFile2(String filePath) {
		List<String> list = new ArrayList<String>();
		String[] strArray = getStringFromFile(filePath).split("\\s\\s+|\r\n|\n");
		for(String str:strArray){
			list.add(str);
		}
		return list;
	}
	/**
	 * 从文件中一次性将文本以String形式读入
	 * 
	 * @param filePath
	 *            源文件地址
	 * @return String类型的文本内容
	 * @throws IOException
	 */
	public static String getStringFromFile(String filePath) {
		String str = "";
		try {
			File file = new File(filePath);
			FileInputStream in = new FileInputStream(file);
			// size 为字串的长度 ，这里一次性读完
			int size = in.available();
			byte[] buffer = new byte[size];
			in.read(buffer);
			in.close();
			str = new String(buffer, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	
}
