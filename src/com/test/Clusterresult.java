package com.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Clusterresult {
	/**
	 * 问题编号与问题内容在一个文件里
	 * @param alldatafile
	 * @param clusterfile
	 */
	public static void clusterresult(String alldatafile,int k,String clusterfile,String filefloderpath){

		List<String> sourcelist=IOHandle.getListFromFile1(alldatafile);
		float amount=sourcelist.size();	
		List<String> catelist=IOHandle.getListFromFile1(clusterfile);
		//对于每一个类
		for(int i=0;i<k;i++){
			List<String> list=new ArrayList<String>();
			List<String> ss=new ArrayList<String>();
			//
			for(String str:catelist){
				String[] st=str.split(",");
				String id =st[0];
				String category=st[1];				
				if(Integer.parseInt(category)==i){
					list.add(id);//list为第i类的所有问题行号
				}
			}			
			for(String s:list){			
				int id=Integer.parseInt(s);
				String content=sourcelist.get(id);
				ss.add(content);
			}
			float thisamount=ss.size();
			
	        NumberFormat numberFormat = NumberFormat.getInstance();   
	        numberFormat.setMaximumFractionDigits(2); 
			String result = numberFormat.format((float)thisamount / (float)amount * 100);
			String filePath=String.valueOf(i)+"+"+result+"%";
			IOHandle.writeListToFilefloder(ss, filePath,filefloderpath);
		}
	}
	
}
