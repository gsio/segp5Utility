package com.cons.man.domain;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class InfoVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2212049792566601861L;
	private int id;
	
	private String category;
	private String title;
	private String date;
	private int hit;
	private String contents;
	private String filepath1;
	private String filename1;
	private String filepath2;
	private String filename2;
	
	private MultipartFile file1;
	private MultipartFile file2;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getFilepath1() {
		return filepath1;
	}
	public void setFilepath1(String filepath1) {
		this.filepath1 = filepath1;
	}
	public String getFilename1() {
		return filename1;
	}
	public void setFilename1(String filename1) {
		this.filename1 = filename1;
	}
	public String getFilepath2() {
		return filepath2;
	}
	public void setFilepath2(String filepath2) {
		this.filepath2 = filepath2;
	}
	public String getFilename2() {
		return filename2;
	}
	public void setFilename2(String filename2) {
		this.filename2 = filename2;
	}
	public MultipartFile getFile1() {
		return file1;
	}
	public void setFile1(MultipartFile file1) {
		this.file1 = file1;
	}
	public MultipartFile getFile2() {
		return file2;
	}
	public void setFile2(MultipartFile file2) {
		this.file2 = file2;
	}
	
	
	
}
