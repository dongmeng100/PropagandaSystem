package com.propaganda.bean;

public class Video {
	private String VideoName;
	private String VideoCaption;
	private long VideoSize;
	private String VideoKey;
	private String VideoType;
	private String VideoAuthor;
	private String UploadTime;
	
	public String getVideoName() {
		return VideoName;
	}
	public void setVideoName(String videoName) {
		VideoName = videoName;
	}
	public String getVideoCaption() {
		return VideoCaption;
	}
	public void setVideoCaption(String videoCaption) {
		VideoCaption = videoCaption;
	}
	public long getVideoSize() {
		return VideoSize;
	}
	public void setVideoSize(long videosize2) {
		VideoSize = videosize2;
	}
	public String getVideoKey() {
		return VideoKey;
	}
	public void setVideoKey(String videoKey) {
		VideoKey = videoKey;
	}
	public String getVideoType() {
		return VideoType;
	}
	public void setVideoType(String videoType) {
		VideoType = videoType;
	}
	public String getVideoAuthor() {
		return VideoAuthor;
	}
	public void setVideoAuthor(String videoAuthor) {
		VideoAuthor = videoAuthor;
	}
	public String getUploadTime() {
		return UploadTime;
	}
	public void setUploadTime(String uploadTime) {
		UploadTime = uploadTime;
	}
}
