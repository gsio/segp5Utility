package com.cons.man.domain;

import java.io.Serializable;

public class LoginLogVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String ip;
	private String userid;
	private String writetime;
	private String url;
	private int is_web;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getWritetime() {
		return writetime;
	}

	public void setWritetime(String writetime) {
		this.writetime = writetime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getIs_web() {
		return is_web;
	}

	public void setIs_web(int is_web) {
		this.is_web = is_web;
	}

}
