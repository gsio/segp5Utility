package com.cons.man.domain;

public class WorkTypeVO {
	
	private int id;	
	private String t_name;
	private String useyn;
	private int gubun;
	private int site_id;
	private String u_id;
	private String name;
	private String writer_user_id;
	private String writer_user_name;
	private String write_time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getT_name() {
		return t_name;
	}
	public void setT_name(String t_name) {
		this.t_name = t_name;
	}
	public String getUseyn() {
		return useyn;
	}
	public void setUseyn(String useyn) {
		this.useyn = useyn;
	}
	public int getGubun() {
		return gubun;
	}
	public void setGubun(int gubun) {
		this.gubun = gubun;
	}
	public int getSite_id() {
		return site_id;
	}
	public void setSite_id(int site_id) {
		this.site_id = site_id;
	}
	public String getU_id() {
		return u_id;
	}
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getWriter_user_name() {
		return writer_user_name;
	}
	public void setWriter_user_name(String writer_user_name) {
		this.writer_user_name = writer_user_name;
	}

}
