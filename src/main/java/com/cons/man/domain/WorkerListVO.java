package com.cons.man.domain;

import java.util.ArrayList;
import java.util.List;

public class WorkerListVO {
	private String workdate;
	private int site_id = -1;
	private String site_name;
	private int cont_id = -1;
	private String cont_name;
	private int cont_type;
	private String writer;
	private String checker;
	private String checker_sign;
	public String getChecker_sign() {
		return checker_sign;
	}
	public void setChecker_sign(String checker_sign) {
		this.checker_sign = checker_sign;
	}
	private String weather;
	private String today_total;
	private String place;
	
	
	//private String yester_total;
	private String total;
	private String comment;
	private String u_id; //점검자
	
	//counting
	private int option_gubun_0;
	private int option_gubun_1;
	private int option_gubun_2;
	
	private int juya; //주간 야간검색 -> 1: 주간 , 주간/야간 , 2: 야간 , 주간/야간
	private int ignore_juya= 0 ; //0이면 주간야간기준 소팅 1이면 무시
	
	List<WorkerVO> workerList = new ArrayList<WorkerVO>();
	
	/**반출 검색조건**/
	private boolean isOutcome = false;
	
	public String getWorkdate() {
		return workdate;
	}
	public void setWorkdate(String workdate) {
		this.workdate = workdate;
	}

	public String getSite_name() {
		return site_name;
	}
	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
	
	public String getCont_name() {
		return cont_name;
	}
	public void setCont_name(String cont_name) {
		this.cont_name = cont_name;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getChecker() {
		return checker;
	}
	public void setChecker(String checker) {
		this.checker = checker;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getToday_total() {
		return today_total;
	}
	public void setToday_total(String today_total) {
		this.today_total = today_total;
	}
	
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<WorkerVO> getWorkerList() {
		return workerList;
	}
	public void setWorkerList(List<WorkerVO> workerList) {
		this.workerList = workerList;
	}

	public String getU_id() {
		return u_id;
	}
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	
	public void setIgnore_juya(int ignore_juya) {
		this.ignore_juya = ignore_juya;
	}
	public int getIgnore_juya() {
		return ignore_juya;
	}
	public int getJuya() {
		return juya;
	}
	public void setJuya(int juya) {
		this.juya = juya;
	}
	public int getOption_gubun_0() {
		return option_gubun_0;
	}
	public void setOption_gubun_0(int option_gubun_0) {
		this.option_gubun_0 = option_gubun_0;
	}
	public int getOption_gubun_1() {
		return option_gubun_1;
	}
	public void setOption_gubun_1(int option_gubun_1) {
		this.option_gubun_1 = option_gubun_1;
	}
	public int getOption_gubun_2() {
		return option_gubun_2;
	}
	public void setOption_gubun_2(int option_gubun_2) {
		this.option_gubun_2 = option_gubun_2;
	}
	public void setSite_id(int site_id) {
		this.site_id = site_id;
	}
	public void setCont_id(int cont_id) {
		this.cont_id = cont_id;
	}
	public int getSite_id() {
		return site_id;
	}
	public int getCont_id() {
		return cont_id;
	}
	public int getCont_type() {
		return cont_type;
	}
	public void setCont_type(int cont_type) {
		this.cont_type = cont_type;
	}
	public boolean isOutcome() {
		return isOutcome;
	}
	public void setOutcome(boolean isOutcome) {
		this.isOutcome = isOutcome;
	}
	
	
	
	
}
