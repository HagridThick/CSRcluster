package com.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.joy.cluster.ClusterByKMeans;

public class FindK {

	/*public int findk(String kfile, String CSRPath,String matrixPath) throws IOException {
		int final_k = 0;
		double sl=-1;
		List<String> klist = IOHandle.getListFromFile1(kfile);//获取到所有的K
		double[][] array=distancearray(matrixPath);
		//对于每个K值
		for (String kl : klist) {
			int pk = Integer.parseInt(kl);
			String tryclusterfile = "resource/try" + kl + ".txt";
			ClusterByKMeans.clusterByKMeans(pk, CSRPath, tryclusterfile);//聚类
			List<String> catelist = IOHandle.getListFromFile1(tryclusterfile);//获取聚类结果
			
//			String fil="resource/tryxh"+ kl + ".txt";
			FileWriter fw = new FileWriter(tryclusterfile);
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < pk; i++) {
//				List<String> list = new ArrayList<String>();
				for (String str : catelist) {
					String[] st = str.split(",");
					String id = st[0];
					String category = st[1];
					if (Integer.parseInt(category) == i) {
//						list.add(id);// list为第i类的所有问题行号
						bw.write(id+" ");
					}
				}
				bw.newLine();
			}
			bw.close();
			
//			System.out.println(pk);
			//计算轮廓系数
			int i,j,k,m;
			int n=0;
			double avgins;
			double a = 0;
			double total_s=0;
			double b=Double.MAX_VALUE;
			List<String> xhlist = IOHandle.getListFromFile1(tryclusterfile);//获取问题序号
			for(i=0;i<xhlist.size();i++)//遍历类
			{
				String str=xhlist.get(i);//获取这一类的所有问题序号
				String[] cus = str.split(" ");//将一个类以空格分割
				for(j=0;j<cus.length;j++)//遍历类中问题序号
				{
					double s=0;
//					int num=Integer.parseInt(cus[j])+1;
					int num=Integer.parseInt(cus[j]);				
					for(k=0;k<xhlist.size();k++)//遍历类
					{
						double nor1=0;
						String st=xhlist.get(k);
						String[] cus1 = st.split(" ");//将一个类以空格分割
						for(m=0;m<cus1.length;m++)//遍历类中问题序号
						{
//							int num1=Integer.parseInt(cus1[m])+1;
							int num1=Integer.parseInt(cus1[m]);
					//		double ins=culins(jl,jl1);
							double ins;
							if(num<num1)
							{
								ins=array[num][num1];
							}
							else
							{
								ins=array[num1][num];
							}
							nor1+=ins;//一个元素与类中所有元素的距离之和																					
						}
						if(i==k)//是同一个问题集合，就求a
						{
							if(cus1.length==1)
							{
								avgins=0;
							}
							else
							{
								avgins=nor1/(cus1.length-1);
							}					
							a=avgins;
				
						}
						else//不是同一个问题集合，就求b
						{
							avgins=nor1/(cus1.length);
							if(avgins<b)
							{
								b=avgins;
							}
						}
					}
				s=(b-a)/Math.max(a, b);//计算一个元素的轮廓系数
					n++;//统计所有类中元素个数之和
				b=Double.MAX_VALUE;
				total_s+=s;//计算所有元素的轮廓系数之和
				}
			}
			double sico=total_s/n;
//			System.out.println("n:"+n);
//			System.out.println("total_s:"+total_s);
			System.out.println("轮廓系数："+sico+"    k:"+pk);//求当前聚类的总体轮廓系数
			if(sico>sl)
			{
				sl=sico;
				final_k=pk;
			}
		}
		System.out.println("final_k:"+final_k);
		return final_k;
	}
	
	private double culins(String[] jl, String[] jl1) {
		// TODO Auto-generated method stub
		int i;
		double in=0;
		for(i=0;i<jl.length;i++)
		{
			double x=Double.parseDouble(jl[i])-Double.parseDouble(jl1[i]);
			in+=Math.pow(x, 2);
		}
		return Math.sqrt(in);
	}
	
	private double[][] distancearray(String matrixpath) throws IOException
	{long stt=System.currentTimeMillis();
		List<String> tfidflist = IOHandle.getListFromFile1(matrixpath);
		int line=tfidflist.size();
		double[][] arr=new double[line][line];
		for(int i=0;i<line;i++)
		{
			String[] num1=tfidflist.get(i).split(" ");
			for(int j=i+1;j<line;j++)
			{				
				String[] num2=tfidflist.get(j).split(" ");
				double dis=culins(num1,num2);
				arr[i][j]=dis;
			}
		}
		long ett=System.currentTimeMillis();
		long tt=ett-stt;
		System.out.println("jzt:"+tt);
		System.out.println("line:"+tfidflist.size());
		return arr;
	}
*/
	//Silhouette计算
	public int findkbySilhouette(String kfile, String CSRPath,String clusterresult) throws IOException
	{
		int final_k=0;
		double max_sil=-1;
		List<String> klist = IOHandle.getListFromFile1(kfile);//获取到所有的K
		for (String kl : klist)
		{
			int k=Integer.parseInt(kl);
			double sil=ClusterByKMeans.getSilhouetteScore(k, CSRPath,clusterresult);
			System.out.println("sil:"+sil+"  k:"+k);
			if(sil>max_sil)
			{
				max_sil=sil;
				final_k=k;
			}
		}
		System.out.println("final_k:"+final_k);
		return final_k;		
	}
	
	//Inertia计算
	public int findkbyInertia(String kfile, String CSRPath,String clusterresult)
	{
		int final_k=0;
		double min_ine=Double.MAX_VALUE;
		List<String> klist = IOHandle.getListFromFile1(kfile);//获取到所有的K
		for (String kl : klist)
		{
			int k=Integer.parseInt(kl);
			double ine=ClusterByKMeans.getInertia(k,CSRPath,clusterresult);
			System.out.println("ine:"+ine+"  k:"+k);
			if(ine<min_ine)
			{
				min_ine=ine;
				final_k=k;
			}
		}
		System.out.println("final_k:"+final_k);
		return final_k;	
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		FindK f = new FindK();
		/*long st=System.currentTimeMillis();		
		f.findk("resource/k.txt", "resource/csr矩阵","resource/4data+TFIDF矩阵.txt");
		long et=System.currentTimeMillis();
		long t=et-st;
		System.out.println("time:"+t);
		f.distancearray("resource/4data+TFIDF矩阵.txt");*/
		f.findkbySilhouette("resource/k.txt", "resource/csr矩阵","resource/cluster");	
//		f.findkbyInertia("resource/k.txt", "resource/2月份csr矩阵","resource/cluster");
	}

}
