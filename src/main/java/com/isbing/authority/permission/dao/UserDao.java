package com.isbing.authority.permission.dao;

import com.isbing.authority.permission.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by song bing
 * Created time 2019/3/28 17:06
 */
@Mapper
public interface UserDao {

	void insert(User user);

	List<User> getAll();

	User getById(Integer id);

	void update(User user);
}
