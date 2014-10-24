package com.fieldschina.edm.service;

import java.util.List;

import com.fieldschina.edm.dao.RecipientResultDao;
import com.fieldschina.edm.dao.UserCSVDao;
import com.fieldschina.edm.dao.impl.RecipientResultDaoImpl;
import com.fieldschina.edm.dao.impl.UserCSVDaoImpl;
import com.fieldschina.edm.entity.UserCSV;
/**
 * 用户信息CSV文件相关的逻辑类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-23 下午3:03:33
 */
public class UserCSVService extends Service{
	private static UserCSVDao userCSVDao = null;
	static{
		if(userCSVDao == null){
			userCSVDao =  new UserCSVDaoImpl();
		}
	}
	/**
	 * 得到所有的中国用户信息用于生产DMA需要的CSV文件
	 * 
	 * @return
	 */
	public List<UserCSV> getAllCNUserCSVs() {
		return userCSVDao.getAllCNUserCSVs();
	}
	/**
	 * 得到所有的外国用户信息用于生产DMA需要的CSV文件
	 * 
	 * @return
	 */
	public List<UserCSV> getAllENUserCSVs() {
		return userCSVDao.getAllENUserCSVs();
	}
}
