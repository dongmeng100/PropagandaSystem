package com.propaganda.bean;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.propaganda.dao.Newsdao;
import com.propaganda.dao.NewsdaoImpl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

public class QueryStatistics {
	private String departName;
	private int news_author;
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public int getNews_author() {
		return news_author;
	}
	public void setNews_author(int i) {
		this.news_author = i;
	}
	
}
