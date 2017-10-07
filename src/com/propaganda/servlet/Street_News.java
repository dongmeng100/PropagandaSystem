package com.propaganda.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.propaganda.dao.Newsdao;
import com.propaganda.dao.NewsdaoImpl;
import com.alibaba.fastjson.JSON;

public class Street_News extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3287079409751706487L;

	/**
	 * Constructor of the object.
	 */
	public Street_News() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
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
		ArrayList<com.propaganda.bean.News> resultlist1 = new ArrayList<com.propaganda.bean.News>();
		
		try{
			resultlist = newsservice.getNews();
			//resultlist.size();
			
			HttpSession session = req.getSession();
			if(session.getAttributeNames().hasMoreElements()){
				boolean flag = false;
				Enumeration<String> enumeration =session.getAttributeNames();
				while(enumeration.hasMoreElements()){
					String Name=enumeration.nextElement().toString();//获取session中的键值
					System.out.println(Name+":"+session.getAttribute(Name));
					if(Name.equals("username")){
						flag = true;
					}
			    }
				if(flag){
					if(session.getAttribute("username").toString().equals("西城区防汛办")){
						resultlist1 = resultlist;
					}
					else{
						if(!session.getAttribute("username").toString().isEmpty()){
							String username = session.getAttribute("username").toString();
							for(int i=0;i<resultlist.size();i++){
								if(resultlist.get(i).getApart().equals(username)){
									resultlist1.add(resultlist.get(i));
								}
							}
						}
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		//JSON.
		out.print(JSON.toJSON(resultlist1));
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request,response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
