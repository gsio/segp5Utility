package com.cons.man.domain;

public class RssiBlockVO {
	
	private int id;
	private String scanner_mac;
	private String beacon_mac;	
	private int site_id;
	private int place_id;
	private int section;
	private int phone_idx;	
	private int beacon_idx;
	private int rssi;
	private int rssi_add;
	private int rssi_cut;
	private String u_id;
	private int role;
	private String name;
	private String wt_name;
	private int cont_id;
	private String cont_name;
	private String write_time;
	private int beacon_rssi_add;
	private int app_type;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getScanner_mac() {
		return scanner_mac;
	}
	public void setScanner_mac(String scanner_mac) {
		this.scanner_mac = scanner_mac;
	}
	public String getBeacon_mac() {
		return beacon_mac;
	}
	public void setBeacon_mac(String beacon_mac) {
		this.beacon_mac = beacon_mac;
	}
	public int getSite_id() {
		return site_id;
	}
	public void setSite_id(int site_id) {
		this.site_id = site_id;
	}
	public int getPlace_id() {
		return place_id;
	}
	public void setPlace_id(int place_id) {
		this.place_id = place_id;
	}
	public int getSection() {
		return section;
	}
	public void setSection(int section) {
		this.section = section;
	}
	public int getPhone_idx() {
		return phone_idx;
	}
	public void setPhone_idx(int phone_idx) {
		this.phone_idx = phone_idx;
	}
	public int getBeacon_idx() {
		return beacon_idx;
	}
	public void setBeacon_idx(int beacon_idx) {
		this.beacon_idx = beacon_idx;
	}
	public int getRssi() {
		return rssi;
	}
	public void setRssi(int rssi) {
		this.rssi = rssi;
	}
	public int getRssi_add() {
		return rssi_add;
	}
	public void setRssi_add(int rssi_add) {
		this.rssi_add = rssi_add;
	}	
	public int getRssi_cut() {
		return rssi_cut;
	}
	public void setRssi_cut(int rssi_cut) {
		this.rssi_cut = rssi_cut;
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
	public String getWrite_time() {
		return write_time;
	}
	public void setWrite_time(String write_time) {
		this.write_time = write_time;
	}
	public int getBeacon_rssi_add() {
		return beacon_rssi_add;
	}
	public void setBeacon_rssi_add(int beacon_rssi_add) {
		this.beacon_rssi_add = beacon_rssi_add;
	}
	public int getApp_type() {
		return app_type;
	}
	public void setApp_type(int app_type) {
		this.app_type = app_type;
	}	
	
}
