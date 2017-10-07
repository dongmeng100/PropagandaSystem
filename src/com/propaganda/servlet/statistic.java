package com.propaganda.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.propaganda.bean.News;
import com.propaganda.bean.QueryStatistics;
import com.propaganda.dao.Newsdao;
import com.propaganda.dao.NewsdaoImpl;

public class statistic extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public statistic() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			req.setCharacterEncoding("utf-8");
			resp.setCharacterEncoding("utf-8");
			resp.setContentType("text/javascript");
			
			String QueryDate = req.getParameter("date");
			
			// 初始化新闻信息数组、统计数组
			ArrayList<News> newslist = new ArrayList<News>();
			ArrayList<QueryStatistics> Aparts = new ArrayList<QueryStatistics>();
			
			// 初始化数据库查询对象
			Newsdao getallnews = new NewsdaoImpl();
			
			try{
				
				// 查询数据库
				newslist = getallnews.getNewsByDate(QueryDate);
				
				// 遍历数据库中每一条新闻记录
				for(int i=0;i<newslist.size();i++){
					boolean flag = false;
					int flagint = 0;
					String DBApartName = newslist.get(i).getApart();
					//将每条新闻记录与Aparts作比较                newslist.get(i).getApart()就是newlist[i]
					for(int j=0;j<Aparts.size();j++){
						String ApartName = Aparts.get(j).getDepartName();
						if(DBApartName.equals(ApartName)){
							flag = true;
							flagint = j;
						}
					}
					
					// Aparts中存在第i条新闻纪录所对应的部门名称时，将Aparts中的相应部门记录条数+1
					if(flag){
						Aparts.get(flagint).setNews_author(Aparts.get(flagint).getNews_author()+1);
					}
					
					// Aparts中不存在第i条新闻纪录所对应的部门名称时，将本条记录的部门加入到Aparts中
					else{
						QueryStatistics test1 = new QueryStatistics();
						test1.setDepartName(newslist.get(i).getApart());
						test1.setNews_author(1);
						Aparts.add(test1);
					}
				}
			}catch(Exception e){
				System.out.println("服务器错误");
			}
			 
	       resp.getWriter().print(JSON.toJSONString(Aparts)); 
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
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		doGet(req,resp);
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
