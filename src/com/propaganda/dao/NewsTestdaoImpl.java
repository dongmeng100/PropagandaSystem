package com.propaganda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.propaganda.DButil.DBUtil;
import com.propaganda.exception.DataBaseException;
public class NewsTestdaoImpl implements NewsTestdao {

	@Override
	public boolean IsUploaded(String Title) {
		// TODO 自动生成的方法存根
		Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean result = false;
        
        try {
            String sql = "select * from news where news_title=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Title);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
            	result = true;
                /*news = new News();
                user.setId(resultSet.getInt("id"));
                news.setTitle(resultSet.getString("passWord"));
                user.setPassWord(resultSet.getString("passWord"));*/
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
        
        return result;
	}

}
