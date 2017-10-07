package com.propaganda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.propaganda.DButil.DBUtil;
import com.propaganda.bean.initialPreviewConfig;
import com.propaganda.exception.DataBaseException;

public class VideoPreviewdaoImpl implements VideoPreviewdao {

	@Override
	public ArrayList<initialPreviewConfig> getvideo() {
		// TODO 自动生成的方法存根
		Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        initialPreviewConfig video = null;
        ArrayList<initialPreviewConfig> videos = new ArrayList<initialPreviewConfig>();
        try {
            String sql = "SELECT [VideoName],[VideoSize],[VideoCaption],[VideoKey],[VideoType],[VideoAuthor],[UploadTime] FROM Video order by UploadTime desc";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
            	video = new initialPreviewConfig();
            	video.setCaption(resultSet.getString("VideoCaption")+"<br>"+resultSet.getString("VideoName")+"<br>"+resultSet.getString("VideoAuthor")+resultSet.getString("UploadTime"));
            	video.setKey(resultSet.getString("VideoKey"));
            	video.setSize(resultSet.getLong("VideoSize"));
            	video.setFiletype(resultSet.getString("VideoType"));
            	video.setUrl("../deleteVideo.do");
            	videos.add(video);
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
		return videos;
	}

}
