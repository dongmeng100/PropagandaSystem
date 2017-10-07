package com.propaganda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.propaganda.DButil.DBUtil;
import com.propaganda.bean.Video;
import com.propaganda.exception.DataBaseException;

public class VideoUploaddaoImpl implements VideoUploaddao {

	@Override
	public void upload(Video video) {
		// TODO 自动生成的方法存根
		Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            String sql = "INSERT INTO Video(VideoName, VideoSize, VideoCaption, VideoKey, VideoType, VideoAuthor, UploadTime)VALUES(?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, video.getVideoName());
            preparedStatement.setLong(2, video.getVideoSize());
            preparedStatement.setString(3, video.getVideoCaption());
            preparedStatement.setString(4, video.getVideoKey());
            preparedStatement.setString(5, video.getVideoType());
            preparedStatement.setString(6, video.getVideoAuthor());
            preparedStatement.setString(7, video.getUploadTime());
            preparedStatement.executeUpdate();
            
            //connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataBaseException("ORA-1012");
        }
        catch (Exception e) {
            e.printStackTrace();
        } 
	}

}
