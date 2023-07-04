package com.cons.man.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cons.man.domain.SiteVO;

@Repository(value="SiteMapper")
public interface SiteMapper {
	
	public List<SiteVO> getSiteList();
	public SiteVO getSiteInfo(int site_id);
	
//	-----------------------------------

	public List<SiteVO> getOptionSiteList();	

	public int getSiteRowCount(SiteVO site);
	public int getNewId();
	public void insert(SiteVO siteVO);
	public void update(SiteVO siteVO);

	public List<SiteVO> getSiteListWithAuth(int auth);
	public List<SiteVO> getSiteListWithSiteGroup(int site_group);
	public int getNextSiteGroupNum();
	public List<SiteVO> getSiteTypeList();
	public List<SiteVO> getHasTunnelSiteList();
	
	public List<String> getConsNameList(int site_group);
	public List<SiteVO> getSiteListByConsName(@Param("site_group") int site_group, @Param("cons_name")String cons_name,  @Param("site_type")int site_type);
	
	// public List<SiteFavoriteVO> getSiteFavoriteList(@Param("type")int type, @Param("u_id")String u_id);
	public List<String> getSiteFavIdListByUserInfo(@Param("type")int type, @Param("cons_name")String cons_name, @Param("site_type")int site_type);
	public void insertSiteFavorite(@Param("type")int type, @Param("u_id")String u_id, @Param("cons_name")String cons_name, @Param("site_type")int site_type);
	public void deleteSiteFavorite(@Param("type")int type, @Param("u_id")String u_id);
	
}