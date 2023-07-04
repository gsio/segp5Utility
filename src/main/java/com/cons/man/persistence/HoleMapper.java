package com.cons.man.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cons.man.domain.BeaconVO;
import com.cons.man.domain.HoleVO;

@Repository(value="HoleMapper")
public interface HoleMapper {

	public List<HoleVO> getHoleList(@Param("site_id")int site_id);
	
	public List<HoleVO> getUnUseHoleList(@Param("site_id")int site_id);	
	
	public List<HoleVO> getUseHoleList(@Param("site_id")int site_id);	
	
	public List<HoleVO> getHoleAssignSectionList(@Param("site_id")int site_id);
	
	public List<HoleVO> getHoleManageList(@Param("site_id")int site_id);
	
	public List<HoleVO> checkHoleDuplicate(@Param("mac_addr") String mac_addr, @Param("idx")int idx);
	
	public HoleVO getHoleMacAddrByIdx(@Param("site_id")int site_id, @Param("idx")int idx);
	
	public void insertHoleItem(@Param("site_id")int site_id, @Param("idx")int idx, @Param("mac_addr") String mac_addr);
	
	public void deleteHoleItem(@Param("idx")int idx, @Param("mac_addr") String mac_addr);
	
	public void updateHoleMacAddr(@Param("site_id")int site_id, @Param("idx")int idx, @Param("mac_addr") String mac_addr);

	public void unAssignHoleData(@Param("id")int id);
		
	public void updateHoleData(@Param("id")int id, @Param("section")int section);
		
	public void updateHoleName(@Param("id")int id, @Param("name")String name);
}
