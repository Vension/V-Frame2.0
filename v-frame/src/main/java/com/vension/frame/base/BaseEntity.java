package com.vension.frame.base;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/25 10:41
 * 描  述：所有实体对象的基类
 * ========================================================
 */

public class BaseEntity implements MultiItemEntity,Serializable {
	@Override
	public int getItemType() {
		return 0;
	}
}
