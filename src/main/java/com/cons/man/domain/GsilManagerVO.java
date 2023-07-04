package com.cons.man.domain;

public class GsilManagerVO {

	private int id;
	private int site_id;
	private String name;
	private String phone;
	private String email;
	private String push_mail;
	private String push_message;
	private int isPushMail;
	private int isPushMessage;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	public int getSite_id() {
		return site_id;
	}
	public void setSite_id(int site_id) {
		this.site_id = site_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPush_mail() {
		return push_mail;
	}
	public void setPush_mail(String push_mail) {
		this.push_mail = push_mail;
	}
	public String getPush_message() {
		return push_message;
	}
	public void setPush_message(String push_message) {
		this.push_message = push_message;
	}
	public int getIsPushMail() {
		return isPushMail;
	}
	public void setIsPushMail(int isPushMail) {
		this.isPushMail = isPushMail;
	}
	public int getIsPushMessage() {
		return isPushMessage;
	}
	public void setIsPushMessage(int isPushMessage) {
		this.isPushMessage = isPushMessage;
	}	
}
