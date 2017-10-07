package com.propaganda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.propaganda.DButil.DBUtil;
import com.propaganda.bean.Carousels;
import com.propaganda.exception.DataBaseException;

public class getCarouseldaoImpl implements getCarouseldao {

	@Override
	public ArrayList<Carousels> get() {
		// TODO 自动生成的方法存根
		Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Carousels> carousels = new ArrayList<Carousels>();
        Carousels carousel = null;
        try{
        	String sql = "select * from carousel";
        	preparedStatement = connection.prepareStatement(sql);
        	resultSet = preparedStatement.executeQuery();
        
        	while (resultSet.next()) {
        		carousel = new Carousels();
        		carousel.setId(resultSet.getString("id"));
        		carousel.setTitle(resultSet.getString("title"));
        		carousel.setContent(resultSet.getString("detail"));
        		carousel.setImagename(resultSet.getString("imagename"));
        		carousels.add(carousel);
        	}
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataBaseException("ORA-1012");
        }
        catch (Exception e) {
            e.printStackTrace();
        } 
		return carousels;
	}

}
