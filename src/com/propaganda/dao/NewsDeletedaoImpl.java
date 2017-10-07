package com.propaganda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.propaganda.DButil.DBUtil;
import com.propaganda.exception.DataBaseException;

public class NewsDeletedaoImpl implements NewsDeletedao {

	@Override
	public void delete(String id) {
		// TODO 自动生成的方法存根
		Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = null;
		try{
			String sql = "delete from news where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
			
		}catch (SQLException e) {
            e.printStackTrace();
            throw new DataBaseException("ORA-1012");
        }catch(Exception e){
			e.printStackTrace();
		}
	}

}
