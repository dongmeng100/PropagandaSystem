package com.propaganda.service;

import com.propaganda.bean.User;
import com.propaganda.dao.Userdao;
import com.propaganda.dao.UserdaoImpl;
import com.propaganda.exception.UserNotFoundException;

public class SystemManagerImpl implements SystemMangager {
	private Userdao userDao = new UserdaoImpl();
	@Override
	public User login(String userName, String passWord) {
		// TODO �Զ����ɵķ������
		 User user = userDao.login(userName);
	        if(user != null){
	            if(user.getPassWord().equals(passWord)){
	            	//System.out.println("������ȷ");
	                // ������ȷ
	                return user;
	            } 
	            else {
	            	//System.out.println("�������");
	                // �������
	                throw new UserNotFoundException("500");
	            }
	        } 
	        else {
	        	//System.out.println("���ݿ��޴��û�");
	            //�û�Ϊ��
	            throw new UserNotFoundException("404");
	        }
	}

}
