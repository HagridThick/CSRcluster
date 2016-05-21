package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class Process {

	public void OrderProcess(String categoryfile, String classnamefile,
			String keywordfile, String outputfile, String uselessfile)
			throws IOException {
		File file = new File(categoryfile);
		File[] filelist = file.listFiles();
		System.out.println("该目录下的文件个数：" + filelist.length);

		List<String> allque = new ArrayList<String>();
		for (int i = 0; i < filelist.length; i++)// 每个txt文件
		{
			String bigcla = null;// 大类
			String smallcla = null;// 小类
			if (filelist[i].isFile()) {
				// 找出此类属于的大类和小类
				List<String> cnamelist = IOHandle
						.getListFromFile1(classnamefile);
				List<String> que = IOHandle.getListFromFile1(filelist[i]
						.getPath());// 每个类txt文件中的问题list
				String filen = filelist[i].toString();
				String[] filenn = filen.split("\\\\|\\+");// 获取txt文件类号
//				 System.out.println(filenn[2]);
				for (int j = 0; j < cnamelist.size() - 2; j++) {
					String[] cnl = cnamelist.get(j).split("\\|\\&\\|");
					String cc = cnl[0];
					String[] xh = cc.split("\\\\|\\+");
					if (filenn[2].equals(xh[1]))// 类的序号与类名中的序号匹配
					{
						System.out.println(filenn[2]+"   "+xh[1]);
						bigcla = cnl[2];
						smallcla = cnl[1];
					}
				}

				// 读取类的关键词
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(keywordfile), "UTF-8");
				BufferedReader br = new BufferedReader(read);
				int line = 2 * (i + 1);
				int num = 0;
				String keyword = null;
				while (num < line) {
					keyword = br.readLine();
					num++;
				}
				// 找出每个问题的关键词
				for (String perque : que)// 对于每个问题
				{
					String ttemp = null;
					String[] string = perque.split("\\|\\&\\|");
					/*
					 * if(string.length>1) {
					 */
					String str = string[1];// 得到问题

					String regex1 = "(([0-9]{4}\\s+){4}[0-9]{3})|([a-zA-Z0-9]{7,})|([0-9]{4}|[0-9]{2})(-|\\/|\\.)[0-9]{1,2}(-|\\/|\\.)[0-9]{1,2}|[0-9]{4}年[0-9]{1,2}月[0-9]{1,2}(日|号|)|[0-9]{1,2}月[0-9]{1,2}(日|号|)|(([0-9]+\\.)+)";// 匹配纯7位以上英文或数字，日期，IP地址的正则表达式
					String regex2 = "\\p{P}";// 标点符号
					String regex3 = "\\s+|\\n";// 空格
					str = str.replaceAll(regex1, "");
					str = str.replaceAll(regex2, " ");
					str = str.replaceAll(regex3, " ");
					// System.out.println(str);

					StringReader sr = new StringReader(str);
					IKSegmenter ik = new IKSegmenter(sr, true);
					Lexeme lex = null;
					String temp = null;
					// 获取问题所有词放入keylist
					List<String> keylist = new ArrayList<String>();
					while ((lex = ik.next()) != null) {
						temp = lex.getLexemeText();
						if (temp.length() >= 2) {
							temp.toUpperCase();
							if (keylist.contains(temp))
								continue;
							keylist.add(temp);
						}
					}
					String[] keyword1 = keyword.split(" ");// 类关键词数组
					int a = Integer.MAX_VALUE;
					// String ttemp=null;
					// 找出问题关键词
					for (String perkey : keylist)// 每个问题中的词
					{
						for (int n = 0; n < keyword1.length; n++)// 遍历类关键词数组
						{
							String[] sss = keyword1[n].split("=");
							if (sss[0].equals(perkey)) {
								if (n < a) {
									a = n;
									ttemp = perkey;
								}
							}
						}
					}
					if (str.trim().length() > 0 && keylist.size() != 0)// 问题不是长字符串，分词之后的词不为空
					{
						allque.add(string[0] + "|&|" + bigcla + "|&|"
								+ smallcla + "|&|" + ttemp);
					} else {
						allque.add(string[0] + "|&|无效类|&|无效类|&|" + ttemp);
					}
					/*
					 * } else { allque.add(string[0]+"|&|无效类|&|无效类|&|"+ttemp); }
					 */
				}
			}
			// System.out.println("file"+filelist[i]+"finished!");
		}
		List<String> uselesslist = IOHandle.getListFromFile1(uselessfile);
		for (String perline : uselesslist) {
			String[] linelist = perline.split("\\|\\&\\|");
			allque.add(linelist[0] + "|&|无效类|&|无效类|&|null");
		}
		IOHandle.writeListToFile(allque, outputfile);
		System.out.println("finished!");
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		/**
		 * filefloderpath 生成类的文件夹 classname 类名文件 keywordspath 关键词文件
		 * alldataResult 所有数据结果文件
		 */

		String filefloderpath = "resource/10";
		String keywordspath = "resource/关键词";
		String classname = "resource/Classname.txt";
		String alldataResult = "resource/全部数据的单独分类.txt";
		String uselessfile = "resource/无效类.txt";

		Process pro = new Process();
		pro.OrderProcess(filefloderpath, classname, keywordspath,
				alldataResult, uselessfile);
	}

}
