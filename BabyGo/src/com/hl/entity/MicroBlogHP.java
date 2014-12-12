package com.hl.entity;

import java.io.Serializable;

public class MicroBlogHP implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer microblogId;
	private Integer userId;
	private String userName;
	private String userPicture;
	private String Content;
	private String Time;
	public Integer getMicroblogId() {
		return microblogId;
	}
	public void setMicroblogId(Integer microblogId) {
		this.microblogId = microblogId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPicture() {
		return userPicture;
	}
	public void setUserPicture(String userPicture) {
		this.userPicture = userPicture;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
}
