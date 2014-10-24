package com.fieldschina.edm.dao;

import com.fieldschina.edm.entity.RecipientResult;

/**
 * 用户增加的每日记录接口
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-6-25 下午3:32:15
 */
public interface RecipientResultDao {
	/**
	 * 添加用户的log记录
	 * 
	 * @return
	 */
	public boolean addRecipientResult(RecipientResult recipientResult);
}
