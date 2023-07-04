package com.cons.man.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cons.man.domain.AppVersionCheckVO;
import com.cons.man.domain.TargetVO;
import com.cons.man.domain.BeaconVO;
import com.cons.man.domain.ContVO;
import com.cons.man.domain.UserVO;
import com.cons.man.domain.WorkTypeVO;
import com.cons.man.domain.WorkerVO;

@Repository(value="MobileMapper")
public interface MobileMapper {
	
	public AppVersionCheckVO getCurrentAppVersion();
	
	public void updateLoginInfo(@Param("id")String id, @Param("fcm_token")String fcm_token);
	
	public void insertUserAppVersion(AppVersionCheckVO vo);
	
	public List<UserVO> getUserList(@Param("site_id")int site_id, @Param("cont_id")int cont_id);
	
	public List<ContVO> getUserContList(@Param("site_id")int site_id);
	
	public List<ContVO> getWorkerContList(@Param("site_id")int site_id);
	
	public List<ContVO> getContList(@Param("site_id")int site_id);
	
	public List<WorkerVO> getWorkerList(@Param("site_id")int site_id, @Param("cont_id")int cont_id);
	
	public List<WorkTypeVO> getWorkTypeList(@Param("site_id")int site_id);
	
	public int insertWorker(WorkerVO worker);
	
	public int updateWorker(WorkerVO worker);
	
	public WorkerVO checkPhoneNumber(@Param("phone")String phone);
	
}
