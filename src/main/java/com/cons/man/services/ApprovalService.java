package com.cons.man.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.cons.man.domain.ApprovalVO;
import com.cons.man.domain.CommentVO;
import com.cons.man.domain.ContVO;
import com.cons.man.domain.EquipCategoryVO;
import com.cons.man.domain.EquipVO;
import com.cons.man.domain.FileVO;
import com.cons.man.domain.NoticeVO;
import com.cons.man.domain.RiskVO;
import com.cons.man.persistence.ApprovalMapper;
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

@Service(value="ApprovalService")
public class ApprovalService {	
	
	@Resource( name="ApprovalMapper" )
	private ApprovalMapper approvalMapper;
	

	public List<ApprovalVO> getApprovalList(int type) {
		return approvalMapper.getApprovalList(type);
	}
	
	
}
