package com.cons.man.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.cons.man.domain.ApprovalVO;
import com.cons.man.domain.CheckerVO;
import com.cons.man.domain.CommentVO;
import com.cons.man.domain.CtgoRiskFactorVO;
import com.cons.man.domain.EquipCategoryVO;
import com.cons.man.domain.EquipVO;
import com.cons.man.domain.FileVO;
import com.cons.man.domain.NoticeVO;
import com.cons.man.domain.RiskFactorVO;
import com.cons.man.domain.RiskManagPlanVO;
import com.cons.man.domain.RiskWorkActivityVO;
import com.cons.man.domain.RiskWorkStepVO;
import com.cons.man.domain.RiskVO;
import com.cons.man.domain.WorkTypeVO;
import com.cons.man.domain.WorkerVO;
import com.cons.man.persistence.EquipMapper;
import com.cons.man.persistence.FileMapper;
import com.cons.man.persistence.NoticeMapper;
import com.cons.man.persistence.PTWMapper;
import com.cons.man.persistence.RiskMapper;
import com.cons.man.persistence.SiteMapper;
import com.cons.man.util.FileWriter;
import com.cons.man.util.UIDMaker;
import com.cons.man.util.FileWriter.PARENT_TYPE;
import com.cons.man.util.PhoneFormat;

@Service(value="RiskService")
public class RiskService {	
	
	@Resource( name="RiskMapper" )
	private RiskMapper riskMapper;
	
	public List<RiskVO> getRiskList(int site_id, int cont_id, int approval_state, String risk_start, String risk_end) {
		
		List<RiskVO> riskList = riskMapper.getRiskList(site_id, cont_id, approval_state, risk_start, risk_end);
		
		return riskList;	
	}	
	
	public RiskVO getRiskData(int id) {		
		
		RiskVO vo = riskMapper.getRiskData(id);
		
		List<ApprovalVO> approvalList = riskMapper.getApprovalList(1, id);		
		
		if(approvalList != null){		
			vo.setApprovalList(approvalList);
		}
		else {
			//System.out.println("[approval-list] 결재선 is NULL");
		}
		
		List<CheckerVO> checkerList = riskMapper.getCheckerlList(id);		
		
		if(checkerList != null){		
			vo.setCheckList(checkerList);
		}
		else {
			//System.out.println("[check-list] 점검대상자 is NULL");
		}
		
		
		ArrayList<RiskWorkActivityVO> waList = riskMapper.getRiskWorkActivitylList(id);
		
		for(RiskWorkActivityVO wa : waList) {
			
			List<RiskWorkStepVO> wsList = riskMapper.getRiskWorkStepData(id, wa.getIdx());
			wa.setWsList(wsList);
			
			for(RiskWorkStepVO ws : wsList) {
				
				List<RiskFactorVO> rfList = riskMapper.getRiskFactorData(id, ws.getWa_idx(), ws.getIdx());
				ws.setRfList(rfList);
				
				for(RiskFactorVO rf : rfList) {					
					List<RiskManagPlanVO> mpList = riskMapper.getRiskManagementData(id, rf.getWa_idx(), rf.getWs_idx(), rf.getIdx());
					rf.setMpList(mpList);					
				}
				
			}
		}
		
		if(waList.size() > 0){		
			vo.setRiskFactorList(waList);
		}
		else {
			//System.out.println("[Risk-Assessment] " + id + "번 위험성평가 is NULL");
		}
		
		replaceNewLineToBR(vo);
		return vo;
	}	

	private void replaceNewLineToBR(RiskVO vo) {
		if(vo.getRemark() != null) {
			vo.setRemark(vo.getRemark().replaceAll("(\\r|\\n|\\r\\n)+","<br>"));
		}		
	}	
	
	public void requestRisk(int risk_id, String user_id) {		
		riskMapper.setRiskApproval(risk_id, user_id);			
		int approver_count = riskMapper.getCountApprover(1, risk_id);
		System.out.println("[결재요청] 결재선 수:" + approver_count);
		if(approver_count == 1) {
			riskMapper.RAStepEnd(risk_id);
		}
		else {
			riskMapper.RAInitRequest(risk_id);
		}
	}
	
	public void approvalRisk(int risk_id, String user_id, int approval_order) {
		int approver_count = riskMapper.getCountApprover(1, risk_id);
		System.out.println("[승인] 결재선 수:" + approver_count + " / 관리자의 순서: " + approval_order);
		riskMapper.setRiskApproval(risk_id, user_id);
		if(approver_count == approval_order) {
			riskMapper.RAStepEnd(risk_id);
		}
		else {
			riskMapper.RAStepProceed(risk_id);
		}
	}
	
	public void cancelRisk(int risk_id, String user_id, String content) {		
		String remark = "반려사유: " + content;
		riskMapper.setRiskCancel(risk_id, user_id);
		riskMapper.RACancel(risk_id, remark);		
	}
	
	public List<WorkTypeVO> getFactorTypeList(int site_id) {
		return riskMapper.getFactorTypeList(site_id);
	}
	
	public List<CtgoRiskFactorVO> getFactorListByTitle() {
		return riskMapper.getFactorListByTitle();
	}
	
	/*
	public List<RiskFactorVO> getRiskFactorList(int id) {
		
		List<RiskFactorVO> list = riskMapper.getRiskFactorList(code_level_one, code_level_two, code_level_three);
		
		
		
		return list;
	}
	*/
	
	public List<CtgoRiskFactorVO> getFactorDetailList(String code_level_one, String code_level_two, String code_level_three) {
		
		List<CtgoRiskFactorVO> list = riskMapper.getFactorDetailList(code_level_one, code_level_two, code_level_three);
		
		for(CtgoRiskFactorVO vo : list) {
			
			int risk_rating = vo.getFrequency() * vo.getStrength();
			if(risk_rating >= 9) {
				vo.setRating("상");
			}
			else if(risk_rating >= 4) {
				vo.setRating("중");
			}
			else {
				vo.setRating("하");
			}
			
			List<String> mList = riskMapper.getManagementMethodList(vo.getCode());
			
			if(mList != null) {
				vo.setmList(mList);	
			}
			
		}
		
		return list;
	}
	
	
	public void insertRiskData (RiskVO riskVO) {	
		
		JSONObject jo = new JSONObject();		
		int riskId = 0;		
		
		riskId = riskMapper.insertRiskData(riskVO);
		String writerId = riskVO.getWriter_id();
	
		if(riskVO.getApprovalList() != null){					
			for(int a=0; a<riskVO.getApprovalList().size(); a++) {
				int order = a+1;
				riskMapper.updateApprovalLine(1, riskVO.getId(), riskVO.getApprovalList().get(a).getUser_id(), order);
			}
		}		

		if(riskVO.getCheckList() != null){					
			for(int c=0; c<riskVO.getCheckList().size(); c++) {						
				riskMapper.updateCheckerUser(riskVO.getId(), riskVO.getCheckList().get(c).getUser_id());
			}
		}
		
		if(riskVO.getRiskFactorList() != null){
			
			initRiskFactorList(riskId);
			
			int waIdx = 1;
			
			//System.out.println("----------------------------------------------------------------------------------------------------------------------");
			//System.out.println("[위험성평가표] 총 " + riskVO.getRiskFactorList().size() + "개");
			
			for(int r=0; r<riskVO.getRiskFactorList().size(); r++) {
					
				String className = riskVO.getRiskFactorList().get(r).getClass_name();
				
				if(className == null) {					
					continue;
				}
				else {
					String waCode = riskVO.getRiskFactorList().get(r).getWa_code();
					String code = riskVO.getRiskFactorList().get(r).getCode();
					
					//System.out.println("[" + waIdx + "] 작업활동 코드: " + waCode);
					//System.out.println("[" + waIdx + "] 세부 공종: " + className);
					//System.out.println("[" + waIdx + "] 코드: " + code);
					
					if(riskVO.getRiskFactorList().get(r).getCode() != null) {
						//System.out.println("[" + waIdx + "] 는 항목등록에서 가져온 데이터");	
						riskMapper.insertRiskWorkActivityData(riskId, waIdx, 1, waCode, className, code, writerId);
					}
					else {
						//System.out.println("[" + waIdx + "] 는 추후 New Code 생성 필요");
						riskMapper.insertRiskWorkActivityData(riskId, waIdx, 2, "", "", "", writerId);
					}					
					
					//System.out.println("----------------------------------------------------------------------------------------------------------------------");
					
					if(riskVO.getRiskFactorList().get(r).getWsList() != null) {						
						
						int wsIdx = 1;
						
						//System.out.println("[" + waIdx + "] 번 위험성평가의 작업단계 " + riskVO.getRiskFactorList().get(r).getWsList().size()+ "개");						
						
						// 작업단계
						for(int s=0; s<riskVO.getRiskFactorList().get(r).getWsList().size(); s++) {
							
							String workContent = riskVO.getRiskFactorList().get(r).getWsList().get(s).getWork_content();
							
							if(workContent == null) {
								continue;
							}
							else {
								
								String equipName = riskVO.getRiskFactorList().get(r).getWsList().get(s).getEquip_name();							
								int equipCnt = riskVO.getRiskFactorList().get(r).getWsList().get(s).getEquip_cnt();
								String workStart = riskVO.getRiskFactorList().get(r).getWsList().get(s).getWork_start();
								String workEnd = riskVO.getRiskFactorList().get(r).getWsList().get(s).getWork_end();
								String sectionName = riskVO.getRiskFactorList().get(r).getWsList().get(s).getSection_name();
								int engineerCnt = riskVO.getRiskFactorList().get(r).getWsList().get(s).getEngineer_cnt();
								
								//System.out.println("[" + waIdx + "-" + wsIdx + "] 작업 내용: " + workContent);
								//System.out.println("[" + waIdx + "-" + wsIdx + "] 사용 장비: " + equipName);
								//System.out.println("[" + waIdx + "-" + wsIdx + "] 장비 수: " + equipCnt);
								//System.out.println("[" + waIdx + "-" + wsIdx + "] 장비 기간: " + workStart + " ~ " + workEnd);
								//System.out.println("[" + waIdx + "-" + wsIdx + "] 작업 위치: " + sectionName);
								//System.out.println("[" + waIdx + "-" + wsIdx + "] 인원 수: " + engineerCnt);
																
								riskMapper.insertRiskWorkStepData(riskId, waIdx, wsIdx, workContent, equipName, equipCnt, workStart, workEnd, sectionName, engineerCnt);
							
								//System.out.println("----------------------------------------------------------------------------------------------------------------------");
								
								if(riskVO.getRiskFactorList().get(r).getWsList().get(s).getRfList() != null) {
									int raIdx = 1;
									
									//System.out.println("[" + waIdx + "-" + wsIdx + "] 번 작업단계의 위험성평가 " + riskVO.getRiskFactorList().get(r).getWsList().get(s).getRfList().size()+ "개");						
									
									for(int a=0; a<riskVO.getRiskFactorList().get(r).getWsList().get(s).getRfList().size(); a++) {
										
										String riskContent = riskVO.getRiskFactorList().get(r).getWsList().get(s).getRfList().get(a).getRisk_content();
										
										if(riskContent == null) {
											continue;
										}
										else {
											
											String damageForm = riskVO.getRiskFactorList().get(r).getWsList().get(s).getRfList().get(a).getDamage_form();
											int frequency = riskVO.getRiskFactorList().get(r).getWsList().get(s).getRfList().get(a).getFrequency();
											int strength = riskVO.getRiskFactorList().get(r).getWsList().get(s).getRfList().get(a).getStrength();
											String rating = riskVO.getRiskFactorList().get(r).getWsList().get(s).getRfList().get(a).getRating();
											
											//System.out.println("[" + waIdx + "-" + wsIdx + "-" + raIdx + "] 피해형태: " + workContent);
											//System.out.println("[" + waIdx + "-" + wsIdx + "-" + raIdx + "] 빈도: " + frequency);
											//System.out.println("[" + waIdx + "-" + wsIdx + "-" + raIdx + "] 강도: " + strength);
											//System.out.println("[" + waIdx + "-" + wsIdx + "-" + raIdx + "] 등급: " + rating);
																																	
											riskMapper.insertRiskFactorData(riskId, waIdx, wsIdx, raIdx, riskContent, damageForm, frequency, strength, rating);
									
											//System.out.println("----------------------------------------------------------------------------------------------------------------------");											
											
											int rmidx = 1;											
											
											//System.out.println("[" + waIdx + "-" + wsIdx + "-" + raIdx + "] 번 위험성평가의 관리방안 " + riskVO.getRiskFactorList().get(r).getWsList().get(s).getRfList().get(a).getMpList().size() + "개");
											
											for(int m=0; m<riskVO.getRiskFactorList().get(r).getWsList().get(s).getRfList().get(a).getMpList().size(); m++) {
												
												String managePlan = riskVO.getRiskFactorList().get(r).getWsList().get(s).getRfList().get(a).getMpList().get(m).getContent();
												
												if(managePlan == null) {
													continue;
												}
												else {													
													//System.out.println("[" + waIdx + "-" + wsIdx + "-" + raIdx + "-" + rmidx +"] 관리방안: " + managePlan);
													riskMapper.insertRiskManagementData(riskId, waIdx, wsIdx, raIdx, rmidx, managePlan);
													rmidx++;
												}																					
											}											
											
											raIdx++;
										}
									}
								}
								wsIdx++;
							
							}							
						}
					}
					waIdx++;
				}
			}			
		}		
		
		//System.out.println("-----------------------------------------------------------");
	
	}
	
	public void initRiskFactorList(int risk_id) {
		riskMapper.deleteRiskWorkActivityData(risk_id);
		riskMapper.deleteRiskWorkStepData(risk_id);
		riskMapper.deleteRiskFactorData(risk_id);
		riskMapper.deleteRiskManagementData(risk_id);
	}
	
	public void updateRiskData (RiskVO riskVO) {			
		
		//System.out.println("------------------------ UPDATE ---------------------------");				
		//System.out.println("[writer_id] 작성자: " + riskVO.getWriter_id());
		//System.out.println("[cont_id] 업체: " + riskVO.getCont_id());
		//System.out.println("[content] 평가내용: " + riskVO.getContent());
		//System.out.println("[risk_start ~ risk_end] 평가기간: " + riskVO.getRisk_start() + " ~ " + riskVO.getRisk_end());
		//System.out.println("[remark] 메모사항: " + riskVO.getRemark());
		//System.out.println("\n");
		
		riskMapper.updateRiskData(riskVO);
		
		//System.out.println("[New] 결재선 초기화: " + riskVO.getId() + "번 위험성평가");
		riskMapper.deleteApprovalLine(1, riskVO.getId());
		riskMapper.deleteCheckerUser(riskVO.getId());
		
		if(riskVO.getApprovalList() != null){					
			for(int a=0; a<riskVO.getApprovalList().size(); a++) {
				int order = a+1;
				//System.out.println(1 + "/" + riskVO.getId() + "/" + riskVO.getApprovalList().get(a).getUser_id() + "/" + order);
				riskMapper.updateApprovalLine(1, riskVO.getId(), riskVO.getApprovalList().get(a).getUser_id(), order);
			}
		}
		else {
			//System.out.println("[approval-list] 결재선 is NULL");
		}
		
		//System.out.println("\n");
		
		if(riskVO.getCheckList() != null){					
			for(int c=0; c<riskVO.getCheckList().size(); c++) {								
				riskMapper.updateCheckerUser(riskVO.getId(), riskVO.getCheckList().get(c).getUser_id());
			}
		}
		else {
			//System.out.println("[check-list] 점검대상자 is NULL");
		}
		
		//System.out.println("-----------------------------------------------------------");
		
		int riskId = riskVO.getId();
		
		if(riskVO.getRiskFactorList() != null){
			
			initRiskFactorList(riskId);
			
			int waIdx = 1;
			
			//System.out.println("----------------------------------------------------------------------------------------------------------------------");
			//System.out.println("[위험성평가표] 총 " + riskVO.getRiskFactorList().size() + "개");
			
			for(int r=0; r<riskVO.getRiskFactorList().size(); r++) {
					
				String className = riskVO.getRiskFactorList().get(r).getClass_name();
				
				if(className == null) {					
					continue;
				}
				else {
					String waCode = riskVO.getRiskFactorList().get(r).getWa_code();
					String code = riskVO.getRiskFactorList().get(r).getCode();
					
					//System.out.println("[" + waIdx + "] 작업활동 코드: " + waCode);
					//System.out.println("[" + waIdx + "] 세부 공종: " + className);
					//System.out.println("[" + waIdx + "] 코드: " + code);
					
					if(riskVO.getRiskFactorList().get(r).getCode() != null) {
						//System.out.println("[" + waIdx + "] 는 항목등록에서 가져온 데이터");	
						riskMapper.insertRiskWorkActivityData(riskId, waIdx, 1, waCode, className, code, riskVO.getWriter_id());
					}
					else {
						//System.out.println("[" + waIdx + "] 는 추후 New Code 생성 필요");
						riskMapper.insertRiskWorkActivityData(riskId, waIdx, 2, "", "", "", riskVO.getWriter_id());
					}					
					
					//System.out.println("----------------------------------------------------------------------------------------------------------------------");
					
					if(riskVO.getRiskFactorList().get(r).getWsList() != null) {						
						
						int wsIdx = 1;
						
						//System.out.println("[" + waIdx + "] 번 위험성평가의 작업단계 " + riskVO.getRiskFactorList().get(r).getWsList().size()+ "개");						
						
						// 작업단계
						for(int s=0; s<riskVO.getRiskFactorList().get(r).getWsList().size(); s++) {
							
							String workContent = riskVO.getRiskFactorList().get(r).getWsList().get(s).getWork_content();
							
							if(workContent == null) {
								continue;
							}
							else {
								
								String equipName = riskVO.getRiskFactorList().get(r).getWsList().get(s).getEquip_name();							
								int equipCnt = riskVO.getRiskFactorList().get(r).getWsList().get(s).getEquip_cnt();
								String workStart = riskVO.getRiskFactorList().get(r).getWsList().get(s).getWork_start();
								String workEnd = riskVO.getRiskFactorList().get(r).getWsList().get(s).getWork_end();
								String sectionName = riskVO.getRiskFactorList().get(r).getWsList().get(s).getSection_name();
								int engineerCnt = riskVO.getRiskFactorList().get(r).getWsList().get(s).getEngineer_cnt();
								
								//System.out.println("[" + waIdx + "-" + wsIdx + "] 작업 내용: " + workContent);
								//System.out.println("[" + waIdx + "-" + wsIdx + "] 사용 장비: " + equipName);
								//System.out.println("[" + waIdx + "-" + wsIdx + "] 장비 수: " + equipCnt);
								//System.out.println("[" + waIdx + "-" + wsIdx + "] 장비 기간: " + workStart + " ~ " + workEnd);
								//System.out.println("[" + waIdx + "-" + wsIdx + "] 작업 위치: " + sectionName);
								//System.out.println("[" + waIdx + "-" + wsIdx + "] 인원 수: " + engineerCnt);
																
								riskMapper.insertRiskWorkStepData(riskId, waIdx, wsIdx, workContent, equipName, equipCnt, workStart, workEnd, sectionName, engineerCnt);
							
								//System.out.println("----------------------------------------------------------------------------------------------------------------------");
								
								if(riskVO.getRiskFactorList().get(r).getWsList().get(s).getRfList() != null) {
									int raIdx = 1;
									
									//System.out.println("[" + waIdx + "-" + wsIdx + "] 번 작업단계의 위험성평가 " + riskVO.getRiskFactorList().get(r).getWsList().get(s).getRfList().size()+ "개");						
									
									for(int a=0; a<riskVO.getRiskFactorList().get(r).getWsList().get(s).getRfList().size(); a++) {
										
										String riskContent = riskVO.getRiskFactorList().get(r).getWsList().get(s).getRfList().get(a).getRisk_content();
										
										if(riskContent == null) {
											continue;
										}
										else {
											
											String damageForm = riskVO.getRiskFactorList().get(r).getWsList().get(s).getRfList().get(a).getDamage_form();
											int frequency = riskVO.getRiskFactorList().get(r).getWsList().get(s).getRfList().get(a).getFrequency();
											int strength = riskVO.getRiskFactorList().get(r).getWsList().get(s).getRfList().get(a).getStrength();
											String rating = riskVO.getRiskFactorList().get(r).getWsList().get(s).getRfList().get(a).getRating();
											
											//System.out.println("[" + waIdx + "-" + wsIdx + "-" + raIdx + "] 피해형태: " + workContent);
											//System.out.println("[" + waIdx + "-" + wsIdx + "-" + raIdx + "] 빈도: " + frequency);
											//System.out.println("[" + waIdx + "-" + wsIdx + "-" + raIdx + "] 강도: " + strength);
											//System.out.println("[" + waIdx + "-" + wsIdx + "-" + raIdx + "] 등급: " + rating);
																																	
											riskMapper.insertRiskFactorData(riskId, waIdx, wsIdx, raIdx, riskContent, damageForm, frequency, strength, rating);
									
											//System.out.println("----------------------------------------------------------------------------------------------------------------------");											
											
											int rmidx = 1;											
											
											//System.out.println("[" + waIdx + "-" + wsIdx + "-" + raIdx + "] 번 위험성평가의 관리방안 " + riskVO.getRiskFactorList().get(r).getWsList().get(s).getRfList().get(a).getMpList().size() + "개");
											
											for(int m=0; m<riskVO.getRiskFactorList().get(r).getWsList().get(s).getRfList().get(a).getMpList().size(); m++) {
												
												String managePlan = riskVO.getRiskFactorList().get(r).getWsList().get(s).getRfList().get(a).getMpList().get(m).getContent();
												
												if(managePlan == null) {
													continue;
												}
												else {													
													//System.out.println("[" + waIdx + "-" + wsIdx + "-" + raIdx + "-" + rmidx +"] 관리방안: " + managePlan);
													riskMapper.insertRiskManagementData(riskId, waIdx, wsIdx, raIdx, rmidx, managePlan);
													rmidx++;
												}																					
											}											
											
											raIdx++;
										}
									}
								}
								wsIdx++;
							
							}							
						}
					}
					waIdx++;
				}
			}			
		}		
		
		//System.out.println("-----------------------------------------------------------");
		
	}
	
	public void deleteRiskData (int id) {	
		riskMapper.deleteRiskData(id);
		riskMapper.deleteApprovalLine(1, id);
		riskMapper.deleteCheckerUser(id);
		riskMapper.deleteRiskManagementData(id);
	}
	
	
}
