package com.cons.man.domain;

import java.util.List;

public class RiskWorkActivityVO {
	
	private int id;
	private int type;
	private int risk_id;
	private String wa_code;
	private String class_name;
	private int idx;
	private String code;
	private String writer_id; 
	private String write_time;
	private List<RiskWorkStepVO> wsList;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getRisk_id() {
		return risk_id;
	}
	public void setRisk_id(int risk_id) {
		this.risk_id = risk_id;
	}
	public String getWa_code() {
		return wa_code;
	}
	public void setWa_code(String wa_code) {
		this.wa_code = wa_code;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getWriter_id() {
		return writer_id;
	}
	public void setWriter_id(String writer_id) {
		this.writer_id = writer_id;
	}
	public String getWrite_time() {
		return write_time;
	}
	public void setWrite_time(String write_time) {
		this.write_time = write_time;
	}
	public List<RiskWorkStepVO> getWsList() {
		return wsList;
	}
	public void setWsList(List<RiskWorkStepVO> wsList) {
		this.wsList = wsList;
	}
	
	
}