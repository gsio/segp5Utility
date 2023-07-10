package com.cons.man.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cons.man.domain.ContVO;

@Repository(value="ContMapper")
public interface ContMapper {
	
	public List<ContVO> getContList(int site_id);
	
	public ContVO getContInfo(int id);
	
	public void insert(ContVO cont);
	
	public void update(ContVO cont);
	
	public void disableCont(int id);	
	
	public int insertContTemp(ContVO cont);
}
