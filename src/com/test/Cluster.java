package com.test;

import java.io.IOException;

public class Cluster {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub	
		
		String filefloderpath="10";
		String alldatafile="3月所有数据1.txt";
		String clusterfile="小类1.txt";
		String keywordspath="关键词";
		String outputpath="热点问题";
		int K=0;
		Clusterresult.clusterresult(alldatafile, K,clusterfile, filefloderpath);;
		FindClusterKeyword.conclusionKeyWords(filefloderpath, keywordspath);
		FindTypecial.fineTypecialQuestion(filefloderpath, keywordspath, outputpath);
	}
}
