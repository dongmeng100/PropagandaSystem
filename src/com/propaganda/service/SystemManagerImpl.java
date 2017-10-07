package com.propaganda.service;

import com.propaganda.bean.User;
import com.propaganda.dao.Userdao;
import com.propaganda.dao.UserdaoImpl;
import com.propaganda.exception.UserNotFoundException;

public class SystemManagerImpl implements SystemMangager {
	private Userdao userDao = new UserdaoImpl();
	@Override
	public User login(String userName, String passWord) {
		// TODO 自动生成的方法存根
		 User user = userDao.login(userName);
	        if(user != null){
	            if(user.getPassWord().equals(passWord)){
	            	//System.out.println("密码正确");
	                // 密码正确
	                return user;
	            } 
	            else {
	            	//System.out.println("密码错误");
	                // 密码错误
	                throw new UserNotFoundException("500");
	            }
	        } 
	        else {
	        	//System.out.println("数据库无此用户");
	            //用户为空
	            throw new UserNotFoundException("404");
	        }
	}

}
