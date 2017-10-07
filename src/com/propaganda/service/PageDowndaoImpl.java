package com.propaganda.service;

import java.io.BufferedReader;  
import java.io.File;  
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.io.OutputStreamWriter;  
import java.net.MalformedURLException;  
import java.net.URL;  

public class PageDowndaoImpl implements PageDowndao {

	@Override
	public String download(String URL,String filepath) {
		// TODO 自动生成的方法存根
		 BufferedReader br = null;  
	        FileOutputStream fos = null;  
	        OutputStreamWriter osw = null;  
	        String inputLine,result="";  
	        boolean flag=false;
	        try {  
	            URL url = null;  
	            url = new URL(URL);
	  
	            // 通过url.openStream(),来获得输入流  
	            br = new BufferedReader(new InputStreamReader(url.openStream(),  
	                    "utf-8"));  
	  
	            File file = new File(filepath);  
	            fos = new FileOutputStream(file);  
	            osw = new OutputStreamWriter(fos, "utf-8");  
	  
	            // 将输入流读入到临时变量中，再写入到文件  
	            //System.out.println("******************************开始********************************");
	            while ((inputLine = br.readLine()) != null) {  
	            	inputLine = inputLine.replace("gb2312", "utf-8");
	            	inputLine = inputLine.replace("\"/r/cms/", "\"http://bjfx.gov.cn/r/cms/");
	            	inputLine = inputLine.replace("\"/u/cms", "\"http://bjfx.gov.cn/u/cms");
	            	if(inputLine.contains("<div class=\"clearboth\"></div>")){
	            		flag = false;
	            		inputLine = inputLine.replace("<div class=\"clearboth\"></div>", "");
	            	}
	            	if(inputLine.contains("<div class=\"Cont_Main\">")){
	            		flag = true;
	            	}
	            	
	            	if(flag){
	            		result += inputLine;
	            		osw.write(inputLine);
	            		//System.out.println(inputLine);
	            	}
	            	//System.out.println(inputLine);
	                //osw.write(inputLine);  
	            }  
	            
	            //System.out.println("******************************结束********************************"); 
	            br.close();  
	            osw.close();  
	            //System.out.println("下载完毕!");
	            
	            //result.replace("<div class=\"clearboth\"></div>", "");
	            
	        } catch (MalformedURLException e) {  
	            e.printStackTrace();  
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } finally {
	            try {
	                if (br != null && osw != null) {  
	                    br.close();  
	                    osw.close();  
	                }  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }   
	        }
	        return result;
	}

}
