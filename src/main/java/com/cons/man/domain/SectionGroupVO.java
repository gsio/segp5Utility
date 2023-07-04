package com.cons.man.domain;

import java.util.List;

public class SectionGroupVO {
	
	private int id;
	private int site_id;
	private int place_id;
	private int section;
	private String group_name;
	private String writer_id;
	private String updater_id;
	private String writer_name;
	private String updater_name;
	private String write_time;
	private String last_update_time;
	private String useyn;
	private List<SectionVO> section_member;
	
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
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public String getWriter_id() {
		return writer_id;
	}
	public void setWriter_id(String writer_id) {
		this.writer_id = writer_id;
	}
	public String getWriter_name() {
		return writer_name;
	}
	public void setWriter_name(String writer_name) {
		this.writer_name = writer_name;
	}
	public String getWrite_time() {
		return write_time;
	}
	public void setWrite_time(String write_time) {
		this.write_time = write_time;
	}
	public List<SectionVO> getSection_member() {
		return section_member;
	}
	public void setSection_member(List<SectionVO> section_member) {
		this.section_member = section_member;
	}
	public String getUpdater_id() {
		return updater_id;
	}
	public void setUpdater_id(String updater_id) {
		this.updater_id = updater_id;
	}
	public String getLast_update_time() {
		return last_update_time;
	}
	public void setLast_update_time(String last_update_time) {
		this.last_update_time = last_update_time;
	}
	public String getUseyn() {
		return useyn;
	}
	public void setUseyn(String useyn) {
		this.useyn = useyn;
	}
	public String getUpdater_name() {
		return updater_name;
	}
	public void setUpdater_name(String updater_name) {
		this.updater_name = updater_name;
	}	
	
}
