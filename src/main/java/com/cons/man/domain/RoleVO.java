package com.cons.man.domain;

import java.io.Serializable;


public class RoleVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private int type;
	private int code;
	private int userNum; //그룹리스트 불러올때 우측에 대상 숫자 보여주기 위함
	private String comment;
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getUserNum() {
		return userNum;
	}
	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}
	

}
