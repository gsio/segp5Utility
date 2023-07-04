package com.cons.man.domain;

public class ScannerStateVO {

	private int id;
	private String scanner_mac;
	private int app_type;
	private int site_id;
	private int place_id;
	private int section;
	private int phone_idx;
	private String model;
	private String app_ver;
	private int scan_count;
	private String write_time;
	private String noti_time;
	private int time_diff_min_data;
	private String name;
	private int time_diff_min_noti;
	
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
	public String getWrite_time() {
		return write_time;
	}
	public void setWrite_time(String write_time) {
		this.write_time = write_time;
	}
	public String getNoti_time() {
		return noti_time;
	}
	public void setNoti_time(String noti_time) {
		this.noti_time = noti_time;
	}
	public int getTime_diff_min_data() {
		return time_diff_min_data;
	}
	public void setTime_diff_min_data(int time_diff_min_data) {
		this.time_diff_min_data = time_diff_min_data;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTime_diff_min_noti() {
		return time_diff_min_noti;
	}
	public void setTime_diff_min_noti(int time_diff_min_noti) {
		this.time_diff_min_noti = time_diff_min_noti;
	}
	public int getApp_type() {
		return app_type;
	}
	public void setApp_type(int app_type) {
		this.app_type = app_type;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getApp_ver() {
		return app_ver;
	}
	public void setApp_ver(String app_ver) {
		this.app_ver = app_ver;
	}
	public int getScan_count() {
		return scan_count;
	}
	public void setScan_count(int scan_count) {
		this.scan_count = scan_count;
	}	
	
}
