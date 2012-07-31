package org.zkoss.demo.tablet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utility {
	public static String getString(File f){
		StringBuffer sb = new StringBuffer();
		String tmp;
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			while ((tmp=br.readLine())!=null) {
				sb.append(tmp+"\n");
			}
			br.close();
			return sb.toString();
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}
	
	public static String urlToString(String url){
		StringBuffer result = new StringBuffer();
		String tmp;
		BufferedReader br;
		
		try{
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			while((tmp=br.readLine())!=null){
				result.append(tmp+"\n");
			}
			br.close();
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
		return result.toString();
	}
	
	public static void stringToText(File f, String s){
		try{
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
			osw.write(s);
			osw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
