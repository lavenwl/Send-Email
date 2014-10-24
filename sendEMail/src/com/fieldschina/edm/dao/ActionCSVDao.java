package com.fieldschina.edm.dao;

import java.util.List;
import com.fieldschina.edm.entity.ActionCSV;
/**
 * 用户行为数据库操作接口
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-8 下午2:37:47
 */
public interface ActionCSVDao {
	/**
	 * 得到所有的用户查看行为数据
	 * 
	 * @return	所有的用户查看行为数据
	 */
	public List<ActionCSV> getAllViewActionCSVs(String begainTime, String endTime);
	/**
	 * 得到所有的用户购买行为数据
	 * 
	 * @return	所有的用户购买行为数据
	 */
	public List<ActionCSV> getAllPurchasedActionCSVs(String begainTime, String endTime);
	/**
	 * 得到所有的用户收藏行为数据
	 * 
	 * @return	所有的用户收藏行为数据
	 */
	public List<ActionCSV> getAllCollectActionCSVs(String begainTime, String endTime);
	/**
	 * 得到所有的用户添加购物车行为数据
	 * 
	 * @return	所有的用户添加购物车行为数据
	 */
	public List<ActionCSV> getAllShoppingCartActionCSVs(String begainTime, String endTime);
}
