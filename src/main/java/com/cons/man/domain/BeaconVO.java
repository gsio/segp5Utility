package com.cons.man.domain;

public class BeaconVO{
	
	private int id;
	private int site_id;
	private String mac_addr;
	private int rssi_add;
	private int idx;
	private String u_id;
	private int role;
	private String name;
	private String wt_name;
	private int cont_id;
	private String cont_name;	
	private String phone;
	private String useyn;
	private int type;
	private String remark;
	private int battery;
	private String last_scan_time;
	private int time_diff_hour;
	
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
	public int getRssi_add() {
		return rssi_add;
	}
	public void setRssi_add(int rssi_add) {
		this.rssi_add = rssi_add;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getU_id() {
		return u_id;
	}
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWt_name() {
		return wt_name;
	}
	public void setWt_name(String wt_name) {
		this.wt_name = wt_name;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUseyn() {
		return useyn;
	}
	public void setUseyn(String useyn) {
		this.useyn = useyn;
	}
	public String getMac_addr() {
		return mac_addr;
	}
	public void setMac_addr(String mac_addr) {
		this.mac_addr = mac_addr;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getBattery() {
		return battery;
	}
	public void setBattery(int battery) {
		this.battery = battery;
	}
	public String getLast_scan_time() {
		return last_scan_time;
	}
	public void setLast_scan_time(String last_scan_time) {
		this.last_scan_time = last_scan_time;
	}
	public int getTime_diff_hour() {
		return time_diff_hour;
	}
	public void setTime_diff_hour(int time_diff_hour) {
		this.time_diff_hour = time_diff_hour;
	}
	
}