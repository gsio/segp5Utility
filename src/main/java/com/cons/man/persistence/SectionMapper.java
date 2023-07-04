package com.cons.man.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cons.man.domain.SectionVO;

@Repository(value="SectionMapper")
public interface SectionMapper {

	public List<SectionVO> getSectionList(@Param("site_id")int site_id);
	
	public List<SectionVO> checkSectionDuplicate(@Param("section")int section);
}
