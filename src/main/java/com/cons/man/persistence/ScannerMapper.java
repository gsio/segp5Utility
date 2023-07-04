package com.cons.man.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cons.man.domain.ScannerVO;

@Repository(value="ScannerMapper")
public interface ScannerMapper {

	public List<ScannerVO> getScannerList(@Param("site_id")int site_id);
	
	public List<ScannerVO> getScannerManageList(@Param("site_id")int site_id);
	
	public void updateScannerTimeOut(@Param("section")int section, @Param("time_out")int time_out);	
	
	public void updateScannerWaitOut(@Param("section")int section, @Param("wait_out")int wait_out);		
	
	public void insertScannerData(@Param("site_id")int site_id, @Param("scanner_mac_init")String scanner_mac_init, @Param("scanner_mac")String scanner_mac, @Param("name")String name, @Param("section")int section);
	
	public void deleteScannerData(@Param("site_id")int site_id, @Param("idx")int idx, @Param("scanner_mac")String scanner_mac, @Param("section")int section);
	
	public void activeSectionState(@Param("site_id")int site_id, @Param("section")int section);
	
	public void inActiveSectionState(@Param("site_id")int site_id, @Param("section")int section);

}
