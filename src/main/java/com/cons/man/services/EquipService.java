package com.cons.man.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.cons.man.domain.CommentVO;
import com.cons.man.domain.EquipCategoryVO;
import com.cons.man.domain.EquipVO;
import com.cons.man.domain.FileVO;
import com.cons.man.domain.NoticeVO;
import com.cons.man.persistence.EquipMapper;
import com.cons.man.persistence.FileMapper;
import com.cons.man.persistence.NoticeMapper;
import com.cons.man.persistence.PTWMapper;
import com.cons.man.persistence.SiteMapper;
import com.cons.man.util.FileWriter;
import com.cons.man.util.UIDMaker;
import com.cons.man.util.FileWriter.PARENT_TYPE;
import com.cons.man.util.PhoneFormat;

@Service(value="EquipService")
public class EquipService {	
	
	@Resource( name="EquipMapper" )
	private EquipMapper equipMapper;
	
	@Resource( name="FileMapper" )
	private FileMapper fileMapper;
	
	public List<EquipVO> getEquipList(int site_id, int cont_id) {
		List<EquipVO> equipList = equipMapper.getEquipList(site_id, cont_id);
		for(EquipVO vo : equipList) {
			vo.setDriver_phone(PhoneFormat.transFormPhone(vo.getDriver_phone()));
		}
		return equipList;	
	}	
	
	public EquipVO getEquipData(int id) {		
		EquipVO vo = equipMapper.getEquipData(id);		
		vo.setCheck_list(equipMapper.getEquipCheckList(id));
		vo.setDriver_phone(PhoneFormat.transFormPhone(vo.getDriver_phone()));
		vo.setFile_list(FileWriter.INSTANCE.getFileList(id, FileWriter.PARENT_TYPE.EQUIP, fileMapper));
		return vo;
	}
	
	public List<EquipCategoryVO> getEquipCategory(int site_id) {
		return equipMapper.getEquipCategory(site_id);
	}
	
	public String insertEquipData(EquipVO vo) {	
		
		if(vo.getRemark() != null) {
			vo.setRemark(vo.getRemark().replaceAll("<br>", "\r\n"));
		}		
		
		System.out.println("[Equip - Image]: " + vo.getEquip_img_file() + " / " + vo.getEquip_img_file().getSize());
		
		if(vo.getEquip_img_file() != null && vo.getEquip_img_file().getSize() > 0) {			
			String virtName = "EQUIP/EQUIP_" + UUID.randomUUID().toString() +"_" + vo.getId();
			vo.setEquip_img(virtName);
			FileWriter.INSTANCE.writeFile(vo.getEquip_img_file(), virtName);
		}
		
		JSONObject jo = new JSONObject();		
		int resultInt = 0;			
		resultInt = equipMapper.insertEquipData(vo);
		
		if(vo.getFile_list() != null){
			Iterator<FileVO> it_list =  vo.getFile_list().iterator();
			while(it_list.hasNext()){
				FileVO file =  it_list.next();
				FileWriter.INSTANCE.insertFileDetail(PARENT_TYPE.EQUIP, file, vo.getId(), -1, fileMapper);
			}
		}
			
		if(resultInt > 0) {
			jo.put("result", "true");
			jo.put("equip_id", vo.getId());
		} 
		else {
			jo.put("result", "false");
		}
		return jo.toString();
	}
	
	public void updateEquipData(EquipVO vo) {	
		
		if(vo.getRemark() != null) {
			vo.setRemark(vo.getRemark().replaceAll("<br>", "\r\n"));
		}		
		
		System.out.println("[Equip - Image]: " + vo.getEquip_img_file() + " / " + vo.getEquip_img_file().getSize());
		
		if(vo.getEquip_img_file() != null && vo.getEquip_img_file().getSize() > 0) {			
			String virtName = "EQUIP/EQUIP_" + UUID.randomUUID().toString() +"_" + vo.getId();
			vo.setEquip_img(virtName);
			FileWriter.INSTANCE.writeFile(vo.getEquip_img_file(), virtName);
		};		
		
		equipMapper.updateEquipData(vo);
		
		if(vo.getFile_list() != null){
			Iterator<FileVO> it_list =  vo.getFile_list().iterator();
			while(it_list.hasNext()){
				FileVO file =  it_list.next();
				
				if(file.getIsmodify() == 1){
					if(file.getVirtname() != null) {
						FileWriter.INSTANCE.updateFileDetail(FileWriter.PARENT_TYPE.EQUIP, file, vo.getId(), -1,  fileMapper);
					}				
				}
				else{
					FileWriter.INSTANCE.insertFileDetail(PARENT_TYPE.EQUIP, file, vo.getId(), -1, fileMapper);
				}				
			}
		}
	}
	
}
