package com.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class DeleteSymbol {
	
	public void deletesymbol(String filename) throws UnsupportedEncodingException, IOException 
	{
		InputStreamReader read = new InputStreamReader(new FileInputStream(filename));
        BufferedReader br = new BufferedReader(read);
        List<String> linelist=new ArrayList<String>();
        String temp=null;
        while((temp=br.readLine())!=null)
        {
        	/*String first=temp.substring(0, 1);
        	System.out.println("first:"+first);
        	if(first.equals("\""))
        	{
        		temp=temp.substring(1,temp.length());
        	}
        	linelist.add(temp);
        	System.out.println(temp);*/
        	String[] part=temp.split("\\|\\&\\|");
        	if(part[0].contains("\""))
        	{
        		part[0]=part[0].replace("\"", "");
        		linelist.add(part[0]+"|&|"+part[1]);
        	}
        	else
        	{
        		linelist.add(temp);
        	}       	
        }
       /* for(String str:linelist)
        	System.out.println(str);*/
        IOHandle.writeListToFile(linelist, filename);
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		// TODO Auto-generated method stub
		DeleteSymbol ds=new DeleteSymbol();
		ds.deletesymbol("test.txt");

	}

}

