package com.isbing.authority.permission.service;

import com.isbing.authority.permission.dao.MenuDao;
import com.isbing.authority.permission.entity.Menu;
import com.isbing.authority.permission.entity.PageBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by song bing
 * Created time 2019/3/20 14:19
 */
@Service
public class MenuService {
	@Resource
	private MenuDao menuDao;

	public PageBean getAll() {
		return PageBean.builder().data(menuDao.getAll()).build();
	}

	/**
	 * 找出所有的一级菜单
	 * @return
	 */
	public PageBean getFirstAll() {
		return PageBean.builder().data(menuDao.getFirstAll()).build();
	}
}
