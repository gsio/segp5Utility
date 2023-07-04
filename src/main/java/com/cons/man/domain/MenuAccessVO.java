package com.cons.man.domain;

public class MenuAccessVO {

	private int role_code;
	private int site_auth;
	private int cont_type;
	private int access_type;
	private String access_list;

	public int getRole_code() {
		return role_code;
	}

	public void setRole_code(int role_code) {
		this.role_code = role_code;
	}

	public int getSite_auth() {
		return site_auth;
	}

	public void setSite_auth(int site_auth) {
		this.site_auth = site_auth;
	}

	public int getCont_type() {
		return cont_type;
	}

	public void setCont_type(int cont_type) {
		this.cont_type = cont_type;
	}

	public int getAccess_type() {
		return access_type;
	}

	public void setAccess_type(int access_type) {
		this.access_type = access_type;
	}

	public String getAccess_list() {
		return access_list;
	}

	public void setAccess_list(String access_list) {
		this.access_list = access_list;
	}

}
