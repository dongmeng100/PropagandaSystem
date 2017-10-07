package com.propaganda.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.propaganda.DButil.DBUtil;
import com.propaganda.exception.DataBaseException;
import com.propaganda.bean.News;

public class SingleNewsdaoImpl implements SingleNewsdao {

	@Override
	public News load(String ID) {
		// TODO 自动生成的方法存根
		Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        News news = null;
        
        try {
            String sql = "select * from news where ID=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ID);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                news = new News();
                news.setTime(resultSet.getString("news_time"));
                news.setTitle(resultSet.getString("news_title"));
                news.setAuthor(resultSet.getString("news_author"));
                news.setContent(resultSet.getString("content"));
                news.setID(resultSet.getString("ID"));
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
		return news;
	}

}
