package com.cons.man.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class RiskVO {
	
	private int id;

	private int site_id = -1;
	private int cont_id;
	private String cont_name;	
	private int approval_state; // 결재상태
	private String approval_state_name;	
	private int work_state; 
	private String work_state_name; // 공종상태	
	private String content;	// 평가내용
	private String remark; // 추가메모사항
	private String risk_start;
	private String risk_end;	
	private String writer_id;
	private String approver_id;
	private int approval_order; // 결재순서
	private String writer_name;
	private String writer_cont_name;
	private String last_update_time;	
	private String write_time;
	
	private List<ApprovalVO> approvalList = new ArrayList<ApprovalVO>();
	private List<CheckerVO> checkList = new ArrayList<CheckerVO>();	
	private List<RiskWorkActivityVO> riskFactorList = new ArrayList<RiskWorkActivityVO>();
	
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

	public int getApproval_state() {
		return approval_state;
	}

	public void setApproval_state(int approval_state) {
		this.approval_state = approval_state;
	}
	
	public int getApproval_order() {
		return approval_order;
	}

	public void setApproval_order(int approval_order) {
		this.approval_order = approval_order;
	}

	public String getApproval_state_name() {
		return approval_state_name;
	}

	public void setApproval_state_name(String approval_state_name) {
		this.approval_state_name = approval_state_name;
	}

	public int getWork_state() {
		return work_state;
	}

	public void setWork_state(int work_state) {
		this.work_state = work_state;
	}

	public String getWork_state_name() {
		return work_state_name;
	}

	public void setWork_state_name(String work_state_name) {
		this.work_state_name = work_state_name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRisk_start() {
		return risk_start;
	}

	public void setRisk_start(String risk_start) {
		this.risk_start = risk_start;
	}

	public String getRisk_end() {
		return risk_end;
	}

	public void setRisk_end(String risk_end) {
		this.risk_end = risk_end;
	}

	public String getWriter_id() {
		return writer_id;
	}

	public void setWriter_id(String writer_id) {
		this.writer_id = writer_id;
	}	

	public String getApprover_id() {
		return approver_id;
	}

	public void setApprover_id(String approver_id) {
		this.approver_id = approver_id;
	}

	public String getWriter_name() {
		return writer_name;
	}

	public void setWriter_name(String writer_name) {
		this.writer_name = writer_name;
	}

	public String getWriter_cont_name() {
		return writer_cont_name;
	}

	public void setWriter_cont_name(String writer_cont_name) {
		this.writer_cont_name = writer_cont_name;
	}

	public String getLast_update_time() {
		return last_update_time;
	}

	public void setLast_update_time(String last_update_time) {
		this.last_update_time = last_update_time;
	}

	public String getWrite_time() {
		return write_time;
	}

	public void setWrite_time(String write_time) {
		this.write_time = write_time;
	}

	public List<ApprovalVO> getApprovalList() {
		return approvalList;
	}

	public void setApprovalList(List<ApprovalVO> approvalList) {
		this.approvalList = approvalList;
	}

	public List<CheckerVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<CheckerVO> checkList) {
		this.checkList = checkList;
	}

	public List<RiskWorkActivityVO> getRiskFactorList() {
		return riskFactorList;
	}

	public void setRiskFactorList(List<RiskWorkActivityVO> riskFactorList) {
		this.riskFactorList = riskFactorList;
	}

	public String getUseyn() {
		return useyn;
	}

	public void setUseyn(String useyn) {
		this.useyn = useyn;
	}	
	
	
}
