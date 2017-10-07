package com.propaganda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.propaganda.DButil.DBUtil;
import com.propaganda.exception.DataBaseException;

public class CarouselUpdatedaoImpl implements CarouselUpdatedao {

	@Override
	public void update(String id, String title, String content, String filename) {
		// TODO 自动生成的方法存根
		Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = null;
		try{
			String sql = "update Carousel set title=?,detail=?,imagename=? where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, content);
            preparedStatement.setString(3, filename);
            preparedStatement.setString(4, id);
            preparedStatement.executeUpdate();
			
		}catch (SQLException e) {
            e.printStackTrace();
            throw new DataBaseException("ORA-1012");
        }catch(Exception e){
			e.printStackTrace();
		}
	}

}  
