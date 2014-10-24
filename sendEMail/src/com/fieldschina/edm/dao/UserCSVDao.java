package com.fieldschina.edm.dao;

import java.util.List;
import com.fieldschina.edm.entity.UserCSV;
/**
 * UserCSV数据库操作接口
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-8 下午2:31:13
 */
public interface UserCSVDao {
	/**
	 * 得到所有的中国用户信息
	 * 
	 * @return
	 */
	public List<UserCSV> getAllCNUserCSVs();
	/**
	 * 得到所有的外国用户信息
	 * 
	 * @return
	 */
	public List<UserCSV> getAllENUserCSVs();
}
