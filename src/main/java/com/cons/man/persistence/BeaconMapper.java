package com.cons.man.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cons.man.domain.BeaconVO;
import com.cons.man.domain.TargetVO;

@Repository(value="BeaconMapper")
public interface BeaconMapper {

	public List<BeaconVO> getBeaconList(@Param("site_id")int site_id);
	
	public List<BeaconVO> getUnUseBeaconList(@Param("site_id")int site_id);	
	
	public List<BeaconVO> getUseBeaconList(@Param("site_id")int site_id, @Param("role")int role);
	
	public List<TargetVO> getBeaconTargetList(@Param("site_id")int site_id, @Param("role")int role);
	
	public int updateBeaconData(@Param("idx")int idx, @Param("u_id") String u_id, @Param("role") int role);
	
	public void unAssignBeaconData(@Param("idx")int idx);
	
	public List<BeaconVO> checkBeaconDuplicate(@Param("mac_addr") String mac_addr, @Param("idx")int idx);
	
	public BeaconVO getBeaconMacAddrData(@Param("site_id")int site_id, @Param("idx")int idx);
	
	public void insertBeaconItem(@Param("site_id")int site_id, @Param("idx")int idx, @Param("mac_addr") String mac_addr);
	
	public void deleteBeaconItem(@Param("idx")int idx, @Param("mac_addr") String mac_addr);
	
	public void updateBeaconMacAddr(@Param("site_id")int site_id, @Param("idx")int idx, @Param("mac_addr") String mac_addr);
	
	
	public List<BeaconVO> getBeaconListByCont(@Param("site_id")int site_id, @Param("cont_id")int cont_id);
}
