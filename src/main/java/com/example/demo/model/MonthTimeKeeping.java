package com.example.demo.model;

public class MonthTimeKeeping {
	private int iD;
	private String date;
	private String time;
	private int sumDateActive;
	private String idProfile;

	public int getiD() {
		return iD;
	}

	public void setiD(int iD) {
		this.iD = iD;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getSumDateActive() {
		return sumDateActive;
	}

	public void setSumDateActive(int sumDateActive) {
		this.sumDateActive = sumDateActive;
	}

	public String getIdProfile() {
		return idProfile;
	}

	public void setIdProfile(String idProfile) {
		this.idProfile = idProfile;
	}
}