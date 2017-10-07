package com.propaganda.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.propaganda.dao.deleteVideodao;
import com.propaganda.dao.deleteVideodaoImpl;
import com.propaganda.exception.DataBaseException;

public class deleteVideo extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1196552112614437575L;

	public deleteVideo() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		
		PrintWriter out = resp.getWriter();
		String key = req.getParameter("key"),result="";
		
		try{
			deleteVideodao obj = new deleteVideodaoImpl();
			obj.delete(key);
			result = "{\"success\":true,\"message\":\"É¾³ý³É¹¦£¡\"}";
		}
		catch(DataBaseException e){
			result = "{'success':false,'message':'Êý¾Ý¿â´íÎó£¬É¾³ýÊ§°Ü£¡'}";
			e.printStackTrace();
		}
		catch(Exception e){
			result = "{'success':false,'message':'·þÎñÆ÷Òì³££¬É¾³ýÊ§°Ü£¡'}";
			e.printStackTrace();
		}
		out.print(result);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req,resp);
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
