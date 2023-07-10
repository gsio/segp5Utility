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
}
