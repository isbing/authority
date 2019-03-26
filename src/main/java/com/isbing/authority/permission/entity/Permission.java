package com.isbing.authority.permission.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by song bing
 * Created time 2019/3/26 16:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Permission {
	private int id;
	private String name;//权限名
	private String code;//权限码
	private Date createTime;//创建时间
	private int menuId;//菜单ID

}
