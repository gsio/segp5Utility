package com.cons.man.domain;

import java.io.Serializable;
import java.util.List;


public class RtlsVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
    private int site_id;
    private String uw_id;
    private int role;
    private String cont_name;
    private String wt_name;
    private String name;
    private String write_date;
    
    private List<RtlsLogVO> logList;

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

	public String getUw_id() {
		return uw_id;
	}

	public void setUw_id(String uw_id) {
		this.uw_id = uw_id;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getCont_name() {
		return cont_name;
	}

	public void setCont_name(String cont_name) {
		this.cont_name = cont_name;
	}

	public String getWt_name() {
		return wt_name;
	}

	public void setWt_name(String wt_name) {
		this.wt_name = wt_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWrite_date() {
		return write_date;
	}

	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}

	public List<RtlsLogVO> getLogList() {
		return logList;
	}

	public void setLogList(List<RtlsLogVO> logList) {
		this.logList = logList;
	}
    
    
	
}
