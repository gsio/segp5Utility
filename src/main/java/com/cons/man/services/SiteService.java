package com.cons.man.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cons.man.domain.SiteVO;
import com.cons.man.persistence.SiteMapper;

@Service(value = "SiteService")
public class SiteService {

	@Resource(name = "SiteMapper")
	private SiteMapper siteMapper;

	public List<SiteVO> getSiteList() {
		return siteMapper.getSiteList();
	}

	public SiteVO getSiteInfo(int site_id) {
		return siteMapper.getSiteInfo(site_id);
	}
	
	public List<SiteVO> getSiteListWithAuth(int auth) {
		return siteMapper.getSiteListWithAuth(auth);
	}

	public List<SiteVO> getOptionSiteList() {
		return siteMapper.getOptionSiteList();
	}

	public int getSiteRowCount(SiteVO site) {
		return siteMapper.getSiteRowCount(site);
	}

	@Transactional
	public void insertSite(SiteVO siteVO) {
		int id = siteMapper.getNewId();
		siteVO.setId(id);
		siteMapper.insert(siteVO);
	}

	@Transactional
	public void updateSite(SiteVO siteVO) {
		siteMapper.update(siteVO);
	}

	public List<SiteVO> getSiteListWithSiteGroup(int site_group) {
		return siteMapper.getSiteListWithSiteGroup(site_group);
	}

	public int getNextSiteGroupNum() {
		return siteMapper.getNextSiteGroupNum();
	}

	public List<SiteVO> getSiteTypeList() {
		return siteMapper.getSiteTypeList();
	}

	public List<SiteVO> getHasTunnelSiteList() {
		return siteMapper.getHasTunnelSiteList();
	}

	public List<String> getConsNameList(int site_group) {
		return siteMapper.getConsNameList(site_group);
	}

	public List<SiteVO> getSiteListByConsName(int site_group, String cons_name, int site_type) {
		return siteMapper.getSiteListByConsName(site_group, cons_name, site_type);
	}
}
