package com.isbing.authority.permission.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * Created by song bing
 * Created time 2019/3/28 17:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
	private int id;
	private String userName;//用户名
	private String nickName;//昵称
	private String password;//密码
	private Date createTime;//创建时间

	private String roleStr;
}
