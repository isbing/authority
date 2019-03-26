package com.isbing.authority.permission.dao;

import com.isbing.authority.permission.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by song bing
 * Created time 2019/3/26 16:03
 */
@Mapper
public interface PermissionDao {

	List<Permission> getAll();

	void insert(Permission permission);

	Permission getById(int id);

	void update(Permission permission);
}
