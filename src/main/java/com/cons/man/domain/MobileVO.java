package com.cons.man.domain;

public class MobileVO {
	private String approver_user_name;
	private String confirmer_user_name;
	//nfc
	private String device_id;
	private String name;
	private String useyn;
	private String tagid;
	//device
	private String id;
	private String gubun;
	//question
	private String no;
	private String content;
	private String filename;
	private String virtname;
	//inspect
	private String nfc_id;
	private String date;
	private String time;
	private String checkyn;
	
	private String image;
	
	private String uid;
	private String pw;
	
	private String yn;
	private String reason;
	
	
	private String org;
	private String phone;
	private String rank;
	private String user_id;
	private String r_gubun;
	private String grade;
	private String cont_id;
	private String cname;
	
	private String tel;
	private String idx;
	private String delay_time;
	private String distance;
	private String bigo;
	
	private String type;
	private String addr;
	private String startdate;
	private String enddate;
	private String writetime;
	private String auth;
	private String company;
	private String user_pw;
	
	private String lat;
	private String lng;
	private String site_id;
	private String site_name;
	
	private String work_level_1; 
	private String work_level_2; 
	private String work_level_detail; 
	private String acc_starttime; 
	private String acc_endtime; 
	private String work_address; 
	private String work_place_type; 
	private String work_place; 
	private String weather; 
	private String acc_cause; 
	private String bin;
	private String kang;
	private String death; 
	private String injured;
	private String acc_result; 
	private String photo_1;
	private String photo_2;
	private String photo_3;
	private String namelist;
	
	private String workdate;
	
	private String risk_code;
	private String risk_name;
    private String work_summary;
    private String endhour;
    private String register_user;
    private String confirm_user;
    private String approve_user;
    private String ent_user;
    private String check_user;
    private String comment;
    private String cont_name;
    private String cont_name_1;
    private String cont_name_2;
    private String cont_name_3;
    private String cont_name_4;
    private String cont_name_5;
	
    private String o2;
    private String co;
    private String h2s;
    private String writedate;
    
    
    private String site_group;
    private String cons_name;
    private String line_name;
    private int site_type;
    
    private int is_outcome;
    //333
    private int year;
    private int month;
    private int status;
    private String updatetime;
    private String writer_user_id;
    private String approver_user_id;
    private String confirmer_user_id;
    private String reject_text;

    private int parent_id;
    private String image_title;
    
    private String category1_no;
    private String category1;
    private String category2_no;
    private String category2;
    private String count;
    private String content_no;
    
    private String result;
    private String checker_user_id;
    
    private String check_safe;
    private String check_qual;
    private String check_env;
    
    private String ag_id;
    
    private String work_date;
    private int worker_cnt; 
    private String worker_text; 
    private int equip_cnt; 
    private String equip_text;
    private String write_user_id; 
    private String write_user_name;
    private String completetime; 
    private String attend_comment;
    private String juya_type;
    
    private String place_id;
    private String place_name;
    private String w_id;
    private String t_id;
    private String t_name;
    
	private String title;
	private String category;
	private String ename;
	private String eform;
	private String estand;
	private String code1;
	private String code2;
	private String code3;
	private String code4;
	private String level;
	
	private String check_result;
	private String check_comment;

	private String category_id;
	private String worker_id;
	private String manage_num;
	private String rent_cont_name;
	private String contract_form;
	private String realsize;
	private String check_start;
	private String check_end;
	private String insur_start;
	private String insur_end;
	private String device_img;
	private String device_name;
	private String smart;
	
	private String license;
	
	private String option_gubun;
	
	private String e_id;
	private String check_gubun;
	private String check_id;
	private String checkdate;
	private String check_img;

	private String incomedate;
	private String outcomedate;
	private String searchdate;
	
	private String confirm_id;
	private String confirm_name;
	private String content_img;
	
	private String tag_id;
	
	private String reg_user_id;
	private String reg_user_name;
	private String uname;
	
	private String check_user_id;
	private String confirm_user_id;
	
	private String dstatus;
	private String daily_id;
	private String today;
	private String ecount;
    
	private String b_id;
	
	private String w_count;
	private String e_count;
	 
	private String place_type; 
	private String bridge_side_cnt; 
	private String bridge_cnt; 
	private String bridge_level; 
	private String total_distance; 
	private String today_distance; 
	private String percent; 
	private String total_volume; 
	private String cur_bank; 
	private String cur_cut;
	
	private String measure;
	private String progress;
	private String complete;
	
	
	private String site_addr;

	
	
	
	public String getSite_addr() {
		if( site_addr == null ) site_addr = "";
		return site_addr;
	}
	public void setSite_addr(String site_addr) {
		this.site_addr = site_addr;
	}
	
	public String getMeasure() {
		if( measure == null ) measure = "";
		return measure;
	}
	public void setMeasure(String measure) {
		this.measure = measure;
	}
	public String getProgress() {
		if( progress == null ) progress = "";
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getComplete() {
		if( complete == null ) complete = "";
		return complete;
	}
	public void setComplete(String complete) {
		this.complete = complete;
	}
	public String getPlace_type() {
		return place_type;
	}
	public void setPlace_type(String place_type) {
		this.place_type = place_type;
	}
	public String getBridge_side_cnt() {
		return bridge_side_cnt;
	}
	public void setBridge_side_cnt(String bridge_side_cnt) {
		this.bridge_side_cnt = bridge_side_cnt;
	}
	public String getBridge_cnt() {
		return bridge_cnt;
	}
	public void setBridge_cnt(String bridge_cnt) {
		this.bridge_cnt = bridge_cnt;
	}
	public String getBridge_level() {
		return bridge_level;
	}
	public void setBridge_level(String bridge_level) {
		this.bridge_level = bridge_level;
	}
	public String getTotal_distance() {
		return total_distance;
	}
	public void setTotal_distance(String total_distance) {
		this.total_distance = total_distance;
	}
	public String getToday_distance() {
		return today_distance;
	}
	public void setToday_distance(String today_distance) {
		this.today_distance = today_distance;
	}
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
	public String getTotal_volume() {
		return total_volume;
	}
	public void setTotal_volume(String total_volume) {
		this.total_volume = total_volume;
	}
	public String getCur_bank() {
		return cur_bank;
	}
	public void setCur_bank(String cur_bank) {
		this.cur_bank = cur_bank;
	}
	public String getCur_cut() {
		return cur_cut;
	}
	public void setCur_cut(String cur_cut) {
		this.cur_cut = cur_cut;
	}
	public String getW_count() {
		return w_count;
	}
	public void setW_count(String w_count) {
		this.w_count = w_count;
	}
	public String getE_count() {
		return e_count;
	}
	public void setE_count(String e_count) {
		this.e_count = e_count;
	}
	public String getB_id() {
		return b_id;
	}
	public void setB_id(String b_id) {
		this.b_id = b_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getEform() {
		return eform;
	}
	public void setEform(String eform) {
		this.eform = eform;
	}
	public String getEstand() {
		return estand;
	}
	public void setEstand(String estand) {
		this.estand = estand;
	}
	public String getCode1() {
		return code1;
	}
	public void setCode1(String code1) {
		this.code1 = code1;
	}
	public String getCode2() {
		return code2;
	}
	public void setCode2(String code2) {
		this.code2 = code2;
	}
	public String getCode3() {
		return code3;
	}
	public void setCode3(String code3) {
		this.code3 = code3;
	}
	public String getCode4() {
		return code4;
	}
	public void setCode4(String code4) {
		this.code4 = code4;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getCheck_result() {
		return check_result;
	}
	public void setCheck_result(String check_result) {
		this.check_result = check_result;
	}
	public String getCheck_comment() {
		return check_comment;
	}
	public void setCheck_comment(String check_comment) {
		this.check_comment = check_comment;
	}
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public String getWorker_id() {
		return worker_id;
	}
	public void setWorker_id(String worker_id) {
		this.worker_id = worker_id;
	}
	public String getManage_num() {
		return manage_num;
	}
	public void setManage_num(String manage_num) {
		this.manage_num = manage_num;
	}
	public String getRent_cont_name() {
		return rent_cont_name;
	}
	public void setRent_cont_name(String rent_cont_name) {
		this.rent_cont_name = rent_cont_name;
	}
	public String getContract_form() {
		return contract_form;
	}
	public void setContract_form(String contract_form) {
		this.contract_form = contract_form;
	}
	public String getRealsize() {
		return realsize;
	}
	public void setRealsize(String realsize) {
		this.realsize = realsize;
	}
	public String getCheck_start() {
		return check_start;
	}
	public void setCheck_start(String check_start) {
		this.check_start = check_start;
	}
	public String getCheck_end() {
		return check_end;
	}
	public void setCheck_end(String check_end) {
		this.check_end = check_end;
	}
	public String getInsur_start() {
		return insur_start;
	}
	public void setInsur_start(String insur_start) {
		this.insur_start = insur_start;
	}
	public String getInsur_end() {
		return insur_end;
	}
	public void setInsur_end(String insur_end) {
		this.insur_end = insur_end;
	}
	public String getDevice_img() {
		return device_img;
	}
	public void setDevice_img(String device_img) {
		this.device_img = device_img;
	}
	public String getDevice_name() {
		return device_name;
	}
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	public String getSmart() {
		return smart;
	}
	public void setSmart(String smart) {
		this.smart = smart;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getOption_gubun() {
		return option_gubun;
	}
	public void setOption_gubun(String option_gubun) {
		this.option_gubun = option_gubun;
	}
	public String getE_id() {
		return e_id;
	}
	public void setE_id(String e_id) {
		this.e_id = e_id;
	}
	public String getCheck_gubun() {
		return check_gubun;
	}
	public void setCheck_gubun(String check_gubun) {
		this.check_gubun = check_gubun;
	}
	public String getCheck_id() {
		return check_id;
	}
	public void setCheck_id(String check_id) {
		this.check_id = check_id;
	}
	public String getCheckdate() {
		return checkdate;
	}
	public void setCheckdate(String checkdate) {
		this.checkdate = checkdate;
	}
	public String getCheck_img() {
		return check_img;
	}
	public void setCheck_img(String check_img) {
		this.check_img = check_img;
	}
	public String getIncomedate() {
		return incomedate;
	}
	public void setIncomedate(String incomedate) {
		this.incomedate = incomedate;
	}
	public String getOutcomedate() {
		return outcomedate;
	}
	public void setOutcomedate(String outcomedate) {
		this.outcomedate = outcomedate;
	}
	public String getSearchdate() {
		return searchdate;
	}
	public void setSearchdate(String searchdate) {
		this.searchdate = searchdate;
	}
	public String getConfirm_id() {
		return confirm_id;
	}
	public void setConfirm_id(String confirm_id) {
		this.confirm_id = confirm_id;
	}
	public String getConfirm_name() {
		return confirm_name;
	}
	public void setConfirm_name(String confirm_name) {
		this.confirm_name = confirm_name;
	}
	public String getContent_img() {
		return content_img;
	}
	public void setContent_img(String content_img) {
		this.content_img = content_img;
	}
	public String getTag_id() {
		return tag_id;
	}
	public void setTag_id(String tag_id) {
		this.tag_id = tag_id;
	}
	public String getReg_user_id() {
		return reg_user_id;
	}
	public void setReg_user_id(String reg_user_id) {
		this.reg_user_id = reg_user_id;
	}
	public String getReg_user_name() {
		return reg_user_name;
	}
	public void setReg_user_name(String reg_user_name) {
		this.reg_user_name = reg_user_name;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getCheck_user_id() {
		return check_user_id;
	}
	public void setCheck_user_id(String check_user_id) {
		this.check_user_id = check_user_id;
	}
	public String getConfirm_user_id() {
		return confirm_user_id;
	}
	public void setConfirm_user_id(String confirm_user_id) {
		this.confirm_user_id = confirm_user_id;
	}
	public String getDstatus() {
		return dstatus;
	}
	public void setDstatus(String dstatus) {
		this.dstatus = dstatus;
	}
	public String getDaily_id() {
		return daily_id;
	}
	public void setDaily_id(String daily_id) {
		this.daily_id = daily_id;
	}
	public String getToday() {
		return today;
	}
	public void setToday(String today) {
		this.today = today;
	}
	public String getEcount() {
		return ecount;
	}
	public void setEcount(String ecount) {
		this.ecount = ecount;
	}
	public String getT_name() {
		return t_name;
	}
	public void setT_name(String t_name) {
		this.t_name = t_name;
	}
	public String getT_id() {
		return t_id;
	}
	public void setT_id(String t_id) {
		this.t_id = t_id;
	}
	public String getW_id() {
		return w_id;
	}
	public void setW_id(String w_id) {
		this.w_id = w_id;
	}
	public String getPlace_id() {
		return place_id;
	}
	public void setPlace_id(String place_id) {
		this.place_id = place_id;
	}
	public String getPlace_name() {
		return place_name;
	}
	public void setPlace_name(String place_name) {
		this.place_name = place_name;
	}
	public String getWork_date() {
		return work_date;
	}
	public void setWork_date(String work_date) {
		this.work_date = work_date;
	}
	public int getWorker_cnt() {
		return worker_cnt;
	}
	public void setWorker_cnt(int worker_cnt) {
		this.worker_cnt = worker_cnt;
	}
	public String getWorker_text() {
		return worker_text;
	}
	public void setWorker_text(String worker_text) {
		this.worker_text = worker_text;
	}
	public int getEquip_cnt() {
		return equip_cnt;
	}
	public void setEquip_cnt(int equip_cnt) {
		this.equip_cnt = equip_cnt;
	}
	public String getEquip_text() {
		return equip_text;
	}
	public void setEquip_text(String equip_text) {
		this.equip_text = equip_text;
	}
	public String getWrite_user_id() {
		return write_user_id;
	}
	public void setWrite_user_id(String write_user_id) {
		this.write_user_id = write_user_id;
	}
	public String getWrite_user_name() {
		return write_user_name;
	}
	public void setWrite_user_name(String write_user_name) {
		this.write_user_name = write_user_name;
	}
	public String getCompletetime() {
		return completetime;
	}
	public void setCompletetime(String completetime) {
		this.completetime = completetime;
	}
	public String getAttend_comment() {
		return attend_comment;
	}
	public void setAttend_comment(String attend_comment) {
		this.attend_comment = attend_comment;
	}
	public String getJuya_type() {
		return juya_type;
	}
	public void setJuya_type(String juya_type) {
		this.juya_type = juya_type;
	}
	public String getAg_id() {
		if( ag_id == null ) ag_id = "";
		return ag_id;
	}
	public void setAg_id(String ag_id) {
		this.ag_id = ag_id;
	}
	public String getCheck_safe() {
		if( check_safe == null ) check_safe = "";
		return check_safe;
	}
	public void setCheck_safe(String check_safe) {
		this.check_safe = check_safe;
	}
	public String getCheck_qual() {
		if( check_qual == null ) check_qual = "";
		return check_qual;
	}
	public void setCheck_qual(String check_qual) {
		this.check_qual = check_qual;
	}
	public String getCheck_env() {
		if( check_env == null ) check_env = "";
		return check_env;
	}
	public void setCheck_env(String check_env) {
		this.check_env = check_env;
	}
	public String getContent_no() {
		if( content_no == null ) content_no = "";
		return content_no;
	}
	public void setContent_no(String content_no) {
		this.content_no = content_no;
	}
	public String getChecker_user_id() {
		if( checker_user_id == null ) checker_user_id = "";
		return checker_user_id;
	}
	public void setChecker_user_id(String checker_user_id) {
		this.checker_user_id = checker_user_id;
	}
	public String getResult() {
		if( result == null ) result = "";
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getCategory1_no() {
		if( category1_no == null ) category1_no = "";
		return category1_no;
	}
	public void setCategory1_no(String category1_no) {
		this.category1_no = category1_no;
	}
	public String getCategory1() {
		if( category1 == null ) category1 = "";
		return category1;
	}
	public void setCategory1(String category1) {
		this.category1 = category1;
	}
	public String getCategory2_no() {
		if( category2_no == null ) category2_no = "";
		return category2_no;
	}
	public void setCategory2_no(String category2_no) {
		this.category2_no = category2_no;
	}
	public String getCategory2() {
		if( category2 == null ) category2 = "";
		return category2;
	}
	public void setCategory2(String category2) {
		this.category2 = category2;
	}
	public String getCount() {
		if( count == null ) count = "0";
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getUpdatetime() {
		if( updatetime == null ) updatetime = "";
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getWriter_user_id() {
		if( writer_user_id == null ) writer_user_id = "";
		return writer_user_id;
	}
	public void setWriter_user_id(String writer_user_id) {
		this.writer_user_id = writer_user_id;
	}
	public String getApprover_user_id() {
		if( approver_user_id == null ) approver_user_id = "";
		return approver_user_id;
	}
	public void setApprover_user_id(String approver_user_id) {
		this.approver_user_id = approver_user_id;
	}
	public String getConfirmer_user_id() {
		if( confirmer_user_id == null ) confirmer_user_id = "";
		return confirmer_user_id;
	}
	public void setConfirmer_user_id(String confirmer_user_id) {
		this.confirmer_user_id = confirmer_user_id;
	}
	public String getReject_text() {
		if( reject_text == null ) reject_text = "";
		return reject_text;
	}
	public void setReject_text(String reject_text) {
		this.reject_text = reject_text;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}
	public String getImage_title() {
		if( image_title == null ) image_title = "";
		return image_title;
	}
	public void setImage_title(String image_title) {
		this.image_title = image_title;
	}
	public String getSite_group() {
		if( site_group == null ) site_group = "";
		return site_group;
	}
	public void setSite_group(String site_group) {
		this.site_group = site_group;
	}
	public String getCons_name() {
		if( cons_name == null ) cons_name = "";
		return cons_name;
	}
	public void setCons_name(String cons_name) {
		this.cons_name = cons_name;
	}
	public String getLine_name() {
		if( line_name == null ) line_name = "";
		return line_name;
	}
	public void setLine_name(String line_name) {
		this.line_name = line_name;
	}
	public String getO2() {
		if( o2 == null ) o2 = "";
		return o2;
	}
	public void setO2(String o2) {
		this.o2 = o2;
	}
	public String getCo() {
		if( co == null ) co = "";
		return co;
	}
	public void setCo(String co) {
		this.co = co;
	}
	public String getH2s() {
		if( h2s == null ) h2s = "";
		return h2s;
	}
	public void setH2s(String h2s) {
		this.h2s = h2s;
	}
	public String getWritedate() {
		if( writedate == null ) writedate = "";
		return writedate;
	}
	public void setWritedate(String writedate) {
		this.writedate = writedate;
	}
	public String getCont_name() {
		if( cont_name == null ) cont_name = "";
		return cont_name;
	}
	public void setCont_name(String cont_name) {
		this.cont_name = cont_name;
	}
	public String getRisk_code() {
		if( risk_code == null ) risk_code = "";
		return risk_code;
	}
	public void setRisk_code(String risk_code) {
		this.risk_code = risk_code;
	}
	public String getRisk_name() {
		if( risk_name == null ) risk_name = "";
		return risk_name;
	}
	public void setRisk_name(String risk_name) {
		this.risk_name = risk_name;
	}
	public String getWork_summary() {
		if( work_summary == null ) work_summary = "";
		return work_summary;
	}
	public void setWork_summary(String work_summary) {
		this.work_summary = work_summary;
	}
	public String getEndhour() {
		if( endhour == null ) endhour = "";
		return endhour;
	}
	public void setEndhour(String endhour) {
		this.endhour = endhour;
	}
	public String getRegister_user() {
		if( register_user == null ) register_user = "";
		return register_user;
	}
	public void setRegister_user(String register_user) {
		this.register_user = register_user;
	}
	public String getConfirm_user() {
		if( confirm_user == null ) confirm_user = "";
		return confirm_user;
	}
	public void setConfirm_user(String confirm_user) {
		this.confirm_user = confirm_user;
	}
	public String getApprove_user() {
		if( approve_user == null ) approve_user = "";
		return approve_user;
	}
	public void setApprove_user(String approve_user) {
		this.approve_user = approve_user;
	}
	public String getEnt_user() {
		if( ent_user == null ) ent_user = "";
		return ent_user;
	}
	public void setEnt_user(String ent_user) {
		this.ent_user = ent_user;
	}
	public String getCheck_user() {
		if( check_user == null ) check_user = "";
		return check_user;
	}
	public void setCheck_user(String check_user) {
		this.check_user = check_user;
	}
	public String getComment() {
		if( comment == null ) comment = "";
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCont_name_1() {
		if( cont_name_1 == null ) cont_name_1 = "";
		return cont_name_1;
	}
	public void setCont_name_1(String cont_name_1) {
		this.cont_name_1 = cont_name_1;
	}
	public String getCont_name_2() {
		if( cont_name_2 == null ) cont_name_2 = "";
		return cont_name_2;
	}
	public void setCont_name_2(String cont_name_2) {
		this.cont_name_2 = cont_name_2;
	}
	public String getCont_name_3() {
		if( cont_name_3 == null ) cont_name_3 = "";
		return cont_name_3;
	}
	public void setCont_name_3(String cont_name_3) {
		this.cont_name_3 = cont_name_3;
	}
	public String getCont_name_4() {
		if( cont_name_4 == null ) cont_name_4 = "";
		return cont_name_4;
	}
	public void setCont_name_4(String cont_name_4) {
		this.cont_name_4 = cont_name_4;
	}
	public String getCont_name_5() {
		if( cont_name_5 == null ) cont_name_5 = "";
		return cont_name_5;
	}
	public void setCont_name_5(String cont_name_5) {
		this.cont_name_5 = cont_name_5;
	}
	public String getWorkdate() {
		if( workdate == null ) workdate = "";
		return workdate;
	}
	public void setWorkdate(String workdate) {
		this.workdate = workdate;
	}
	public String getNamelist() {
		if( namelist == null ) namelist = "";
		return namelist;
	}
	public void setNamelist(String namelist) {
		this.namelist = namelist;
	}
	public String getWork_level_1() {
		return work_level_1;
	}
	public void setWork_level_1(String work_level_1) {
		this.work_level_1 = work_level_1;
	}
	public String getWork_level_2() {
		return work_level_2;
	}
	public void setWork_level_2(String work_level_2) {
		this.work_level_2 = work_level_2;
	}
	public String getWork_level_detail() {
		return work_level_detail;
	}
	public void setWork_level_detail(String work_level_detail) {
		this.work_level_detail = work_level_detail;
	}
	public String getAcc_starttime() {
		return acc_starttime;
	}
	public void setAcc_starttime(String acc_starttime) {
		this.acc_starttime = acc_starttime;
	}
	public String getAcc_endtime() {
		return acc_endtime;
	}
	public void setAcc_endtime(String acc_endtime) {
		this.acc_endtime = acc_endtime;
	}
	public String getWork_address() {
		return work_address;
	}
	public void setWork_address(String work_address) {
		this.work_address = work_address;
	}
	public String getWork_place_type() {
		return work_place_type;
	}
	public void setWork_place_type(String work_place_type) {
		this.work_place_type = work_place_type;
	}
	public String getWork_place() {
		return work_place;
	}
	public void setWork_place(String work_place) {
		this.work_place = work_place;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getAcc_cause() {
		return acc_cause;
	}
	public void setAcc_cause(String acc_cause) {
		this.acc_cause = acc_cause;
	}
	public String getBin() {
		return bin;
	}
	public void setBin(String bin) {
		this.bin = bin;
	}
	public String getKang() {
		return kang;
	}
	public void setKang(String kang) {
		this.kang = kang;
	}
	public String getDeath() {
		return death;
	}
	public void setDeath(String death) {
		this.death = death;
	}
	public String getInjured() {
		return injured;
	}
	public void setInjured(String injured) {
		this.injured = injured;
	}
	public String getAcc_result() {
		return acc_result;
	}
	public void setAcc_result(String acc_result) {
		this.acc_result = acc_result;
	}
	public String getPhoto_1() {
		return photo_1;
	}
	public void setPhoto_1(String photo_1) {
		this.photo_1 = photo_1;
	}
	public String getPhoto_2() {
		return photo_2;
	}
	public void setPhoto_2(String photo_2) {
		this.photo_2 = photo_2;
	}
	public String getPhoto_3() {
		return photo_3;
	}
	public void setPhoto_3(String photo_3) {
		this.photo_3 = photo_3;
	}
	public String getSite_name() {
		if( site_name == null ) site_name = "";
		return site_name;
	}
	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
	public String getSite_id() {
		if( site_id == null ) site_id = "";
		return site_id;
	}
	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}
	public String getLat() {
		if( lat == null ) lat = "";
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		if( lng == null ) lng = "";
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getCompany() {
		if( company == null ) company = "";
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getUser_pw() {
		if( user_pw == null ) user_pw = "";
		return user_pw;
	}
	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}
	public String getType() {
		if( type == null ) type = "";
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAddr() {
		if( addr == null ) addr = "";
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getStartdate() {
		if( startdate == null ) startdate = "";
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		if( enddate == null ) enddate = "";
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getWritetime() {
		if( writetime == null ) writetime = "";
		return writetime;
	}
	public void setWritetime(String writetime) {
		this.writetime = writetime;
	}
	public String getAuth() {
		if( auth == null ) auth = "";
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public String getTel() {
		if( tel == null ) tel = "";
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getIdx() {
		if( idx == null ) idx = "";
		return idx;
	}
	public void setIdx(String idx) {
		this.idx = idx;
	}
	public String getDelay_time() {
		if( delay_time == null ) delay_time = "";
		return delay_time;
	}
	public void setDelay_time(String delay_time) {
		this.delay_time = delay_time;
	}
	public String getDistance() {
		if( distance == null ) distance = "";
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getBigo() {
		if( bigo == null ) bigo = "";
		return bigo;
	}
	public void setBigo(String bigo) {
		this.bigo = bigo;
	}
	public String getGrade() {
		if( grade == null ) grade = "";
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getCont_id() {
		return cont_id;
	}
	public void setCont_id(String cont_id) {
		this.cont_id = cont_id;
	}
	public String getCname() {
		if( cname == null ) cname = "";
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public String getPhone() {
		if( phone == null ) phone = "";
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRank() {
		if( rank == null ) rank = "";
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getR_gubun() {
		return r_gubun;
	}
	public void setR_gubun(String r_gubun) {
		this.r_gubun = r_gubun;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getImage() {
		if( image == null ) {
			image = "";
		}
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getName() {
		if( name == null ) name = "";
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getUseyn() {
		return useyn;
	}
	public void setUseyn(String useyn) {
		this.useyn = useyn;
	}
	public String getTagid() {
		return tagid;
	}
	public void setTagid(String tagid) {
		this.tagid = tagid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getContent() {
		if( content == null ) content = "";
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getVirtname() {
		return virtname;
	}
	public void setVirtname(String virtname) {
		this.virtname = virtname;
	}
	public String getNfc_id() {
		return nfc_id;
	}
	public void setNfc_id(String nfc_id) {
		this.nfc_id = nfc_id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getCheckyn() {
		return checkyn;
	}
	public void setCheckyn(String checkyn) {
		this.checkyn = checkyn;
	}
	public String getYn() {
		return yn;
	}
	public void setYn(String yn) {
		this.yn = yn;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public int getSite_type() {
		return site_type;
	}
	public void setSite_type(int site_type) {
		this.site_type = site_type;
	}
	public String getApprover_user_name() {
		return approver_user_name;
	}
	public void setApprover_user_name(String approver_user_name) {
		this.approver_user_name = approver_user_name;
	}
	public String getConfirmer_user_name() {
		return confirmer_user_name;
	}
	public void setConfirmer_user_name(String confirmer_user_name) {
		this.confirmer_user_name = confirmer_user_name;
	}
	public int getIs_outcome() {
		return is_outcome;
	}
	public void setIs_outcome(int is_outcome) {
		this.is_outcome = is_outcome;
	}
	
	
	 
	 
	
}

