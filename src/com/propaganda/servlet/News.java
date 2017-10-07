package com.propaganda.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.propaganda.dao.Newsdao;
import com.propaganda.dao.NewsdaoImpl;
import com.alibaba.fastjson.JSON;

public class News extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4437029026089400221L;

	public News() {
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
		resp.setContentType("application/json; charset=utf-8");
		PrintWriter out = resp.getWriter();
		
		Newsdao newsservice = new NewsdaoImpl();
		ArrayList<com.propaganda.bean.News> resultlist = new ArrayList<com.propaganda.bean.News>();
		
		
		try{
			resultlist = newsservice.getNews();
			//resultlist.size();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		//JSON.

			out.print(JSON.toJSON(resultlist));

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request,response);
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
