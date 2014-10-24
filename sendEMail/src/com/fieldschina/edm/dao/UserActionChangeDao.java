package com.fieldschina.edm.dao;

import java.util.List;

import com.fieldschina.edm.entity.UserActionChange;
/**
 * 检测用户收藏夹与购物车的数据变化的数据库访问接口
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-9-25 下午1:42:49
 */
public interface UserActionChangeDao {
	/**
	 *  得到用户数据在web端的当前数据
	 *  
	 * @return 	用户数据在web端的当前数据
	 */
	public List<UserActionChange> findAllUserActionChangeFromWeb();
	/**
	 * 得到用户数据在本地的当前数据
	 * 
	 * @return	用户数据在local的当前数据
	 */
	public List<UserActionChange> findAllUserActionChangeFromLocal();
	/**
	 * 插入一条新产生的用户变动数据
	 * 
	 * @param userActionChange	新的数据
	 * @return	是否插入成功
	 */
	public boolean insertUserActionChange(UserActionChange userActionChange);
	/**
	 * 更新一条用户的行为变动数据
	 * 
	 * @param userActionChange
	 * @return	是否更新成功
	 */
	public boolean updateUserActionChange(UserActionChange userActionChange);
	/**
	 * 产生一条无用的数据
	 * 
	 * @param email	用户的Email
	 * @return	是否删除成功
	 */
	public boolean deleteStoreRemind(String email);
}
