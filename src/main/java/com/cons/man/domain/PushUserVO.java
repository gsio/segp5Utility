package com.cons.man.domain;

import java.util.List;

public class PushUserVO {
	private String fcm_token;
	private String u_id;
	private int site_id;
	private int beacon_alarm;
	private int env_sensor;
	private int gyn_cctv;
	private int seg_sealed;
	private int seg_wind;
	private int seg_workmin;
	private String writetime;
	private String last_updatetime;
	private List<String> u_id_list;
	public List<String> getU_id_list() {
		return u_id_list;
	}
	public void setU_id_list(List<String> u_id_list) {
		this.u_id_list = u_id_list;
	}
	public String getU_id() {
		return u_id;
	}
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}
	public int getBeacon_alarm() {
		return beacon_alarm;
	}
	public void setBeacon_alarm(int beacon_alarm) {
		this.beacon_alarm = beacon_alarm;
	}
	public int getEnv_sensor() {
		return env_sensor;
	}
	public void setEnv_sensor(int env_sensor) {
		this.env_sensor = env_sensor;
	}
	public int getGyn_cctv() {
		return gyn_cctv;
	}
	public void setGyn_cctv(int gyn_cctv) {
		this.gyn_cctv = gyn_cctv;
	}
	public int getSeg_sealed() {
		return seg_sealed;
	}
	public void setSeg_sealed(int seg_sealed) {
		this.seg_sealed = seg_sealed;
	}
	public int getSeg_wind() {
		return seg_wind;
	}
	public void setSeg_wind(int seg_wind) {
		this.seg_wind = seg_wind;
	}
	public int getSeg_workmin() {
		return seg_workmin;
	}
	public void setSeg_workmin(int seg_workmin) {
		this.seg_workmin = seg_workmin;
	}
	public String getWritetime() {
		return writetime;
	}
	public void setWritetime(String writetime) {
		this.writetime = writetime;
	}
	public String getLast_updatetime() {
		return last_updatetime;
	}
	public void setLast_updatetime(String last_updatetime) {
		this.last_updatetime = last_updatetime;
	}
	public int getSite_id() {
		return site_id;
	}
	public void setSite_id(int site_id) {
		this.site_id = site_id;
	}
	public String getFcm_token() {
		return fcm_token;
	}
	public void setFcm_token(String fcm_token) {
		this.fcm_token = fcm_token;
	}
}
