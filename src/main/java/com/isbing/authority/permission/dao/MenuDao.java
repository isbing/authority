package com.isbing.authority.permission.dao;

import com.isbing.authority.permission.entity.Menu;
import com.isbing.authority.permission.entity.PageBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by song bing
 * Created time 2019/3/20 14:19
 */
@Mapper
public interface MenuDao {

	List<Menu> getAll();

	List getFirstAll();
}
