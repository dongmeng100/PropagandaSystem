package com.propaganda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.propaganda.DButil.DBUtil;
import com.propaganda.bean.User;
import com.propaganda.exception.DataBaseException;

public class UserdaoImpl implements Userdao {

	@Override
	public User login(String userName) {
		Connection connection = DBUtil.getUserConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        

        try {
            String sql = "select * from tbUsers where LonginName=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user.setAuthority(resultSet.getString("XuanAuthority"));
                user.setName(userName);
                user.setPassWord(resultSet.getString("LoginPsw"));
                user.setApart(resultSet.getString("departName"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataBaseException("ORA-1012");
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw e;
        } 
        finally {

        }
        return user;
	}

}
