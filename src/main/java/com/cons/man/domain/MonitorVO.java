package com.cons.man.domain;

public class MonitorVO {
	
	/**site_info**/
	private int company_id = -1;
	private int site_id = -1;
	private String site_name;
	private String site_line_name;
	private String site_cons_name;
	private String site_addr;
	private int site_tmp_group;
	private int site_tmp_idx;
	private int site_group;
	private int site_auth;
	private int site_type;
	private int site_phone;
	private String startdate;
	private String enddate;
	private double site_lat;
	private double site_lng;
	
	private String date;
	private String mod_date;//juya_type에 상황에따른 어제날짜조회용
	
	private String safe_starttime;
	private String safe_hour; 
	private String safe_day; 
	/**부적합보고서**/
	private int meas_cnt;
	private int not_meas_cnt;
	private int meas_tot_cnt;
	/**장비점검**/
	private int total_equip;
	private int check_equip;
	/*private int total_device;
	private int check_device;*/
	/**근로자**/
	private int tot_worker_cnt;
	private int new_worker_cnt;
	/**출역**/
	private int attend_worker;//STATUS Y인 wtype gubun 1인것 + user
	private int attend_device;//STATUS Y, wtype gubun 2인것
	private int attend_equip;//STATUS Y, wtype gubun 2인것
	
	/**업체수**/
	private int total_cont;
	
	/**취약개소**/
	private int total_spot_cnt;
	private int spot_check_cnt;

	/**작업시작보고**/
	
	private int is_brief_start;
	private int is_brief_complete;
	private int juya_type;
	
	/**demo**/
	private int risk_count;
	
	
	
	public int getRisk_count() {
		return risk_count;
	}
	public void setRisk_count(int risk_count) {
		this.risk_count = risk_count;
	}
	//for page 2
	private int cont_id = -1;
	private String cont_name;
	private String cont_type;
	private int worker_cnt;
	private int worker_month_cnt;
	private int device_cnt;
	
	
	/**출역관련 **/
	private int foreign_cnt;
	private int highpress_cnt;
	private int oldage_cnt;
	private int notedu_cnt;
	
	
	private double lat;
	private double lng;
	
	private int is_333;//333
	
	//private int total_worker;//등록된 전체..표현 안함
	//private int xx_device;
	
	private int total_risk;
	private int check_risk;
	
	

	public int getTotal_risk() {
		return total_risk;
	}
	public void setTotal_risk(int total_risk) {
		this.total_risk = total_risk;
	}
	public int getCheck_risk() {
		return check_risk;
	}
	public void setCheck_risk(int check_risk) {
		this.check_risk = check_risk;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getAttend_worker() {
		return attend_worker;
	}
	public void setAttend_worker(int attend_worker) {
		this.attend_worker = attend_worker;
	}
	public int getAttend_device() {
		return attend_device;
	}
	public void setAttend_device(int attend_device) {
		this.attend_device = attend_device;
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
	public String getCont_type() {
		return cont_type;
	}
	public void setCont_type(String cont_type) {
		this.cont_type = cont_type;
	}
	public int getWorker_cnt() {
		return worker_cnt;
	}
	public void setWorker_cnt(int worker_cnt) {
		this.worker_cnt = worker_cnt;
	}
	public int getDevice_cnt() {
		return device_cnt;
	}
	public void setDevice_cnt(int device_cnt) {
		this.device_cnt = device_cnt;
	}
	public String getSafe_starttime() {
		return safe_starttime;
	}
	public void setSafe_starttime(String safe_starttime) {
		this.safe_starttime = safe_starttime;
	}
	public int getMeas_cnt() {
		return meas_cnt;
	}
	public void setMeas_cnt(int meas_cnt) {
		this.meas_cnt = meas_cnt;
	}
	public int getMeas_tot_cnt() {
		return meas_tot_cnt;
	}
	public void setMeas_tot_cnt(int meas_tot_cnt) {
		this.meas_tot_cnt = meas_tot_cnt;
	}
	public String getSafe_hour() {
		return safe_hour;
	}
	public void setSafe_hour(String safe_hour) {
		this.safe_hour = safe_hour;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public int getWorker_month_cnt() {
		return worker_month_cnt;
	}
	public void setWorker_month_cnt(int worker_month_cnt) {
		this.worker_month_cnt = worker_month_cnt;
	}
	public String getSafe_day() {
		return safe_day;
	}
	public void setSafe_day(String safe_day) {
		this.safe_day = safe_day;
	}
	public String getSite_addr() {
		return site_addr;
	}
	public void setSite_addr(String site_addr) {
		this.site_addr = site_addr;
	}
	public int getSite_auth() {
		return site_auth;
	}
	public void setSite_auth(int site_auth) {
		this.site_auth = site_auth;
	}
	public int getSite_type() {
		return site_type;
	}
	public void setSite_type(int site_type) {
		this.site_type = site_type;
	}
	public int getTotal_cont() {
		return total_cont;
	}
	public void setTotal_cont(int total_cont) {
		this.total_cont = total_cont;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public double getSite_lat() {
		return site_lat;
	}
	public void setSite_lat(double site_lat) {
		this.site_lat = site_lat;
	}
	public double getSite_lng() {
		return site_lng;
	}
	public void setSite_lng(double site_lng) {
		this.site_lng = site_lng;
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
	public int getForeign_cnt() {
		return foreign_cnt;
	}
	public void setForeign_cnt(int foreign_cnt) {
		this.foreign_cnt = foreign_cnt;
	}
	public int getHighpress_cnt() {
		return highpress_cnt;
	}
	public void setHighpress_cnt(int highpress_cnt) {
		this.highpress_cnt = highpress_cnt;
	}
	public int getOldage_cnt() {
		return oldage_cnt;
	}
	public void setOldage_cnt(int oldage_cnt) {
		this.oldage_cnt = oldage_cnt;
	}
	public int getNotedu_cnt() {
		return notedu_cnt;
	}
	public void setNotedu_cnt(int notedu_cnt) {
		this.notedu_cnt = notedu_cnt;
	}
	public int getCompany_id() {
		return company_id;
	}
	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}
	public String getSite_line_name() {
		return site_line_name;
	}
	public void setSite_line_name(String site_line_name) {
		this.site_line_name = site_line_name;
	}
	public String getSite_cons_name() {
		return site_cons_name;
	}
	public void setSite_cons_name(String site_cons_name) {
		this.site_cons_name = site_cons_name;
	}
	public int getSite_group() {
		return site_group;
	}
	public void setSite_group(int site_group) {
		this.site_group = site_group;
	}
	public int getTotal_spot_cnt() {
		return total_spot_cnt;
	}
	public void setTotal_spot_cnt(int total_spot_cnt) {
		this.total_spot_cnt = total_spot_cnt;
	}
	public int getSpot_check_cnt() {
		return spot_check_cnt;
	}
	public void setSpot_check_cnt(int spot_check_cnt) {
		this.spot_check_cnt = spot_check_cnt;
	}
	public int getTot_worker_cnt() {
		return tot_worker_cnt;
	}
	public void setTot_worker_cnt(int tot_worker_cnt) {
		this.tot_worker_cnt = tot_worker_cnt;
	}
	public int getNew_worker_cnt() {
		return new_worker_cnt;
	}
	public void setNew_worker_cnt(int new_worker_cnt) {
		this.new_worker_cnt = new_worker_cnt;
	}
	public int getTotal_equip() {
		return total_equip;
	}
	public void setTotal_equip(int total_equip) {
		this.total_equip = total_equip;
	}
	public int getCheck_equip() {
		return check_equip;
	}
	public void setCheck_equip(int check_equip) {
		this.check_equip = check_equip;
	}
	public int getIs_brief_start() {
		return is_brief_start;
	}
	public void setIs_brief_start(int is_brief_start) {
		this.is_brief_start = is_brief_start;
	}
	public int getIs_brief_complete() {
		return is_brief_complete;
	}
	public void setIs_brief_complete(int is_brief_complete) {
		this.is_brief_complete = is_brief_complete;
	}
	public int getSite_phone() {
		return site_phone;
	}
	public void setSite_phone(int site_phone) {
		this.site_phone = site_phone;
	}
	public int getSite_tmp_group() {
		return site_tmp_group;
	}
	public void setSite_tmp_group(int site_tmp_group) {
		this.site_tmp_group = site_tmp_group;
	}
	public int getAttend_equip() {
		return attend_equip;
	}
	public void setAttend_equip(int attend_equip) {
		this.attend_equip = attend_equip;
	}
	public int getNot_meas_cnt() {
		return not_meas_cnt;
	}
	public void setNot_meas_cnt(int not_meas_cnt) {
		this.not_meas_cnt = not_meas_cnt;
	}
	public int getJuya_type() {
		return juya_type;
	}
	public void setJuya_type(int juya_type) {
		this.juya_type = juya_type;
	}
	public String getMod_date() {
		return mod_date;
	}
	public void setMod_date(String mod_date) {
		this.mod_date = mod_date;
	}
	public int getSite_tmp_idx() {
		return site_tmp_idx;
	}
	public void setSite_tmp_idx(int site_tmp_idx) {
		this.site_tmp_idx = site_tmp_idx;
	}
	public int getIs_333() {
		return is_333;
	}
	public void setIs_333(int is_333) {
		this.is_333 = is_333;
	}

	
	//List
}
