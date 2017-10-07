package com.propaganda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.propaganda.DButil.DBUtil;
import com.propaganda.exception.DataBaseException;

public class UpdatedaoImpl implements Updatedao {

	@Override
	public void update(String id,String title,String author,String time,String content) {
		// TODO 自动生成的方法存
		Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = null;
		try{
			String sql = "update news set news_title=?,news_author=?,news_time=?,content=? where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);
            preparedStatement.setString(3, time);
            preparedStatement.setString(4, content);
            preparedStatement.setString(5, id);
            preparedStatement.executeUpdate();
			
		}catch (SQLException e) {
            e.printStackTrace();
            throw new DataBaseException("ORA-1012");
        }catch(Exception e){
			e.printStackTrace();
		}
	}

}
