package com.fieldschina.edm.dao;

import java.util.List;

import com.fieldschina.edm.entity.FavouriteAction;

/**
 * 收藏夹操作新数据的数据库操作接口
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-24 下午4:03:39
 */
public interface FavouriteActionDao {
	/**
	 * 插入一条新的数据
	 * 
	 */
	public boolean insertFavouriteAction(FavouriteAction favouriteAction);
	/**
	 * 更新一条新的数据
	 * 
	 */
	public boolean updateFavouriteAction(FavouriteAction favouriteAction);
	/**
	 * 查询所有的数据
	 * 
	 */
	public List<FavouriteAction> findAllFavouriteAction();
	/**
	 * 查询部分符合时间条件的数据
	 * 
	 */
	public List<FavouriteAction> findSomeFavouriteAction();
}
