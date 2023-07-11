package com.cons.man.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cons.man.domain.ContVO;
import com.cons.man.domain.SectionVO;
import com.cons.man.domain.UserVO;
import com.cons.man.domain.WorkerVO;

@Repository(value="QRMapper")
public interface QRMapper {
	
	public int checkUWDuplicatePhone(String phone);
	
	public int insertUserTemp(UserVO vo);
	
	public int insertWorkerTemp(WorkerVO vo);
	
	public int insertQRInData(@Param("site_id")int site_id, @Param("uw_id")String uw_id, @Param("role")int role);
	
	public int insertQROutData(@Param("site_id")int site_id, @Param("uw_id")String uw_id, @Param("role")int role);
	
	public void insertQRInoutLog(@Param("site_id")int site_id, @Param("uw_id")String uw_id, @Param("role")int role, @Param("type")int type, @Param("comment")String comment);
}
