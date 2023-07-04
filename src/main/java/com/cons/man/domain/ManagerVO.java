
package com.cons.man.domain;

import java.io.Serializable;


public class ManagerVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4177606408787283224L;
	private String id;

	private int site_id = -1;
	private String site_name;
	private int site_auth;
	private int company_id;
	private String company_name;
	private String role_name;
	private String userid;
	private String password;
	private String useyn;
	private String name;
	private String writetime;
	
	//Search, numbering, paging
	private String num;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSite_id() {
		return site_id;
	}

	public void setSite_id(int site_id) {
		this.site_id = site_id;
	}

	public String getSite_name() {
		return site_name;
	}

	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}

	public int getSite_auth() {
		return site_auth;
	}

	public void setSite_auth(int site_auth) {
		this.site_auth = site_auth;
	}

	public int getCompany_id() {
		return company_id;
	}

	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUseyn() {
		return useyn;
	}

	public void setUseyn(String useyn) {
		this.useyn = useyn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWritetime() {
		return writetime;
	}

	public void setWritetime(String writetime) {
		this.writetime = writetime;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
	
			
}

