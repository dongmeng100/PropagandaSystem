package com.propaganda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.propaganda.DButil.DBUtil;
import com.propaganda.exception.DataBaseException;

public class NewsUploaddoImpl implements NewsUploaddao {

	@Override
	public void Upload(String Title,String Author,String Time,String Content,String apart) {
		// TODO 自动生成的方法存根
		Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = null;

        
        try {
            String sql = "insert into news(news_title,news_author,news_time,content,apart) values(?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Title);
            preparedStatement.setString(2, Author);
            preparedStatement.setString(3, Time);
            preparedStatement.setString(4, Content);
            preparedStatement.setString(5, apart);
            preparedStatement.executeUpdate();
            
            //connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataBaseException("ORA-1012");
        }
        catch (Exception e) {
            e.printStackTrace();
        } 
        
        

	}

}
