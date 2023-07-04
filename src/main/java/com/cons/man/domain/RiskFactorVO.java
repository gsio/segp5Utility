package com.cons.man.domain;

import java.util.List;

public class RiskFactorVO {
	
	private int risk_id;
	private int wa_idx;
	private int ws_idx;
	private String risk_content;
	private String damage_form;
	private int frequency;
	private int strength;
	private String rating;
	private int idx;
	private List<RiskManagPlanVO> mpList;
	private String write_time;
	
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
	public String getRisk_content() {
		return risk_content;
	}
	public void setRisk_content(String risk_content) {
		this.risk_content = risk_content;
	}
	public String getDamage_form() {
		return damage_form;
	}
	public void setDamage_form(String damage_form) {
		this.damage_form = damage_form;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public int getStrength() {
		return strength;
	}
	public void setStrength(int strength) {
		this.strength = strength;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public List<RiskManagPlanVO> getMpList() {
		return mpList;
	}
	public void setMpList(List<RiskManagPlanVO> mpList) {
		this.mpList = mpList;
	}
	public String getWrite_time() {
		return write_time;
	}
	public void setWrite_time(String write_time) {
		this.write_time = write_time;
	}
}
