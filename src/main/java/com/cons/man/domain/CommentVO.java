package com.cons.man.domain;

import java.io.Serializable;

public class CommentVO implements Serializable {

	private int id;
	private int site_id;
	private int notice_id;

	private int parent_id;
	private String content;	

	private String write_time;
	private String writer_user_id;
	private String writer_name;
	private String writer_user_grade;
	private String writer_cont_name;
	private String writer_photo;

	private String parent_name;
	private String parent_grade;
	private String parent_cont_name;
	
	private String useyn;
	
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
	public int getNotice_id() {
		return notice_id;
	}
	public void setNotice_id(int notice_id) {
		this.notice_id = notice_id;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
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
	public String getWriter_user_id() {
		return writer_user_id;
	}
	public void setWriter_user_id(String writer_user_id) {
		this.writer_user_id = writer_user_id;
	}	
	public String getWriter_name() {
		return writer_name;
	}
	public void setWriter_name(String writer_name) {
		this.writer_name = writer_name;
	}
	public String getWriter_user_grade() {
		return writer_user_grade;
	}
	public void setWriter_user_grade(String writer_user_grade) {
		this.writer_user_grade = writer_user_grade;
	}
	public String getWriter_cont_name() {
		return writer_cont_name;
	}
	public void setWriter_cont_name(String writer_cont_name) {
		this.writer_cont_name = writer_cont_name;
	}
	public String getParent_name() {
		return parent_name;
	}
	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}
	public String getParent_grade() {
		return parent_grade;
	}
	public void setParent_grade(String parent_grade) {
		this.parent_grade = parent_grade;
	}
	public String getParent_cont_name() {
		return parent_cont_name;
	}
	public void setParent_cont_name(String parent_cont_name) {
		this.parent_cont_name = parent_cont_name;
	}
	public String getWriter_photo() {
		return writer_photo;
	}
	public void setWriter_photo(String writer_photo) {
		this.writer_photo = writer_photo;
	}
	public String getUseyn() {
		return useyn;
	}
	public void setUseyn(String useyn) {
		this.useyn = useyn;
	}
	
	
}
