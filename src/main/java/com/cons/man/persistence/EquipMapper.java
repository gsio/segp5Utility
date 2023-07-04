package com.cons.man.persistence;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cons.man.domain.EquipCategoryVO;
import com.cons.man.domain.EquipCheckVO;
import com.cons.man.domain.EquipVO;
import com.cons.man.domain.NoticeVO;

@Repository(value="EquipMapper")
public interface EquipMapper {
	
	public ArrayList<EquipVO> getEquipList(@Param("site_id")int site_id, @Param("cont_id")int cont_id);
	
	public EquipVO getEquipData(@Param("id")int id);
	
	public List<EquipCategoryVO> getEquipCategory(@Param("site_id")int site_id);
	
	public int insertEquipData(EquipVO vo);
	
	public void updateEquipData(EquipVO vo);		
	
	public List<EquipCheckVO> getEquipCheckList(@Param("id")int id);
}
