package integration;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.joy.cluster.ClusterByKMeans;
import com.test.Clusterresult;
import com.test.CreateTFIDFMatrix;
import com.test.DeleteSymbol;
import com.test.FindClusterKeyword;
import com.test.FindK;
import com.test.FindTypecial;
import com.test.Process;

public class All {
	String path ="";
	int K=0;
	
	public All(String floder_path){
		path=floder_path;
	}
	
	
	
	
	public void three_in_one(boolean i) throws UnsupportedEncodingException, IOException{
		first_step();
		second_step(i);
	    third_step();
	}
	
	
	
	public void first_step() throws UnsupportedEncodingException, IOException{
		String datasource = path+"/DataSource.txt";
		String matrixPath = path+"/TFIDF.txt";
		String uselessClass = path+"/无效类.txt";
		String managedData = path+"/去除无效类的DataSource.txt";
		String Ksource=path+"/k.txt";//存放K的取值的文本
		
		DeleteSymbol ds=new DeleteSymbol();
	    ds.deletesymbol(datasource);
		CreateTFIDFMatrix cx = new CreateTFIDFMatrix();
		cx.OneGo(datasource,matrixPath,uselessClass,managedData);
        System.out.println("first_step:矩阵计算已完成");
	}
	
	public void second_step(Boolean i) throws IOException{
		String datasource = path+"/DataSource.txt";
		String matrixPath = path+"/TFIDF.txt";
		String uselessClass = path+"/无效类.txt";
		String managedData = path+"/去除无效类的DataSource.txt";
		String Ksource=path+"/k.txt";//存放K的取值的文本
		
		String CSRPath = path+"/csr矩阵";
		String clusterResult=path+"/cluster";
		FindK f = new FindK();
	    ClusterByKMeans.createCSR(matrixPath, CSRPath);
	    if(i==true){
			K=f.findkbySilhouette(Ksource, CSRPath,clusterResult);	    //数据少时可以选用此方法计算（1万条左右）
	    }
	    else{
			K=f.findkbyInertia(Ksource, CSRPath,clusterResult);         //数据多时可以选用此方法计算
	    }
        System.out.println("second_step:聚类计算以及最佳K选取已完成");
	} 
	
	public void third_step() throws IOException{
		String managedData = path+"/去除无效类的DataSource.txt";
		String clusterResult=path+"/cluster";
		
		String clusterResultPath=clusterResult+K+".txt";
		String filefloderpath=path+"/DataDivideByK";
		String keywordspath=path+"/KeyWords关键词.txt";
		String outputpath=path+"/HotQuestions热点问题.txt";
		//找到对应问题存入文件夹
		Clusterresult.clusterresult(managedData, K,clusterResultPath, filefloderpath);		
	    FindClusterKeyword.conclusionKeyWords(filefloderpath, keywordspath);
		FindTypecial.fineTypecialQuestion(filefloderpath, keywordspath, outputpath);
        System.out.println("third_step:按照最佳K划分+关键词+热点问题已完成");
	}
	
	public void fourth_step() throws IOException{
		String classname=path+"/Classname.txt";
		String alldataResult=path+"/带类别的DataSource.txt";
		String filefloderpath=path+"/DataDivideByK";
		String keywordspath=path+"/KeyWords关键词.txt";
		String uselessClass = path+"/无效类.txt";

		Process pro=new Process();
		pro.OrderProcess(filefloderpath,classname,keywordspath,alldataResult,uselessClass);		
        System.out.println("fourth_step:给数据源全部问题打类别已完成");

	}
	
	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		// use case
		/*创建一个 文件夹，在All初始化中，指定这个文件夹
		    在其中放置
		  DataSource.txt
		  k.txt
		 */
		All for_test = new All("Testresource");
//		for_test.three_in_one(true);
  //  	for_test.first_step();
  //    for_test.second_step(true);
  //    for_test.third_step();
		
		// 放置入Classname.txt
        for_test.fourth_step();
	}

}
