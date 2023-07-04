package com.cons.man.domain;

import java.io.Serializable;


public class SensorLogVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
    private String scanner_mac;
    private String alias;
    private int section_type;
    private String section_name;
    private double o2;
    private double co2;
    private double co;
    private double h2s;
    private double lel;
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
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
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
	public double getO2() {
		return o2;
	}	
	public double getCo2() {
		return co2;
	}
	public void setCo2(double co2) {
		this.co2 = co2;
	}
	public void setO2(double o2) {
		this.o2 = o2;
	}
	public void setCo2(int co2) {
		this.co2 = co2;
	}
	public double getCo() {
		return co;
	}
	public void setCo(double co) {
		this.co = co;
	}
	public double getH2s() {
		return h2s;
	}
	public void setH2s(double h2s) {
		this.h2s = h2s;
	}
	public double getLel() {
		return lel;
	}
	public void setLel(double lel) {
		this.lel = lel;
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
