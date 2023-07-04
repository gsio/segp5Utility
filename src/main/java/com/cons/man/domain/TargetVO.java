package com.cons.man.domain;

public class TargetVO {
	
	private String uw_id;
	private int role;
	private String name;
	private String photo;
	private String cont_name;
	private String type;
	private String phone;
	private int beacon_idx;
	private int nfc_idx;	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUw_id() {
		return uw_id;
	}
	public void setUw_id(String uw_id) {
		this.uw_id = uw_id;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getCont_name() {
		return cont_name;
	}
	public void setCont_name(String cont_name) {
		this.cont_name = cont_name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getBeacon_idx() {
		return beacon_idx;
	}
	public void setBeacon_idx(int beacon_idx) {
		this.beacon_idx = beacon_idx;
	}
	public int getNfc_idx() {
		return nfc_idx;
	}
	public void setNfc_idx(int nfc_idx) {
		this.nfc_idx = nfc_idx;
	}
	
}
