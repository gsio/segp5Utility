package com.cons.man.domain;

public class QrVO {

	private int id;
	private int role;
	private String uw_id;
	private String cont_name;
	private String name;
	private String wt_name;	
	private String start_time;
	private String end_time;
	private String photo;
	private int work_min;	
	private int is_end;
	private String comment;
	private String write_time;
	private String last_update_time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public String getWrite_time() {
		return write_time;
	}
	public void setWrite_time(String write_time) {
		this.write_time = write_time;
	}
	public String getUw_id() {
		return uw_id;
	}
	public void setUw_id(String uw_id) {
		this.uw_id = uw_id;
	}
	public String getCont_name() {
		return cont_name;
	}
	public void setCont_name(String cont_name) {
		this.cont_name = cont_name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWt_name() {
		return wt_name;
	}
	public void setWt_name(String wt_name) {
		this.wt_name = wt_name;
	}
	public int getIs_end() {
		return is_end;
	}
	public void setIs_end(int is_end) {
		this.is_end = is_end;
	}	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
	public int getWork_min() {
		return work_min;
	}
	public void setWork_min(int work_min) {
		this.work_min = work_min;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getLast_update_time() {
		return last_update_time;
	}
	public void setLast_update_time(String last_update_time) {
		this.last_update_time = last_update_time;
	}
	
}
