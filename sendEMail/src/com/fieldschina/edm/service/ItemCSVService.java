package com.fieldschina.edm.service;

import java.util.List;
import java.util.Map;

import com.fieldschina.edm.dao.ActionCSVDao;
import com.fieldschina.edm.dao.ItemCSVDao;
import com.fieldschina.edm.dao.impl.ActionCSVDaoImpl;
import com.fieldschina.edm.dao.impl.ItemCSVDaoImpl;
import com.fieldschina.edm.entity.ActionCSV;
import com.fieldschina.edm.entity.ItemCSV;
/**
 * 产品CSV文件相关操作的逻辑类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-23 下午3:08:41
 */
public class ItemCSVService extends Service{
	private static ItemCSVDao itemCSVDao = null;
	static{
		if(itemCSVDao == null){
			itemCSVDao =  new ItemCSVDaoImpl();
		}
	}
	/**
	 * 得到所有的中文描述的产品信息用于生产DMA需要的CSV文件
	 * 
	 * @return
	 */
	public List<ItemCSV> getAllCNItemCSVs() {
		return itemCSVDao.getAllCNItemCSVs();
	}
	/**
	 * 得到所有的英文描述的产品信息用于生产DMA需要的CSV文件
	 * 
	 * @return
	 */
	public List<ItemCSV> getAllENItemCSVs() {
		return itemCSVDao.getAllENItemCSVs();
	}
	/**
	 * 获取所有产品的当前状态（normal， out of stock）
	 * 
	 * @return
	 */
	public Map<Integer, Integer> getAllProduct() {
		return itemCSVDao.getAllProduct();
	}
	public String getProList() {
		return itemCSVDao.getProList();
	}
}
