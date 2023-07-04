package com.cons.man.domain;

public class BeaconLogVO{
	
	private int id;
	private String beacon_mac;
	private String scanner_mac;
	private int app_type;
	private int rssi;
	private int phone_idx;
	private String write_date;
	private String write_time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBeacon_mac() {
		return beacon_mac;
	}
	public void setBeacon_mac(String beacon_mac) {
		this.beacon_mac = beacon_mac;
	}
	public String getScanner_mac() {
		return scanner_mac;
	}
	public void setScanner_mac(String scanner_mac) {
		this.scanner_mac = scanner_mac;
	}
	public int getApp_type() {
		return app_type;
	}
	public void setApp_type(int app_type) {
		this.app_type = app_type;
	}
	public int getRssi() {
		return rssi;
	}
	public void setRssi(int rssi) {
		this.rssi = rssi;
	}
	public int getPhone_idx() {
		return phone_idx;
	}
	public void setPhone_idx(int phone_idx) {
		this.phone_idx = phone_idx;
	}
	public String getWrite_date() {
		return write_date;
	}
	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}
	public String getWrite_time() {
		return write_time;
	}
	public void setWrite_time(String write_time) {
		this.write_time = write_time;
	}
	
}