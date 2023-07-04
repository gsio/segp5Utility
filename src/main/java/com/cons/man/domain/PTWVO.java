package com.cons.man.domain;

import java.io.Serializable;
import java.util.List;

public class PTWVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private int site_id;	
	private String site_name;
	
	// 1:작성, 2:결재요청 3:결재중, 4:결재완료, 99:반려
	private int state;
	
	private int cont_id;
	private String cont_name;
	
	private String uw_id;
	private String writer_name;
	private String writer_photo;
	
	// 장비 선택할 수 있는 장비
	private List<EquipVO> equip_list;
	
	// 작업 인원을 선택 할 수 있는 근로자 / 관리자
	private List<EngineerVO> engineer_list;
	
	// 결재선 (관리자)
	private List<ApprovalVO> approval_list;
	
	// 작업 내용
	private List<WorkDetailVO> work_detail_list;
	
	private String start_time;
	private String end_time;	
	
	private String write_date;
	private String write_time;
	
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
	public String getSite_name() {
		return site_name;
	}
	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getCont_id() {
		return cont_id;
	}
	public void setCont_id(int cont_id) {
		this.cont_id = cont_id;
	}
	public String getCont_name() {
		return cont_name;
	}
	public void setCont_name(String cont_name) {
		this.cont_name = cont_name;
	}
	public String getUw_id() {
		return uw_id;
	}
	public void setUw_id(String uw_id) {
		this.uw_id = uw_id;
	}
	public String getWriter_name() {
		return writer_name;
	}
	public void setWriter_name(String writer_name) {
		this.writer_name = writer_name;
	}
	public String getWriter_photo() {
		return writer_photo;
	}
	public void setWriter_photo(String writer_photo) {
		this.writer_photo = writer_photo;
	}
	public List<EquipVO> getEquip_list() {
		return equip_list;
	}
	public void setEquip_list(List<EquipVO> equip_list) {
		this.equip_list = equip_list;
	}
	public List<EngineerVO> getEngineer_list() {
		return engineer_list;
	}
	public void setEngineer_list(List<EngineerVO> engineer_list) {
		this.engineer_list = engineer_list;
	}
	public List<ApprovalVO> getApproval_list() {
		return approval_list;
	}
	public void setApproval_list(List<ApprovalVO> approval_list) {
		this.approval_list = approval_list;
	}
	public List<WorkDetailVO> getWork_detail_list() {
		return work_detail_list;
	}
	public void setWork_detail_list(List<WorkDetailVO> work_detail_list) {
		this.work_detail_list = work_detail_list;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getWrite_date() {
		return write_date;
	}
	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}
	public String getWrite_time() {
		return write_time;
	}
	public void setWrite_time(String write_time) {
		this.write_time = write_time;
	}

	
	
}
