package com.cons.man.domain;

public class DidSettingVO {
	
	private int id;
	private int site_id;
	private int gubun; // 시간
	private int type;
	private int alarm_state;
	private int sound_state;
	private String ip;
	private String userid;
	private int is_service;
	private int is_web;
	private String start_time;
	private String end_time;
	private String write_time;
	private String writer_user_id;
	private String writer_name;
	private int time_diff_sec;
	
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}	
	public int getAlarm_state() {
		return alarm_state;
	}
	public void setAlarm_state(int alarm_state) {
		this.alarm_state = alarm_state;
	}
	public int getSound_state() {
		return sound_state;
	}
	public void setSound_state(int sound_state) {
		this.sound_state = sound_state;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getIs_service() {
		return is_service;
	}
	public void setIs_service(int is_service) {
		this.is_service = is_service;
	}
	public int getIs_web() {
		return is_web;
	}
	public int getGubun() {
		return gubun;
	}
	public void setGubun(int gubun) {
		this.gubun = gubun;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public void setIs_web(int is_web) {
		this.is_web = is_web;
	}
	public String getWrite_time() {
		return write_time;
	}
	public void setWrite_time(String write_time) {
		this.write_time = write_time;
	}
	public String getWriter_user_id() {
		return writer_user_id;
	}
	public void setWriter_user_id(String writer_user_id) {
		this.writer_user_id = writer_user_id;
	}
	public int getTime_diff_sec() {
		return time_diff_sec;
	}
	public void setTime_diff_sec(int time_diff_sec) {
		this.time_diff_sec = time_diff_sec;
	}
	public String getWriter_name() {
		return writer_name;
	}
	public void setWriter_name(String writer_name) {
		this.writer_name = writer_name;
	}
	
}
