package com.isbing.authority.permission.dao;

import com.isbing.authority.permission.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by song bing
 * Created time 2019/3/27 19:24
 */
@Mapper
public interface RoleDao {

	List<Role> getAll();

	void insert(Role role);

	Role getById(int id);

	void update(Role role);
}
