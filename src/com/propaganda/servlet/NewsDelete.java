package com.propaganda.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.propaganda.dao.NewsDeletedao;
import com.propaganda.dao.NewsDeletedaoImpl;
import com.propaganda.exception.DataBaseException;

public class NewsDelete extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2872026398226795189L;

	public NewsDelete() {
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
			String result = null;
			try{
				String id = req.getParameter("id");
				NewsDeletedao d = new NewsDeletedaoImpl();
				//System.out.println(id);
				d.delete(id);
				result = "1";
			}catch(DataBaseException e){
				e.getStackTrace();
				result = "0";
			}catch(Exception e){
				e.getStackTrace();
				result = "0";
			}
			out.write(result);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		doGet(req,resp);
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
