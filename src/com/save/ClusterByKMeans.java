package com.save;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ClusterByKMeans {

	/**
	 * method to cluster cluster by K-Means
	 *
	 * @param K
	 *            how many clusters except to get ,must be number and should more than 1
	 * @param CSRPath
	 *            CSR matrix Path
	 * @param clusterResultPath
	 *            clusterResult Path
	 */
	public static void clusterByKMeans(int K, String CSRPath, String clusterResultPath) {
		// get project's name who run this Class
		File file = new File("");
		String kmeansPY = file.getAbsolutePath() + "\\kmeans.py";// use
																	// kmeans.py

		// create command line: python pythonFilePath k CSRPath clusterResoultPath
		String[] arg = new String[] { "python", kmeansPY, String.valueOf(K), CSRPath+".npz", clusterResultPath };
		runCMD(arg);
	}

	/**
	 * method to create CSR matrix
	 *
	 * @param matrixPath
	 *            big matrix path
	 * @param CSRPath
	 *            CSR matrix path
	 */
	public static void createCSR(String matrixPath, String CSRPath) {
		// get project's name who run this Class
		File file = new File("");
		String matrix2csrPY = file.getAbsolutePath() + "\\matrix2csr.py";// use
																			// matrix2csr.py

		// create command line: python pythonFilePath matrixPath CSRPath
		String[] arg = new String[] { "python", matrix2csrPY, matrixPath, CSRPath };
		runCMD(arg);
	}

	/**
	 * run command lines
	 *
	 * @param args
	 */
	private static void runCMD(String[] args) {
		java.lang.Process process;
		try {
			process = Runtime.getRuntime().exec(args[1]);
			InputStream inputStream = process.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String line;

			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		System.out.println("this is main method");
	}

}
