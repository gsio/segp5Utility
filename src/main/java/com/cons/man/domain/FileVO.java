package com.cons.man.domain;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class FileVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private int parent_id;
	private int idx = -1;
	private String virtname;
	private String orgname;
	private MultipartFile file;
	private int type;
	private String content_type;
	private long file_size;

	private int ismodify = 0;

	public FileVO() {

	}

	public FileVO(MultipartFile file) {
		this.file = file;
	}

	public String getVirtname() {
		return virtname;
	}

	public void setVirtname(String virtname) {
		this.virtname = virtname;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public int getParent_id() {
		return parent_id;
	}

	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getContent_type() {
		return content_type;
	}

	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}

	public long getFile_size() {
		return file_size;
	}

	public void setFile_size(long file_size) {
		this.file_size = file_size;
	}

	public int getIsmodify() {
		return ismodify;
	}

	public void setIsmodify(int ismodify) {
		this.ismodify = ismodify;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

}
