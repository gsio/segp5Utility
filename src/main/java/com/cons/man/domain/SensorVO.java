package com.cons.man.domain;

import java.io.Serializable;


public class SensorVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
    private String scanner_mac;
    private int site_id;
    private int place_id;
    private int section;
    private String section_name;
    private int section_type;
    private double o2;
    private String o2_unit;
    private double o2_min;
    private double  o2_max;
    private int o2_state;
    private int co2;
    private String co2_unit;
    private int co2_min;
    private int co2_max;
    private int co2_state;
    private double co;
    private String co_unit;
    private double co_min;
    private double co_max;
    private int co_state;
    private double h2s;
    private String h2s_unit;
    private double h2s_min;
    private double h2s_max;
    private int h2s_state;
    private double lel;
    private String lel_unit;
    private double lel_min;
    private double lel_max;
    private int lel_state;
    private String write_date;
    private String read_time;
    private String write_time;
    private int time_diff_min;
    
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
	public String getSection_name() {
		return section_name;
	}
	public void setSection_name(String section_name) {
		this.section_name = section_name;
	}
	public int getSection_type() {
		return section_type;
	}
	public void setSection_type(int section_type) {
		this.section_type = section_type;
	}
	public double getO2() {
		return o2;
	}
	public void setO2(double o2) {
		this.o2 = o2;
	}
	public String getO2_unit() {
		return o2_unit;
	}
	public void setO2_unit(String o2_unit) {
		this.o2_unit = o2_unit;
	}
	public double getO2_min() {
		return o2_min;
	}
	public void setO2_min(double o2_min) {
		this.o2_min = o2_min;
	}
	public double getO2_max() {
		return o2_max;
	}
	public void setO2_max(double o2_max) {
		this.o2_max = o2_max;
	}
	public int getO2_state() {
		return o2_state;
	}
	public void setO2_state(int o2_state) {
		this.o2_state = o2_state;
	}
	public int getCo2() {
		return co2;
	}
	public void setCo2(int co2) {
		this.co2 = co2;
	}
	public String getCo2_unit() {
		return co2_unit;
	}
	public void setCo2_unit(String co2_unit) {
		this.co2_unit = co2_unit;
	}
	public int getCo2_min() {
		return co2_min;
	}
	public void setCo2_min(int co2_min) {
		this.co2_min = co2_min;
	}
	public int getCo2_max() {
		return co2_max;
	}
	public void setCo2_max(int co2_max) {
		this.co2_max = co2_max;
	}
	public int getCo2_state() {
		return co2_state;
	}
	public void setCo2_state(int co2_state) {
		this.co2_state = co2_state;
	}
	public double getCo() {
		return co;
	}
	public void setCo(double co) {
		this.co = co;
	}
	public String getCo_unit() {
		return co_unit;
	}
	public void setCo_unit(String co_unit) {
		this.co_unit = co_unit;
	}
	public double getCo_min() {
		return co_min;
	}
	public void setCo_min(double co_min) {
		this.co_min = co_min;
	}
	public double getCo_max() {
		return co_max;
	}
	public void setCo_max(double co_max) {
		this.co_max = co_max;
	}
	public int getCo_state() {
		return co_state;
	}
	public void setCo_state(int co_state) {
		this.co_state = co_state;
	}
	public double getH2s() {
		return h2s;
	}
	public void setH2s(double h2s) {
		this.h2s = h2s;
	}
	public String getH2s_unit() {
		return h2s_unit;
	}
	public void setH2s_unit(String h2s_unit) {
		this.h2s_unit = h2s_unit;
	}
	public double getH2s_min() {
		return h2s_min;
	}
	public void setH2s_min(double h2s_min) {
		this.h2s_min = h2s_min;
	}
	public double getH2s_max() {
		return h2s_max;
	}
	public void setH2s_max(double h2s_max) {
		this.h2s_max = h2s_max;
	}
	public int getH2s_state() {
		return h2s_state;
	}
	public void setH2s_state(int h2s_state) {
		this.h2s_state = h2s_state;
	}
	public double getLel() {
		return lel;
	}
	public void setLel(double lel) {
		this.lel = lel;
	}
	public String getLel_unit() {
		return lel_unit;
	}
	public void setLel_unit(String lel_unit) {
		this.lel_unit = lel_unit;
	}
	public double getLel_min() {
		return lel_min;
	}
	public void setLel_min(double lel_min) {
		this.lel_min = lel_min;
	}
	public double getLel_max() {
		return lel_max;
	}
	public void setLel_max(double lel_max) {
		this.lel_max = lel_max;
	}
	public int getLel_state() {
		return lel_state;
	}
	public void setLel_state(int lel_state) {
		this.lel_state = lel_state;
	}
	public String getWrite_date() {
		return write_date;
	}
	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}
	public String getRead_time() {
		return read_time;
	}
	public void setRead_time(String read_time) {
		this.read_time = read_time;
	}
	public String getWrite_time() {
		return write_time;
	}
	public void setWrite_time(String write_time) {
		this.write_time = write_time;
	}
	public int getTime_diff_min() {
		return time_diff_min;
	}
	public void setTime_diff_min(int time_diff_min) {
		this.time_diff_min = time_diff_min;
	}
	
    
	
}
