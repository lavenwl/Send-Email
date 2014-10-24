package com.fieldschina.edm.dao;

import com.fieldschina.edm.entity.DMDReturnFile;
/**
 * dmd平台回传文件相关数据库操作类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-23 下午4:00:52
 */
public interface DMDReturnFileDao {
	/**
	 * 插入一条数据到本地数据库
	 * @param dMDReturnFile
	 * @return 是否插入成功
	 */
	boolean insertDMAReturnFile(DMDReturnFile dMDReturnFile);
}
