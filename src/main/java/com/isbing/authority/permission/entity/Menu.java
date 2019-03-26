package com.isbing.authority.permission.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 菜单
 * Created by song bing
 * Created time 2019/3/20 14:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Menu {
	private int id;
	private String name;//菜单名
	private String icon;//一级菜单的图标
	private String url;//二级菜单的页面地址
	private int parentId;//二级菜单关联的一级菜单ID
	private int sort;//排序字段
	private Date createTime;//创建时间

	// 冗余展示字段
	private String parentUrl;//父类的
}
