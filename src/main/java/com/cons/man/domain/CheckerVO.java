package com.cons.man.domain;

public class CheckerVO {
	
	private int id;
	private int type; // 1:위험성평가
	private String name;
	private String useyn;
	private int content_id; // type에 해당하는 id
	private String user_id;
	private int order; // type에 해당하는 id
	private String last_update_time;
	private String write_time;
	
	private int cont_id;
	private String cont_name;
	private String phone;
	private String grade; 
	private int role_code;
	private String role_name;
	private String confirm;
	
	public CheckerVO(){
		
	}
	
	public CheckerVO(int type, int content_id, String user_id, int order){
		this.type = type;
		this.content_id = content_id;
		this.user_id = user_id;
		this.order = order;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUseyn() {
		return useyn;
	}
	public void setUseyn(String useyn) {
		this.useyn = useyn;
	}
	public int getContent_id() {
		return content_id;
	}
	public void setContent_id(int content_id) {
		this.content_id = content_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getLast_update_time() {
		return last_update_time;
	}
	public void setLast_update_time(String last_update_time) {
		this.last_update_time = last_update_time;
	}
	public String getWrite_time() {
		return write_time;
	}
	public void setWrite_time(String write_time) {
		this.write_time = write_time;
	}

	public int getCont_id() {
		return cont_id;
	}

	public void setCont_id(int cont_id) {
		this.cont_id = cont_id;
	}

	public String getCont_name() {
		return cont_name;
	}

	public void setCont_name(String cont_name) {
		this.cont_name = cont_name;
	}

	public int getRole_code() {
		return role_code;
	}

	public void setRole_code(int role_code) {
		this.role_code = role_code;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
}
