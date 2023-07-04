package com.cons.man.persistence;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cons.man.domain.ApprovalVO;
import com.cons.man.domain.EquipCategoryVO;
import com.cons.man.domain.EquipCheckVO;
import com.cons.man.domain.EquipVO;
import com.cons.man.domain.NoticeVO;
import com.cons.man.domain.RiskVO;

@Repository(value="ApprovalMapper")
public interface ApprovalMapper {
	
	public ArrayList<ApprovalVO> getApprovalList(@Param("type")int type);
	
	
	
}
