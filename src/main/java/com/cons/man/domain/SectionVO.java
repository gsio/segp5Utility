package com.cons.man.domain;

public class SectionVO {
	
	private int id;
	private int site_id;
	private int place_id;
	private int section;	
	private int type;
	private int group_id;
	private String section_type;
	private String section_name;
	private String group_name;
	private String alias;	
	private String floor;	
	private int state;
	private String state_name;
	private String state_color; 
	private int total_count;
	private String writer_user_id;
	private String writer_user_name;
	private String write_time;

	// 아래는 대표 공종
	private int cont_id;
	private String cont_name;
	private String work_state_name;
	private int work_state_count;
	
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getGroup_id() {
		return group_id;
	}
	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}
	public String getSection_type() {
		return section_type;
	}
	public void setSection_type(String section_type) {
		this.section_type = section_type;
	}
	public String getSection_name() {
		return section_name;
	}
	public void setSection_name(String section_name) {
		this.section_name = section_name;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getState_name() {
		return state_name;
	}
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	public String getState_color() {
		return state_color;
	}
	public void setState_color(String state_color) {
		this.state_color = state_color;
	}
	public int getTotal_count() {
		return total_count;
	}
	public void setTotal_count(int total_count) {
		this.total_count = total_count;
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
	public String getWork_state_name() {
		return work_state_name;
	}
	public void setWork_state_name(String work_state_name) {
		this.work_state_name = work_state_name;
	}
	public int getWork_state_count() {
		return work_state_count;
	}
	public void setWork_state_count(int work_state_count) {
		this.work_state_count = work_state_count;
	}
	public String getWriter_user_id() {
		return writer_user_id;
	}
	public void setWriter_user_id(String writer_user_id) {
		this.writer_user_id = writer_user_id;
	}
	public String getWriter_user_name() {
		return writer_user_name;
	}
	public void setWriter_user_name(String writer_user_name) {
		this.writer_user_name = writer_user_name;
	}
	public String getWrite_time() {
		return write_time;
	}
	public void setWrite_time(String write_time) {
		this.write_time = write_time;
	}
}
