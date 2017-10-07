package com.propaganda.dao;

import java.util.ArrayList;

import com.propaganda.bean.News;

public interface Newsdao {
	public ArrayList<News> getNews();
	public ArrayList<News> getStreetNews();
	public ArrayList<News> getNewsByDate(String date);
}

