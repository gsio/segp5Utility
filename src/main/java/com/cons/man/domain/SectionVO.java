package com.cons.man.domain;

public class SectionVO {
	
	private int id;
	private int site_id;
	private int place_id;
	private int section;	
	private int type; // 1:일반수조, 2:특수(MCC, RM, 전기실)
	private int group_id;
	private String sec_type;
	private String sec_name;	
	private int alert_id;
	private int alert_type;
	private int alert_activated;
	private int alert_time_diff_min;
	private String scanner_mac;
	private int scanner_idx;
	private int has_env;
	private int beacon_count;
	private int state_count;
	private int fan_min_time;
	private int fan_state;
	private int hole_state;
	private int input_state;
	private String alias;
	private String name;
	private String floor;
	private String section_name;
	private String group_name;
	private int state;
	private String state_name;
	private String state_color; 
	private int fan_standard_time;
	
	// 지워야 할 목록
	private String scanner_mac_init;
	private int push_sensor;
	
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
	public String getSec_type() {
		return sec_type;
	}
	public void setSec_type(String sec_type) {
		this.sec_type = sec_type;
	}
	public String getSec_name() {
		return sec_name;
	}
	public void setSec_name(String sec_name) {
		this.sec_name = sec_name;
	}
	public int getAlert_id() {
		return alert_id;
	}
	public void setAlert_id(int alert_id) {
		this.alert_id = alert_id;
	}
	public int getAlert_type() {
		return alert_type;
	}
	public void setAlert_type(int alert_type) {
		this.alert_type = alert_type;
	}
	public int getAlert_activated() {
		return alert_activated;
	}
	public void setAlert_activated(int alert_activated) {
		this.alert_activated = alert_activated;
	}
	public int getAlert_time_diff_min() {
		return alert_time_diff_min;
	}
	public void setAlert_time_diff_min(int alert_time_diff_min) {
		this.alert_time_diff_min = alert_time_diff_min;
	}
	public String getScanner_mac() {
		return scanner_mac;
	}
	public void setScanner_mac(String scanner_mac) {
		this.scanner_mac = scanner_mac;
	}
	public int getScanner_idx() {
		return scanner_idx;
	}
	public void setScanner_idx(int scanner_idx) {
		this.scanner_idx = scanner_idx;
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
	public int getBeacon_count() {
		return beacon_count;
	}
	public void setBeacon_count(int beacon_count) {
		this.beacon_count = beacon_count;
	}
	public String getScanner_mac_init() {
		return scanner_mac_init;
	}
	public void setScanner_mac_init(String scanner_mac_init) {
		this.scanner_mac_init = scanner_mac_init;
	}
	public int getInput_state() {
		return input_state;
	}
	public void setInput_state(int input_state) {
		this.input_state = input_state;
	}
	public int getState_count() {
		return state_count;
	}
	public void setState_count(int state_count) {
		this.state_count = state_count;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getState_name() {
		return state_name;
	}
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	public String getState_color() {
		return state_color;
	}
	public void setState_color(String state_color) {
		this.state_color = state_color;
	}
	public int getHole_state() {
		return hole_state;
	}
	public void setHole_state(int hole_state) {
		this.hole_state = hole_state;
	}	
	public int getFan_min_time() {
		return fan_min_time;
	}
	public void setFan_min_time(int fan_min_time) {
		this.fan_min_time = fan_min_time;
	}
	public int getFan_state() {
		return fan_state;
	}
	public void setFan_state(int fan_state) {
		this.fan_state = fan_state;
	}
	public int getGroup_id() {
		return group_id;
	}
	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSection_name() {
		return section_name;
	}
	public void setSection_name(String section_name) {
		this.section_name = section_name;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public int getFan_standard_time() {
		return fan_standard_time;
	}
	public void setFan_standard_time(int fan_standard_time) {
		this.fan_standard_time = fan_standard_time;
	}		
	
}
