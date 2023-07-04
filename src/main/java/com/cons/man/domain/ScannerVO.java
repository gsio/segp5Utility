package com.cons.man.domain;

public class ScannerVO {

	private int id;	
	private String scanner_mac_init;
	private String scanner_mac;
	private String name;		
	private int site_id;
	private int place_id;
	private int section;
	private int phone_idx;
	private int has_env;
	private int push_sensor;
	private int env_type;
	private int idx;
	
	private String scan_type;
	private String section_name;
	private int time_out;
	private int wait_out;
	private int rssi_cut;
	private int sensor_idx;
	
	public ScannerVO() {}
	
	public ScannerVO(int site_id , int idx) {
		this.site_id = site_id;
		this.idx=  idx;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	
	public String getScanner_mac_init() {
		return scanner_mac_init;
	}

	public void setScanner_mac_init(String scanner_mac_init) {
		this.scanner_mac_init = scanner_mac_init;
	}

	public String getScanner_mac() {
		return scanner_mac;
	}

	public void setScanner_mac(String scanner_mac) {
		this.scanner_mac = scanner_mac;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public int getHas_env() {
		return has_env;
	}
	public void setHas_env(int has_env) {
		this.has_env = has_env;
	}
	
	public int getPush_sensor() {
		return push_sensor;
	}

	public void setPush_sensor(int push_sensor) {
		this.push_sensor = push_sensor;
	}

	public int getEnv_type() {
		return env_type;
	}

	public void setEnv_type(int env_type) {
		this.env_type = env_type;
	}

	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getScan_type() {
		return scan_type;
	}

	public void setScan_type(String scan_type) {
		this.scan_type = scan_type;
	}

	public String getSection_name() {
		return section_name;
	}

	public void setSection_name(String section_name) {
		this.section_name = section_name;
	}

	public int getTime_out() {
		return time_out;
	}

	public void setTime_out(int time_out) {
		this.time_out = time_out;
	}

	public int getRssi_cut() {
		return rssi_cut;
	}

	public void setRssi_cut(int rssi_cut) {
		this.rssi_cut = rssi_cut;
	}

	public int getSensor_idx() {
		return sensor_idx;
	}

	public void setSensor_idx(int sensor_idx) {
		this.sensor_idx = sensor_idx;
	}

	public int getWait_out() {
		return wait_out;
	}

	public void setWait_out(int wait_out) {
		this.wait_out = wait_out;
	}
	
	
}
