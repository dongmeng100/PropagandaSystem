package com.propaganda.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.propaganda.dao.SingleNewsdao;
import com.propaganda.dao.SingleNewsdaoImpl;
import com.propaganda.bean.News;

public class SingleNews extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7562882697715336151L;

	public SingleNews() {
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
			String ID = req.getParameter("ID");
			News result = null;
			//System.out.println(ID);
			try{
				result = new News();
				SingleNewsdao news = new SingleNewsdaoImpl();
				result = news.load(ID);
				//System.out.println(result.getTitle());
			}
			catch(Exception e){
				e.printStackTrace();
			}
			//System.out.println(JSON.toJSON(result).toString());
			out.print(JSON.toJSON(result));
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request,response);
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
