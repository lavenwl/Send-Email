package com.fieldschina.edm.dao;

import java.util.List;
import java.util.Map;

import com.fieldschina.edm.entity.ItemCSV;
/**
 * ItemCSV数据库操作接口
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-8 下午2:29:30
 */
public interface ItemCSVDao {
	/**
	 * 得到所有的中文描述的商品信息
	 * 
	 * @return
	 */
	public List<ItemCSV> getAllCNItemCSVs();
	/**
	 * 得到所有的英文描述的商品信息
	 * 
	 * @return
	 */
	public List<ItemCSV> getAllENItemCSVs();
	/**
	 * 得到想要字段信息组成的产品集合
	 * @return
	 */
	public Map<Integer, Integer> getAllProduct();
	public String getProList();
}
