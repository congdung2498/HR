package com.example.demo.model;

public class Comment {
	private int id;
	private String content;
	private String date;
	private String userID;
	private int newsID;
	private String avatar;
	private String displayname;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public int getNewsID() {
		return newsID;
	}

	public void setNewsID(int newsID) {
		this.newsID = newsID;
	}

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Comment(){

	}

	public Comment(int id, String userID, int newsID, String content, String date , String avatar, String displayname){
		this.id = id;
		this.content = content;
		this.date = date;
		this.newsID = newsID;
		this.userID = userID;
		this.avatar = avatar;
		this.displayname = displayname;
	}
}