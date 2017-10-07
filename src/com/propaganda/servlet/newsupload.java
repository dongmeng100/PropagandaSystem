package com.propaganda.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.propaganda.dao.NewsTestdao;
import com.propaganda.dao.NewsTestdaoImpl;
import com.propaganda.dao.NewsUploaddao;
import com.propaganda.dao.NewsUploaddoImpl;
import com.propaganda.exception.DataBaseException;

public class newsupload extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8554159114738208215L;

	public newsupload() {
		super();
	}

	
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req,resp);
	}

	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		
		PrintWriter out = resp.getWriter();
		
		String title,author,time,content,resultcode="0",apart="";
		StringBuilder sb = new StringBuilder();
		
		try(BufferedReader reader = req.getReader();) {
			char[] buff = new char[1024];
			int len;
			//System.out.println("正在读取。。。");
			while((len = reader.read(buff)) != -1) {
				sb.append(buff,0, len);
		    	}
			//System.out.println("读取完成");
		}
		catch (IOException e) {
		     e.printStackTrace();
		}
		try{
			/*title = req.getParameter("title");
			author = req.getParameter("author");
			time = req.getParameter("time");
			content = req.getParameter("content");*/
			
			//System.out.println("正在解析...");
			//System.out.println(sb.toString());
			HttpSession session = req.getSession();
			String s = java.net.URLDecoder.decode(sb.toString(),"utf-8");
			title = s.substring(s.indexOf("title=")+6, s.indexOf("&author="));
			time = s.substring(s.indexOf("&time=")+6, s.indexOf("&content="));
			content = s.substring(s.indexOf("&content=")+9);
			author = s.substring(s.indexOf("&author=")+8, s.indexOf("&time="));
			apart = session.getAttribute("username").toString();
			NewsTestdao isuploaded = new NewsTestdaoImpl();
			NewsUploaddao upload = new NewsUploaddoImpl();
			boolean uploaded = isuploaded.IsUploaded(title);
			//System.out.println(uploaded);
			if(!uploaded){
				upload.Upload(title, author, time, content, apart);
				resultcode = "1";
			}
			else{
				resultcode = "2";
			}
		}
		catch(DataBaseException e){
			e.printStackTrace();
			resultcode = "3";
		}
		catch(Exception e){
			e.printStackTrace();
			resultcode = "4";
		}
		finally{
			out.write(resultcode);
		}
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
