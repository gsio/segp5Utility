package com.cons.man.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cons.man.domain.DidSettingVO;
import com.cons.man.domain.LocationVO;
import com.cons.man.domain.SectionGroupVO;
import com.cons.man.domain.SectionVO;
import com.cons.man.domain.WorkStateVO;
import com.cons.man.domain.WorkTypeVO;

@Repository(value="ManageMapper")
public interface ManageMapper {
	
	public List<WorkStateVO> getWorkStateListFromWeb(@Param("site_id")int site_id);
	
	public List<WorkStateVO> getWorkStateList(@Param("site_id")int site_id);		
	
	public List<WorkTypeVO> getWorkTypeList(@Param("site_id")int site_id);	
	
	public void insertWorkType(@Param("site_id")int site_id, @Param("gubun")int gubun, @Param("t_name")String t_name, @Param("u_id")String u_id);
	
	public void insertWorkState(@Param("site_id")int site_id, @Param("color")String color, @Param("name")String name, @Param("u_id")String u_id);
	
	public void deletetWorkType(@Param("id")int id, @Param("u_id")String u_id);
	
	public void deletetWorkState(@Param("id")int id, @Param("u_id")String u_id);
	
	public List<SectionGroupVO> getSectionGroupList(@Param("site_id")int site_id);
	
	public List<SectionVO> getSectionMemberList(@Param("group_id")int group_id);	
	
	public List<SectionVO> getSectionGroupStateList(@Param("site_id")int site_id);	
	
	public void insertGroupName(@Param("site_id")int site_id, @Param("writer_id")String writer_id, @Param("group_name")String group_name);
	
	public void updateGroupName(@Param("id")int id, @Param("group_name")String group_name, @Param("updater_id")String updater_id);
	
	public void selectSectionGroup(@Param("site_id")int site_id, @Param("id")int id, @Param("section")int section);	
	
	public void updateLastUpdater(@Param("id")int id, @Param("updater_id")String updater_id);
		
	public void deleteSectionGroup(@Param("site_id")int site_id, @Param("section")int section);
	
	public void deleteGroup(@Param("site_id")int site_id, @Param("id")int id);
	
	public void initGroupMember(@Param("site_id")int site_id, @Param("id")int id);
	
	public void insertDidSetting(@Param("site_id")int site_id, @Param("gubun")int gubun, @Param("type")int type, @Param("start_time")String start_time, @Param("end_time")String end_time, @Param("u_id")String u_id);
	
	public List<DidSettingVO> getDidSettingList(@Param("site_id")int site_id);
	
	public void deleteMonitorSetting(@Param("site_id")int site_id, @Param("id")int id); 
	
	/*
	public List<LocationVO> getAttendList(@Param("site_id")int site_id);	
	
	public List<LocationVO> getLocationList(@Param("site_id")int site_id, @Param("input_date")String input_date);	
	
	public List<LocationVO> getLocationDetail(@Param("site_id")int site_id, @Param("role")int role, @Param("u_id")String u_id, @Param("input_date")String input_date);

	
	
	
	public void insertDailyValue(@Param("site_id")int site_id, @Param("place_id")int place_id, @Param("write_date")String write_date, @Param("value")double value, @Param("writer_user_id")String writer_user_id);
	

	
	public void deleteDailyInfo(@Param("id")int id); 
	*/
}