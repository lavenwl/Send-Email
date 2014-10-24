package com.fieldschina.edm.service;

import java.util.Collection;
import java.util.List;

import com.fieldschina.edm.dao.ActionCSVDao;
import com.fieldschina.edm.dao.UserCSVDao;
import com.fieldschina.edm.dao.impl.ActionCSVDaoImpl;
import com.fieldschina.edm.dao.impl.UserCSVDaoImpl;
import com.fieldschina.edm.entity.ActionCSV;
import com.fieldschina.edm.entity.UserCSV;
/**
 * 用户行为CSV文件相关操作逻辑类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-23 下午3:33:05
 */
public class ActionCSVService extends Service{
	private static ActionCSVDao actionCSVDao = null;
	static{
		if(actionCSVDao == null){
			actionCSVDao =  new ActionCSVDaoImpl();
		}
	}
	/**
	 * 得到所有的查看行为信息用于生产DMA需要的CSV文件
	 * 
	 * @return
	 */
	public List<ActionCSV> getAllViewActionCSVs(String begainTime, String endTime) {
		return actionCSVDao.getAllViewActionCSVs(begainTime, endTime);
	}
	/**
	 * 得到所有的购买行为信息用于生产DMA需要的CSV文件
	 * 
	 * @return
	 */
	public List<ActionCSV> getAllPurchasedActionCSVs(String begainTime, String endTime) {
		return actionCSVDao.getAllPurchasedActionCSVs(begainTime, endTime);
	}
	/**
	 * 得到所有的收藏行为信息用于生产DMA需要的CSV文件
	 * 
	 * @return
	 */
	public List<ActionCSV> getAllCollectActionCSVs(String begainTime, String endTime) {
		return actionCSVDao.getAllCollectActionCSVs(begainTime, endTime);
	}
	/**
	 * 得到所有的添加购物车行为信息用于生产DMA需要的CSV文件
	 * 
	 * @return
	 */
	public List<ActionCSV> getAllShoppingCartActionCSVs(String begainTime, String endTime) {
		return actionCSVDao.getAllShoppingCartActionCSVs(begainTime, endTime);
	}
}
