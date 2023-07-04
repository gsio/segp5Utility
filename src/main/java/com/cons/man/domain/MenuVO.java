package com.cons.man.domain;

import java.io.Serializable;

public class MenuVO implements Serializable {
	
	private static final long serialVersionUID = 1250124680869811211L;
	private int id;
	private int idx;
	private int group;
	private String name;
	private String href;
	private int isaccess;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public int getIsaccess() {
		return isaccess;
	}

	public void setIsaccess(int isaccess) {
		this.isaccess = isaccess;
	}

}
