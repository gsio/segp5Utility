package com.cons.man.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cons.man.domain.MobileUserVO;
import com.cons.man.domain.WorkerListVO;
import com.cons.man.domain.WorkerVO;

@Repository(value="WorkerMapper")
public interface WorkerMapper {
	
	public List<WorkerVO> checkWorkerPhone(String phone);
	
	public WorkerVO getWorker(int worker_id);
	
	public List<WorkerVO> getWorkRecord(@Param("site_id")int site_id, @Param("cont_id")int cont_id, @Param("isOutcome")boolean outcome);
	
	public List<WorkerVO> getWorkerTypeList();
	
	public void insertWorker(WorkerVO vo);
	
	public void updateRecord(WorkerVO workerVO);
	
	public List<WorkerVO> getWorkerListByContId(WorkerVO vo);	
	
	public void deleteWorker(@Param("site_id")int site_id, @Param("id")int id);
	
	public void updateEduImageByWorkerId(WorkerVO vo);
	
	public List<WorkerVO> getDriverList(@Param("site_id")int site_id, @Param("cont_id")int cont_id);
	
	public List<WorkerVO> getWTList();	
	
//	------------------------------------------------------------------------
	
}