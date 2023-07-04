package com.cons.man.domain;

import java.util.List;

public class RiskWorkStepVO {
	
	private int id;
	private int risk_id;
	private int wa_idx;
	private String work_content;
	private int equip_id;
	private String equip_name;
	private int equip_cnt;
	private String work_start;
	private String work_end;
	private String section_name;
	private int engineer_cnt;
	private int idx;
	private List<RiskFactorVO> rfList;
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
	public String getWork_content() {
		return work_content;
	}
	public void setWork_content(String work_content) {
		this.work_content = work_content;
	}
	public int getEquip_id() {
		return equip_id;
	}
	public void setEquip_id(int equip_id) {
		this.equip_id = equip_id;
	}
	public String getEquip_name() {
		return equip_name;
	}
	public void setEquip_name(String equip_name) {
		this.equip_name = equip_name;
	}
	public int getEquip_cnt() {
		return equip_cnt;
	}
	public void setEquip_cnt(int equip_cnt) {
		this.equip_cnt = equip_cnt;
	}
	public String getWork_start() {
		return work_start;
	}
	public void setWork_start(String work_start) {
		this.work_start = work_start;
	}
	public String getWork_end() {
		return work_end;
	}
	public void setWork_end(String work_end) {
		this.work_end = work_end;
	}
	public String getSection_name() {
		return section_name;
	}
	public void setSection_name(String section_name) {
		this.section_name = section_name;
	}
	public int getEngineer_cnt() {
		return engineer_cnt;
	}
	public void setEngineer_cnt(int engineer_cnt) {
		this.engineer_cnt = engineer_cnt;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public List<RiskFactorVO> getRfList() {
		return rfList;
	}
	public void setRfList(List<RiskFactorVO> rfList) {
		this.rfList = rfList;
	}
	public String getWrite_time() {
		return write_time;
	}
	public void setWrite_time(String write_time) {
		this.write_time = write_time;
	}
	
}