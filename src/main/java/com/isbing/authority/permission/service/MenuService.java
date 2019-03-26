package com.isbing.authority.permission.service;

import com.isbing.authority.permission.dao.MenuDao;
import com.isbing.authority.permission.entity.Menu;
import com.isbing.authority.permission.entity.PageBean;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by song bing
 * Created time 2019/3/20 14:19
 */
@Service
public class MenuService {
	@Resource
	private MenuDao menuDao;

	public PageBean getAllFirstLevel() {
		return PageBean.builder().content(menuDao.getAllFirstLevel()).build();
	}

	public List<Menu> getAllSecondLevel(int parentId) {
		List<Menu> menuList = menuDao.getAllSecondLevel(parentId);
		// 增加冗余字段
		if(CollectionUtils.isNotEmpty(menuList)){
			Menu parentMenu = getById(parentId);
			if(Objects.nonNull(parentMenu)){
				menuList.forEach(menu -> menu.setParentUrl(parentMenu.getUrl()));
			}
		}
		return menuList;
	}


	public Integer create(Menu menu) {
		menuDao.insert(menu);
		return menu.getId();
	}

	public Menu getById(Integer id) {
		return menuDao.getById(id);
	}

	public void update(Menu menu) {
		menuDao.update(menu);
	}

	public List<Menu> getAllSecondLevelNoId() {
		return menuDao.getAllSecondLevelNoId();
	}
}
