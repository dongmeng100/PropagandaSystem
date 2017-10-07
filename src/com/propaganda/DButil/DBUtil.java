package com.propaganda.DButil;

import java.sql.Connection;
import java.sql.DriverManager;

import com.propaganda.exception.DataBaseException;

public class DBUtil {
	 	public static final String DRIVER_CLASS;
	    public static final String URL;
	    public static final String USER_NAME;
	    public static final String PASSWORD;
	    public static final String UserURL;
	    
	    static {
	        DRIVER_CLASS = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	        URL = "jdbc:sqlserver://localhost:1433;DatabaseName=DBFlood2_Xuan";
	        //UserURL = "jdbc:sqlserver://localhost:1433;DatabaseName=DBFlood2";
	        UserURL = "jdbc:sqlserver://localhost:1433;DatabaseName=DBFlood2_Xuan";
	        PASSWORD = "123";
	        USER_NAME = "sa";
	    }

	    public static Connection getConnection() {     
	        Connection connection = null;

	        try {
	        	Class.forName(DRIVER_CLASS);
	        	System.out.println(" ");   
	            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);  //建立数据库连接   url：指定连接数据库的url
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("数据库连接失败！");
	            throw new DataBaseException("ORA-1013");
	        }
	        return connection;
	    }
	    
	    public static Connection getUserConnection() {
	        Connection connection = null;

	        try {
	        	Class.forName(DRIVER_CLASS);  //加载数据库驱动
	        	System.out.println(" ");
	            connection = DriverManager.getConnection(UserURL, USER_NAME, PASSWORD);
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("数据库连接失败！");
	            throw new DataBaseException("ORA-1013");
	        }
	        return connection;
	    }
}
