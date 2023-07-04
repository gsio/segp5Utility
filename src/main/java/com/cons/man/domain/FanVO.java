package com.cons.man.domain;

public class FanVO{
	
	private int id;
	private String scanner_mac;
	private String mac_addr;
	private int type; // 0:급기, 1:배기
	private int idx;	
	private int fan_idx;
	private String fan_name;
	private String fan_idx_list;
	private int running_time;
	private int site_id;
	private int place_id;
	private int section;
	private int phone_idx;
	private int state;
	private int rssi;
	private int axis1;
	private int axis2;
	private double x_point;
	private double y_point;
	private String read_time;
	private String input_date;
	private String write_date;
	private String write_time;
	private String section_name;
	private int fan_off_count;
	private int time_diff_second;
	private int time_diff_min;
	private int time_diff_sec;
	private int battery;
	private String last_scan_time;
	
	// SECTION
	private int section_id;
	private int section_type;
	
	// Manage Page
	private String beacon_mac;
	
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
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}	
	public int getFan_idx() {
		return fan_idx;
	}
	public void setFan_idx(int fan_idx) {
		this.fan_idx = fan_idx;
	}
	
	public String getFan_name() {
		return fan_name;
	}
	public void setFan_name(String fan_name) {
		this.fan_name = fan_name;
	}
	public String getFan_idx_list() {
		return fan_idx_list;
	}
	public void setFan_idx_list(String fan_idx_list) {
		this.fan_idx_list = fan_idx_list;
	}	
	public int getRunning_time() {
		return running_time;
	}
	public void setRunning_time(int running_time) {
		this.running_time = running_time;
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
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getRssi() {
		return rssi;
	}
	public void setRssi(int rssi) {
		this.rssi = rssi;
	}
	public int getAxis1() {
		return axis1;
	}
	public void setAxis1(int axis1) {
		this.axis1 = axis1;
	}
	public int getAxis2() {
		return axis2;
	}
	public void setAxis2(int axis2) {
		this.axis2 = axis2;
	}
	public double getX_point() {
		return x_point;
	}
	public void setX_point(double x_point) {
		this.x_point = x_point;
	}
	public double getY_point() {
		return y_point;
	}
	public void setY_point(double y_point) {
		this.y_point = y_point;
	}
	public String getRead_time() {
		return read_time;
	}
	public void setRead_time(String read_time) {
		this.read_time = read_time;
	}
	public String getInput_date() {
		return input_date;
	}
	public void setInput_date(String input_date) {
		this.input_date = input_date;
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
	public String getSection_name() {
		return section_name;
	}
	public void setSection_name(String section_name) {
		this.section_name = section_name;
	}	
	public int getTime_diff_second() {
		return time_diff_second;
	}
	public void setTime_diff_second(int time_diff_second) {
		this.time_diff_second = time_diff_second;
	}
	public int getTime_diff_min() {
		return time_diff_min;
	}
	public void setTime_diff_min(int time_diff_min) {
		this.time_diff_min = time_diff_min;
	}
	public int getTime_diff_sec() {
		return time_diff_sec;
	}
	public void setTime_diff_sec(int time_diff_sec) {
		this.time_diff_sec = time_diff_sec;
	}
	public int getSection_id() {
		return section_id;
	}
	public void setSection_id(int section_id) {
		this.section_id = section_id;
	}
	public int getSection_type() {
		return section_type;
	}
	public void setSection_type(int section_type) {
		this.section_type = section_type;
	}
	public String getBeacon_mac() {
		return beacon_mac;
	}
	public void setBeacon_mac(String beacon_mac) {
		this.beacon_mac = beacon_mac;
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
	public int getFan_off_count() {
		return fan_off_count;
	}
	public void setFan_off_count(int fan_off_count) {
		this.fan_off_count = fan_off_count;
	}
	
}