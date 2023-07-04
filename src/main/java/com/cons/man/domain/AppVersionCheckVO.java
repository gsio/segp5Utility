package com.cons.man.domain;

public class AppVersionCheckVO {
	
	private String id;
	private String user_id;
	private String app_ver;
	private String android_ver;
	private String browser_ver;
	private String etc;
	private String writetime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getApp_ver() {
		return app_ver;
	}
	public void setApp_ver(String app_ver) {
		this.app_ver = app_ver;
	}
	public String getBrowser_ver() {
		return browser_ver;
	}
	public void setBrowser_ver(String browser_ver) {
		this.browser_ver = browser_ver;
	}
	public String getEtc() {
		return etc;
	}
	public void setEtc(String etc) {
		this.etc = etc;
	}
	public String getWritetime() {
		return writetime;
	}
	public void setWritetime(String writetime) {
		this.writetime = writetime;
	}
	public String getAndroid_ver() {
		return android_ver;
	}
	public void setAndroid_ver(String android_ver) {
		this.android_ver = android_ver;
	}
}
