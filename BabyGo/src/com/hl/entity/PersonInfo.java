package com.hl.entity;

import java.io.Serializable;

public class PersonInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer userId;
	private String  userName;
	private String  userAge;
	private String  userPicture;
	private String  lastLoginTime;
	private Integer mbCount;
	private Integer idolCount;
	private Integer fansCount;
	
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
	public String getUserAge() {
		return userAge;
	}
	public void setUserAge(String userAge) {
		this.userAge = userAge;
	}
	public String getUserPicture() {
		return userPicture;
	}
	public void setUserPicture(String userPicture) {
		this.userPicture = userPicture;
	}
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Integer getMbCount() {
		return mbCount;
	}
	public void setMbCount(Integer mbCount) {
		this.mbCount = mbCount;
	}
	public Integer getIdolCount() {
		return idolCount;
	}
	public void setIdolCount(Integer idolCount) {
		this.idolCount = idolCount;
	}
	public Integer getFansCount() {
		return fansCount;
	}
	public void setFansCount(Integer fansCount) {
		this.fansCount = fansCount;
	}
}
