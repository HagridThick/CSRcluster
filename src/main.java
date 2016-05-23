import java.io.IOException;

import com.joy.cluster.ClusterByKMeans;
import com.test.Clusterresult;
import com.test.CreateTFIDFMatrix;
import com.test.DeleteSymbol;
import com.test.FindClusterKeyword;
import com.test.FindK;
import com.test.FindTypecial;
import com.test.Process;

public class main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		int K = 0;
		String datasource="resource/2月test1W.txt";
		String matrixPath = "resource/2月test1W+TFIDF矩阵.txt";
		String uselessClass = "resource/2月test1W无效类.txt";		
		String managedData = "resource/2月test1WmanagedData.txt";
		String Ksource="resource/k.txt";//存放K的取值的文本
		
	//	DeleteSymbol ds=new DeleteSymbol();
	//	ds.deletesymbol(datasource);
	//  CreateTFIDFMatrix cx = new CreateTFIDFMatrix();
	//	cx.OneGo(datasource,matrixPath,uselessClass,managedData);
			
		String CSRPath = "resource/2月test1Wcsr矩阵";
		String clusterResult="resource/cluster";
		FindK f = new FindK();
//	    ClusterByKMeans.createCSR(matrixPath, CSRPath);
//		K=f.findkbySilhouette(Ksource, CSRPath,clusterResult);	    //数据少时可以选用此方法计算（1万条左右）
//		K=f.findkbyInertia(Ksource, CSRPath,clusterResult);         //数据多时可以选用此方法计算
		
       
		
		String clusterResultPath=clusterResult+K+".txt";
		String filefloderpath="resource/2月test1WK";
		String keywordspath="resource/2月test1W关键词";
		String outputpath="resource/2月test1W热点问题";
		//找到对应问题存入文件夹
		 K=25;
	//	Clusterresult.clusterresult(managedData, K,clusterResultPath, filefloderpath);		
	//   FindClusterKeyword.conclusionKeyWords(filefloderpath, keywordspath);
	//	FindTypecial.fineTypecialQuestion(filefloderpath, keywordspath, outputpath);
		
				
		String classname="resource/2月test1WClassname.txt";
		String alldataResult="resource/2月test1W全部数据的单独分类.txt";
		Process pro=new Process();
		pro.OrderProcess(filefloderpath,classname,keywordspath,alldataResult,uselessClass);		
	}
}
