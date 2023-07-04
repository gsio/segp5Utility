package com.cons.man.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cons.man.domain.MenuAccessVO;
import com.cons.man.domain.MenuVO;
import com.cons.man.persistence.MenuMapper;

@Service(value="MenuService")
public class MenuService {
	
	@Resource(name="MenuMapper")
	private MenuMapper menuMapper;	
	
	public List<MenuVO> getMenuList() {
		return menuMapper.getMenuList();		
	}
	
	public String getMenuAccessList(MenuAccessVO maVO) {
		return menuMapper.getMenuAccessList(maVO);		
	}

	public List<MenuVO> getMenuListByAccess(List<String> list) {
		return menuMapper.getMenuListByAccess(list);	
	}
	
}
