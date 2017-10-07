package com.propaganda.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.propaganda.dao.Updatedao;
import com.propaganda.dao.UpdatedaoImpl;
import com.propaganda.exception.DataBaseException;

public class NewsUpdate extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 275216794540795698L;

	public NewsUpdate() {
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
		
		String id,title,author,time,content,resultcode="0";
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
			
			//System.out.println("正在解析...");
			//System.out.println(sb.toString());
			String s = java.net.URLDecoder.decode(sb.toString(),"utf-8");
			
			id = s.substring(s.indexOf("id=")+3, s.indexOf("&title="));
			title = s.substring(s.indexOf("title=")+6, s.indexOf("&author="));
			time = s.substring(s.indexOf("&time=")+6, s.indexOf("&content="));
			content = s.substring(s.indexOf("&content=")+9);
			author = s.substring(s.indexOf("&author=")+8, s.indexOf("&time="));
			
			Updatedao test = new UpdatedaoImpl();
			//System.out.println(uploaded);
			
			/*HttpSession session = req.getSession();
			String apart = session.getAttribute("username").toString();*/
	
			test.update(id, title, author, time, content);
			resultcode = "1";
		}
		catch(DataBaseException e){
			e.printStackTrace();
			resultcode = "0";
		}
		catch(Exception e){
			e.printStackTrace();
			resultcode = "0";
		}
		finally{
			out.write(resultcode);
		}
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
