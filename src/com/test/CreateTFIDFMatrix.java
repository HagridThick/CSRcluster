package com.test;

import java.lang.Math;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class CreateTFIDFMatrix {

	/*
	 * 考虑到是短文本，不对词频作特殊处理，一个词在一个句子中出现的次数就作为词频TF log(句子总数/包含该词的句子数+1)
	 * 作为逆文档频率IDF，+1用于防止0的出现 nubmer 代表句子总数 Word_Sentence 作为统计一个词对应的句子数
	 * 计算出的矩阵结果取小数点后4位
	 */
	private Double number = (double) 0;
	private HashMap<String, Double> Word_Sentence = new HashMap<String, Double>();

	/*
	 * // 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1 ,用于过滤掉文档中小于5个字的句子 public static
	 * boolean isLetter(char c) { int k = 0x80; return c / k == 0 ? true :
	 * false; } public static int length(String s) { if (s == null) return 0;
	 * char[] c = s.toCharArray(); int len = 0; for (int i = 0; i < c.length;
	 * i++) { len++; if (!isLetter(c[i])) { len++; } } return len; }
	 */
	// 得到 词对应的句子数
	public void OneGo(String datasource,String matrixpath,String uselesspath,String managedData) throws IOException{
		CountIDF(datasource,false);
		createMatrixIDF(datasource,matrixpath,uselesspath,false);
		wipeUseless(datasource,uselesspath,managedData);
	}
	
	public String pre_treatment(String in) {
		String regex1 = "(([0-9]{4}\\s+){4}[0-9]{3})|([a-zA-Z0-9]{7,})|([0-9]{4}|[0-9]{2})(-|\\/|\\.)[0-9]{1,2}(-|\\/|\\.)[0-9]{1,2}|[0-9]{4}年[0-9]{1,2}月[0-9]{1,2}(日|号|)|[0-9]{1,2}月[0-9]{1,2}(日|号|)|(([0-9]+\\.)+)";// 匹配纯7位以上英文或数字，日期，IP地址的正则表达式
		String regex2 = "\\p{P}";// 标点符号
		String regex3 = "\\s+|\\n";// 空格
		in = in.replaceAll(regex1, "");
		in = in.replaceAll(regex2, " ");
		in = in.replaceAll(regex3, " ");
		return in;
	}

	public void CountIDF(String filepath, boolean smart) {
		try {
			File file = new File(filepath);
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file));
				BufferedReader bufferedReader = new BufferedReader(read);
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					String temp2[] = line.split("\\|");
					// if (length(line) > 10) {
					if (temp2.length > 2) {
						if (!temp2[2].trim().equals("")) {
							number = number + 1;
							StringReader sr;
							sr = new StringReader(pre_treatment(temp2[2]));
							IKSegmenter ik = new IKSegmenter(sr, smart);
							Lexeme lex = null;
							String temp = null;
							while ((lex = ik.next()) != null) {
								temp = lex.getLexemeText();
								if (temp.length() >= 2) {
									temp.toUpperCase();
									if (Word_Sentence.containsKey(temp))
										Word_Sentence.put(temp,
												Word_Sentence.get(temp) + 1);// 因同一个句子出现同样地词几率较少，就不做特殊处理了
									else
										Word_Sentence.put(temp, (double) 1);
								}
								// System.out.print(lex.getLexemeText()+" ");
							}
						}
					}
					// }
					// System.out.println();
				}
				System.out.println("IDF计算已完成");
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}

	}

	public void createMatrixIDF(String filepath, String outputpath,
			String uselesspath, boolean smart) {
		try {
			File file = new File(filepath);
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file));
				BufferedReader bufferedReader = new BufferedReader(read);
				String line = null;

				BufferedWriter bufWriter = new BufferedWriter(new FileWriter(
						outputpath));

				BufferedWriter uselessWriter = new BufferedWriter(
						new FileWriter(uselesspath));
				// int count = 0
				while ((line = bufferedReader.readLine()) != null) {
					String temp2[] = line.split("\\|");
					// if (length(line) > 10) {
					// 然后再进行一遍分词，对于每一行，进行分词统计，输出序号count和词频向量 1 0 3 2 1 0 0 0
					// 1等等
					// count = count + 1;
					// bufWriter.write(String.valueOf(count));
					// bufWriter.write(" ");
					boolean has = false;// 判断分词后语句有没有用，无效则跳过矩阵
					if (temp2.length > 2) {
						if (!temp2[2].trim().equals("")) {
							HashMap<String, Double> map = new HashMap<String, Double>();
							StringReader sr;
							sr = new StringReader(pre_treatment(temp2[2]));
							IKSegmenter ik = new IKSegmenter(sr, smart);
							Lexeme lex = null;
							String temp = null;
							while ((lex = ik.next()) != null) {
								has = true;
								temp = lex.getLexemeText();
								if (temp.length() >= 2) {
									temp.toUpperCase();
									if (map.containsKey(temp))
										map.put(temp, map.get(temp) + 1);
									else
										map.put(temp, (double) 1);
								}
							}

							if (has == true) {
								// 对于每个句子的词频hashmap，在全部的hash中，如果没有词，标记为0，否则标记为TF*IDF
								Iterator it = Word_Sentence.keySet().iterator();
								while (it.hasNext()) {
									String key = (String) it.next();
									if (map.containsKey(key)) {
										bufWriter.write(String.format(
												"%.4f",
												(map.get(key) * Math.log(number
														/ Word_Sentence
																.get(key))))
												+ " ");
										// System.out.println(
										// String.format("%.4f", (map.get(key) *
										// Math.log(number /
										// Word_Sentence.get(key)))));
										// System.out.println(Math.log(number/Word_Sentence.get(key)));
										// System.out.print(map.get(key)+" ");
									} else {
										bufWriter.write(0 + " ");
										// System.out.print(0+" ");
									}
								}
								bufWriter.newLine();
								has = false;
							} else {
								uselessWriter.write(line);
								uselessWriter.newLine();
							}
							// }
						} else {
							uselessWriter.write(line);
							uselessWriter.newLine();
						}
					} else {
						uselessWriter.write(line);
						uselessWriter.newLine();
					}
				}
				System.out.println("TF-IDF矩阵以及构建完成，并输出到了：" + outputpath);
				System.out.println("无效类输出到了：" + uselesspath);
				read.close();
				bufWriter.close();
				uselessWriter.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}

	}

	public void wipeUseless(String datasource, String useless,
			String managedData) throws IOException {
		ArrayList<String> uselessStrings = new ArrayList<String>();

		File file = new File(useless);
		InputStreamReader read = new InputStreamReader(
				new FileInputStream(file));
		BufferedReader bufferedReader = new BufferedReader(read);
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			uselessStrings.add(line);
		}

		read.close();

		File file2 = new File(datasource);
		InputStreamReader read2 = new InputStreamReader(new FileInputStream(
				file2));
		BufferedReader br2 = new BufferedReader(read2);
		String line2 = null;

		BufferedWriter bufWriter = new BufferedWriter(new FileWriter(
				managedData));

		while ((line2 = br2.readLine()) != null) {
			if (!uselessStrings.contains(line2)) {
				bufWriter.write(line2);
				bufWriter.newLine();
			}
		}
		read2.close();
		bufWriter.close();
		System.out.println("数据源剔除无效类完毕，输出到了 " + managedData);
	}


	public int countline(String data) throws IOException{
		File file = new File(data);
		InputStreamReader read = new InputStreamReader(
				new FileInputStream(file));
		BufferedReader bufferedReader = new BufferedReader(read);
		int i=0;
		while (bufferedReader.readLine()!= null) {
			i++;
		}
		read.close();
		System.out.println("该文件行数为: " + i);
		return i;
	}
	// public void findUseless(String matrixpath, String datapath, String
	// uselesspath) throws IOException {
	// ArrayList<Integer> use_numbers = new ArrayList<Integer>();
	// // ArrayList<Integer> useless_numbers = new ArrayList<Integer>();
	//
	// ArrayList<String> datause_lines = new ArrayList<String>();
	// ArrayList<String> datauseless_lines = new ArrayList<String>();
	// ArrayList<String> use_lines = new ArrayList<String>();
	// int count = 0;
	// // 找到无效行,将矩阵中无效行去除
	// File file = new File(matrixpath);
	// InputStreamReader read = new InputStreamReader(new
	// FileInputStream(file));
	// BufferedReader br = new BufferedReader(read);
	// String line = null;
	// boolean use = false;
	// while ((line = br.readLine()) != null) {
	// count = count + 1;
	// String temp[] = line.split(" ");
	// for (int i = 0; i < temp.length; i++) {
	// if (!temp[i].equals("0")) {
	// use = true;
	// }
	// }
	// if (use) {
	// use_lines.add(line);
	// use_numbers.add(count);
	// } // else
	// // useless_numbers.add(count);
	// }
	// BufferedWriter bufWriter = new BufferedWriter(new FileWriter(file));
	// for (int j = 0; j < use_lines.size(); j++) {
	// bufWriter.write(use_lines.get(j));
	// bufWriter.newLine();
	// }
	// bufWriter.close();
	// // 找到对应的源文件，分开有效行和无效行
	// File file2 = new File(datapath);
	// InputStreamReader read2 = new InputStreamReader(new
	// FileInputStream(file2));
	// BufferedReader br2 = new BufferedReader(read2);
	// String line2 = null;
	// int count2 = 0;
	// while ((line2 = br2.readLine()) != null) {
	// count2 = count2 + 1;
	// if (use_numbers.contains(count2)) {
	// datause_lines.add(line2);
	// } else
	// datauseless_lines.add(line2);
	// }
	// BufferedWriter bufWriter2 = new BufferedWriter(new FileWriter(file2));
	// for (int k = 0; k < datause_lines.size(); k++) {
	// bufWriter2.write(datause_lines.get(k));
	// bufWriter2.newLine();
	// }
	// bufWriter2.close();
	// // 输出无效类
	// File file3 = new File(uselesspath);
	// BufferedWriter bufWriter3 = new BufferedWriter(new FileWriter(file3));
	// for (int h = 0; h < datauseless_lines.size(); h++) {
	// bufWriter3.write(datauseless_lines.get(h));
	// bufWriter3.newLine();
	// }
	// bufWriter3.close();
	// System.out.println("无效类寻找完毕");
	// }

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// System.out.print(String.format("%.4f", 1113.1415926));
		// String test = "SX16020100095|&|现在用的光纤冷接子质量极差，能不能换的稍微好一点 ";
		// String[] test2 = test.split("\\|");
		// System.out.println(test2[0]);
		// System.out.println(test2[1]);
		// System.out.println(test2[2]);

		CreateTFIDFMatrix cx = new CreateTFIDFMatrix();
        //数据源路径，输出矩阵路径，无效类路径，去除无效类的数据源路径
	//	cx.OneGo(datasource, matrixpath, uselesspath, managedData);
		// cx.CountIDF("resource/4data", false);
		// cx.createMatrixIDF("resource/4data",
		// "resource/4data+TFIDF","resource/4data+useless", false);
	//	cx.wipeUseless("resource/4data", "resource/4data+useless","resource/4data+managed");
				cx.countline("resource/4data+TFIDF");
		// StringReader sr;
		// sr = new StringReader("帮我先处理");
		// IKSegmenter ik = new IKSegmenter(sr, false);
		// Lexeme lex = null;
		// String temp = null;
		// while ((lex = ik.next()) != null) {
		// temp = lex.getLexemeText();
		// System.out.println(temp);
		// }
		// System.out.println("done");
		// cx.CountIDF("2月份数据源.txt", false);
		// cx.createMatrixIDF("2月份数据源.txt", "2月份数据源+TFIDF矩阵.txt", false);
		// cx.findUseless("2月份数据源+TFIDF矩阵.txt", "2月份数据源.txt", "无效类.txt");
		// cx.idf("resource/2月份数据源.txt", "resource/2月份数据源+TFIDF矩阵.txt",
		// "resource/无效类.txt");
		// System.out.print("done");
		//
		// String s1 = "295306|&|   1  2   ";
		// String s2[] = s1.split("\\|");
		// // System.out.print(s2[2].trim());
		//
		// String s3 = "   ";
		// if (s3.trim().equals(""))
		// System.out.print("1");
	}

}
