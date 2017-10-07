package com.propaganda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.propaganda.DButil.DBUtil;
import com.propaganda.exception.DataBaseException;
import com.propaganda.bean.News;

public class NewsdaoImpl implements Newsdao {

	@Override
	public ArrayList<News> getNews() {
		// TODO 自动生成的方法存根
		Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        News news = null;
        ArrayList<News> newslist = new ArrayList<News>();
        
        try {
            String sql = "select news_time,news_title,news_author,ID,apart from news";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                news = new News();
                String s = "";
                news.setTime(resultSet.getString("news_time"));
                news.setTitle(resultSet.getString("news_title"));
                news.setAuthor(resultSet.getString("news_author"));
                news.setContent(s);
                news.setID(resultSet.getString("ID"));
                news.setApart(resultSet.getString("apart"));
                newslist.add(news);
            }
            //connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataBaseException("ORA-1012");
        }
        catch (Exception e) {
            e.printStackTrace();
        } 
		return newslist;
	}

	@Override
	public ArrayList<News> getStreetNews() {
		// TODO 自动生成的方法存根
		Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        News news = null;
        ArrayList<News> newslist = new ArrayList<News>();
        
        try {
            String sql = "select news_time,news_title,news_author,ID from news";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                news = new News();
                String s = "";
                news.setTime(resultSet.getString("news_time"));
                news.setTitle(resultSet.getString("news_title"));
                news.setAuthor(resultSet.getString("news_author"));
                news.setContent(s);
                news.setID(resultSet.getString("ID"));
                newslist.add(news);
            }
            //connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataBaseException("ORA-1012");
        }
        catch (Exception e) {
            e.printStackTrace();
        } 
		return newslist;
	}

	@Override
	public ArrayList<News> getNewsByDate(String date) {
		// TODO Auto-generated method stub
		Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        News news = null;
        ArrayList<News> newslist = new ArrayList<News>();
        
        try {
            String sql = "select news_time,news_title,news_author,ID,apart from news where convert(varchar(7),news_time,120)='"+date+"'";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                news = new News();
                String s = "";
                news.setTime(resultSet.getString("news_time"));
                news.setTitle(resultSet.getString("news_title"));
                news.setAuthor(resultSet.getString("news_author"));
                news.setContent(s);
                news.setID(resultSet.getString("ID"));
                news.setApart(resultSet.getString("apart"));
                newslist.add(news);
            }
            //connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataBaseException("ORA-1012");
        }
        catch (Exception e) {
            e.printStackTrace();
        } 
		return newslist;
	}

}
