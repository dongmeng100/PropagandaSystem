package com.propaganda.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.propaganda.bean.Carousels;
import com.propaganda.dao.getCarouseldao;
import com.propaganda.dao.getCarouseldaoImpl;
import com.propaganda.exception.DataBaseException;

public class getCarousels extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1317516852020845898L;

	public getCarousels() {
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
		ArrayList<Carousels> result = new ArrayList<Carousels>();
		getCarouseldao obj = new getCarouseldaoImpl();
		
		try{
			result = obj.get();
		}
		catch(DataBaseException e){
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		out.print(JSON.toJSON(result));
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			doGet(req,resp);
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
