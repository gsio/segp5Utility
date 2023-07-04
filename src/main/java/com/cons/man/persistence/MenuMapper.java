package com.cons.man.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cons.man.domain.MenuAccessVO;
import com.cons.man.domain.MenuVO;

@Repository(value = "MenuMapper")
public interface MenuMapper {

	public List<MenuVO> getMenuList();

	public String getMenuAccessList(MenuAccessVO maVO);

	public List<MenuVO> getMenuListByAccess(List<String> list);

}