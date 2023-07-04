package com.cons.man.domain;

import java.io.Serializable;


public class SiteVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// Origin
	private int id;
	private String name;
	private int auth;	
	private String url;
	private int type;
	private String main_page;
	private String keyword;
	private String topic_alert;
	private double lat;	
	private double lng;
	private String address;
	private String start_date;
	private String end_date;
	private String useyn;	
	private String write_time;
	

	/* 사용처 확인 안됨
	 * 
		private String u_id;
	
	*/
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAuth() {
		return auth;
	}
	public void setAuth(int auth) {
		this.auth = auth;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getMain_page() {
		return main_page;
	}
	public void setMain_page(String main_page) {
		this.main_page = main_page;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getTopic_alert() {
		return topic_alert;
	}
	public void setTopic_alert(String topic_alert) {
		this.topic_alert = topic_alert;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getUseyn() {
		return useyn;
	}
	public void setUseyn(String useyn) {
		this.useyn = useyn;
	}
	public String getWrite_time() {
		return write_time;
	}
	public void setWrite_time(String write_time) {
		this.write_time = write_time;
	}

}
