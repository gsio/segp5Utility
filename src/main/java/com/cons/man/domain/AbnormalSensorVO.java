package com.cons.man.domain;

public class AbnormalSensorVO {
	
	private int id;
	private int section_type;
	private String section_name;
	private int site_id;
	private int place_id;
	private int section;
	private String sensor_name;
	private String sensor_unit;
	private double value;
	private String write_time;
	private int time_diff_second;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSection_type() {
		return section_type;
	}
	public void setSection_type(int section_type) {
		this.section_type = section_type;
	}
	public String getSection_name() {
		return section_name;
	}
	public void setSection_name(String section_name) {
		this.section_name = section_name;
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
	public String getSensor_name() {
		return sensor_name;
	}
	public void setSensor_name(String sensor_name) {
		this.sensor_name = sensor_name;
	}
	public String getSensor_unit() {
		return sensor_unit;
	}
	public void setSensor_unit(String sensor_unit) {
		this.sensor_unit = sensor_unit;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getWrite_time() {
		return write_time;
	}
	public void setWrite_time(String write_time) {
		this.write_time = write_time;
	}
	public int getTime_diff_second() {
		return time_diff_second;
	}
	public void setTime_diff_second(int time_diff_second) {
		this.time_diff_second = time_diff_second;
	}
	
	
}
