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

@Repository(value = "ManageMapper")
public interface ManageMapper {

	public List<WorkTypeVO> getWorkTypeList(@Param("site_id") int site_id);

	public void insertWorkType(@Param("site_id") int site_id, @Param("gubun") int gubun, @Param("t_name") String t_name,
			@Param("writer_user_id") String writer_user_id);

	public void deletetWorkType(@Param("id") int id, @Param("writer_user_id") String writer_user_id);

	public List<WorkStateVO> getWorkStateListFromWeb(@Param("site_id") int site_id);

	public List<WorkStateVO> getWorkStateList(@Param("site_id") int site_id);

	public void insertWorkState(@Param("site_id") int site_id, @Param("color") String color, @Param("name") String name,
			@Param("u_id") String u_id);

	public void deletetWorkState(@Param("id") int id, @Param("u_id") String u_id);

	public void insertSectionData(@Param("site_id") int site_id, @Param("section_name") String section_name,
			@Param("floor") String floor, @Param("writer_user_id") String writer_user_id);

	public void updateSectionAutoNumber(@Param("site_id") int site_id, @Param("writer_user_id") String writer_user_id);

	public void updateSectionData(@Param("id") int id, @Param("site_id") int site_id,
			@Param("section_name") String section_name, @Param("floor") String floor,
			@Param("writer_user_id") String writer_user_id);

	public void deleteSectionData(@Param("id") int id, @Param("writer_user_id") String writer_user_id);
}