package com.cons.man.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cons.man.domain.BeaconVO;
import com.cons.man.domain.NFCVO;
import com.cons.man.domain.NfcLocationVO;
import com.cons.man.domain.TargetVO;

@Repository(value="NFCMapper")
public interface NFCMapper {

	
	public List<NFCVO> getNFCList(@Param("site_id")int site_id);
	
	public List<NFCVO> getUnUseNFCList(@Param("site_id")int site_id);	
	
	public List<NFCVO> getUseNFCList(@Param("site_id")int site_id, @Param("role")int role);
	
	public List<TargetVO> getNFCTargetList(@Param("site_id")int site_id, @Param("role")int role);
	
	public void createNFCData(@Param("site_id")int site_id, @Param("serial_number") String serial_number);
	
	public void createNFCDataByIdx(@Param("site_id")int site_id, @Param("idx")int idx,@Param("serial_number") String serial_number);
	
	public int updateNFCData(@Param("idx")int idx, @Param("u_id") String u_id, @Param("role") int role);
	
	// FROM SEG
	public NFCVO checkNFCDuplicate(@Param("site_id")int site_id, @Param("serial_number")String serial_number);
	
	public NFCVO checkNFCState(@Param("site_id")int site_id, @Param("serial_number")String serial_number); 
	
	public List<NFCVO> checkNFCDuplicateByIdx(@Param("serial_number") String serial_number, @Param("idx")int idx);
	
	public void unAassignNFCData(@Param("serial_number") String serial_number, @Param("idx")int idx);	
	
	public List<Integer> getRemainNFCList(@Param("site_id")int site_id);
	
	public Integer checkNFCUpdateData(@Param("idx")int idx);
	
	public void insertNFCItem(@Param("site_id")int site_id, @Param("idx")int idx, @Param("serial_number") String serial_number);
	
	public void deleteNFCItem(@Param("idx")int idx, @Param("serial_number") String serial_number);
	
	public void updateNFCByIdx(@Param("site_id")int site_id, @Param("idx")int idx, @Param("serial_number") String serial_number);
	
	public List<NFCVO> getNFCListByCont(@Param("site_id")int site_id, @Param("cont_id")int cont_id);	

	public void unAssignNFCDataByidx(@Param("idx")int idx);	
	
	public List<NfcLocationVO> getWaitList(@Param("site_id")int site_id, @Param("place_id")int place_id, @Param("section")int section);
	
	public void postWaitOut(@Param("uw_id")String uw_id, @Param("role")int role, @Param("serial_number")String serial_number);	

}
