package com.cons.man.persistence;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cons.man.domain.ApprovalVO;
import com.cons.man.domain.CheckerVO;
import com.cons.man.domain.CtgoRiskFactorVO;
import com.cons.man.domain.RiskFactorVO;
import com.cons.man.domain.RiskManagPlanVO;
import com.cons.man.domain.RiskWorkActivityVO;
import com.cons.man.domain.RiskVO;
import com.cons.man.domain.RiskWorkStepVO;
import com.cons.man.domain.WorkTypeVO;

@Repository(value="RiskMapper")
public interface RiskMapper {
	
	public List<RiskVO> getRiskList(@Param("site_id")int site_id, @Param("cont_id")int cont_id, @Param("approval_state")int approval_state, @Param("risk_start")String risk_start, @Param("risk_end")String risk_end);
	
	public RiskVO getRiskData(@Param("id")int id);
	
	public List<WorkTypeVO> getFactorTypeList(@Param("site_id")int site_id);
	
	public List<CtgoRiskFactorVO> getFactorListByTitle();
	
	public List<CtgoRiskFactorVO> getFactorDetailList(@Param("code_level_one")String code_level_one, @Param("code_level_two")String code_level_two, @Param("code_level_three")String code_level_three);
	
	public List<String> getManagementMethodList(@Param("cf_code") String cf_code);
	
	public int insertRiskData(RiskVO vo);
	
	public void updateRiskData(RiskVO vo);
	
	public void deleteRiskData(@Param("id")int id);
	
	public void updateApprovalLine(@Param("type")int type, @Param("content_id")int content_id, @Param("user_id")String user_id, @Param("order")int order);
	
	public void deleteApprovalLine(@Param("type")int type, @Param("content_id")int content_id);
	
	public void updateCheckerUser(@Param("content_id")int content_id, @Param("user_id")String user_id);
	
	public void deleteCheckerUser(@Param("risk_id")int risk_id);
	
	public List<ApprovalVO> getApprovalList(@Param("type")int type, @Param("content_id")int content_id);
	
	public int getCountApprover(@Param("type")int type, @Param("content_id")int content_id);
	
	public List<CheckerVO> getCheckerlList(@Param("risk_id")int risk_id);	

	public void insertRiskWorkActivityData(@Param("risk_id")int risk_id, @Param("idx")int idx, @Param("type")int type, @Param("wa_code") String wa_code, 
			@Param("class_name") String class_name, @Param("code")String code, @Param("writer_id") String writer_id);
	
	
	public void insertRiskWorkStepData(@Param("risk_id")int risk_id, @Param("wa_idx")int wa_idx, @Param("idx")int idx, @Param("work_content")String work_content, 
			@Param("equip_name")String equip_name, @Param("equip_cnt") int equip_cnt, @Param("work_start") String work_start,
			@Param("work_end") String work_end, @Param("section_name") String section_name, @Param("engineer_cnt")int engineer_cnt);	
	
	public void insertRiskFactorData(@Param("risk_id")int risk_id, @Param("wa_idx")int wa_idx, @Param("ws_idx")int ws_idx, @Param("idx")int idx, @Param("risk_content")String risk_content,
			@Param("damage_form")String damage_form, @Param("frequency") int frequency, @Param("strength") int strength, @Param("rating")String rating);
		
	public void insertRiskManagementData(@Param("risk_id")int risk_id, @Param("wa_idx")int wa_idx, @Param("ws_idx")int ws_id, @Param("rf_idx")int rf_idx, 
			@Param("idx")int idx, @Param("content")String content);
	
	public void deleteRiskWorkActivityData(@Param("risk_id")int risk_id);
	public void deleteRiskWorkStepData(@Param("risk_id")int risk_id);
	public void deleteRiskFactorData(@Param("risk_id")int risk_id);
	public void deleteRiskManagementData(@Param("risk_id")int risk_id);
	
	public ArrayList<RiskWorkActivityVO> getRiskWorkActivitylList(@Param("risk_id")int risk_id);
	public ArrayList<RiskWorkStepVO> getRiskWorkStepData(@Param("risk_id")int risk_id, @Param("wa_idx")int wa_idx);
	public ArrayList<RiskFactorVO> getRiskFactorData(@Param("risk_id")int risk_id, @Param("wa_idx")int wa_idx, @Param("ws_idx")int ws_idx);
	public ArrayList<RiskManagPlanVO> getRiskManagementData(@Param("risk_id")int risk_id, @Param("wa_idx")int wa_idx, @Param("ws_idx")int ws_id, @Param("rf_idx")int rf_idx);
	
	public void setRiskApproval(@Param("id")int id, @Param("approver_id")String approver_id);	
	public void setRiskCancel(@Param("id")int id, @Param("approver_id")String approver_id);	
	
	public void RAInitRequest(@Param("id")int id);
	public void RAStepProceed(@Param("id")int id);
	public void RAStepEnd(@Param("id")int id);	
	public void RACancel(@Param("id")int id, @Param("remark")String remark);
	
	
}
