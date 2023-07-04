package com.cons.man.domain;

public class RiskManagPlanVO {
	
	private int id;
	private int risk_id;
	private int wa_idx; // Work Activity Idx
	private int ws_idx; // Work Step Idx
	private int rf_idx; // RiskFactorVO Idx
	private int idx;
	private String content;
	private String write_time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRisk_id() {
		return risk_id;
	}
	public void setRisk_id(int risk_id) {
		this.risk_id = risk_id;
	}
	public int getWa_idx() {
		return wa_idx;
	}
	public void setWa_idx(int wa_idx) {
		this.wa_idx = wa_idx;
	}
	public int getWs_idx() {
		return ws_idx;
	}
	public void setWs_idx(int ws_idx) {
		this.ws_idx = ws_idx;
	}
	public int getRf_idx() {
		return rf_idx;
	}
	public void setRf_idx(int rf_idx) {
		this.rf_idx = rf_idx;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWrite_time() {
		return write_time;
	}
	public void setWrite_time(String write_time) {
		this.write_time = write_time;
	}
	
}

