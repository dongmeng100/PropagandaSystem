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
			
			// ��ʼ��������Ϣ���顢ͳ������
			ArrayList<News> newslist = new ArrayList<News>();
			ArrayList<QueryStatistics> Aparts = new ArrayList<QueryStatistics>();
			
			// ��ʼ�����ݿ��ѯ����
			Newsdao getallnews = new NewsdaoImpl();
			
			try{
				
				// ��ѯ���ݿ�
				newslist = getallnews.getNewsByDate(QueryDate);
				
				// �������ݿ���ÿһ�����ż�¼
				for(int i=0;i<newslist.size();i++){
					boolean flag = false;
					int flagint = 0;
					String DBApartName = newslist.get(i).getApart();
					//��ÿ�����ż�¼��Aparts���Ƚ�                newslist.get(i).getApart()����newlist[i]
					for(int j=0;j<Aparts.size();j++){
						String ApartName = Aparts.get(j).getDepartName();
						if(DBApartName.equals(ApartName)){
							flag = true;
							flagint = j;
						}
					}
					
					// Aparts�д��ڵ�i�����ż�¼����Ӧ�Ĳ�������ʱ����Aparts�е���Ӧ���ż�¼����+1
					if(flag){
						Aparts.get(flagint).setNews_author(Aparts.get(flagint).getNews_author()+1);
					}
					
					// Aparts�в����ڵ�i�����ż�¼����Ӧ�Ĳ�������ʱ����������¼�Ĳ��ż��뵽Aparts��
					else{
						QueryStatistics test1 = new QueryStatistics();
						test1.setDepartName(newslist.get(i).getApart());
						test1.setNews_author(1);
						Aparts.add(test1);
					}
				}
			}catch(Exception e){
				System.out.println("����������");
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
