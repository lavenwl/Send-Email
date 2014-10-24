package com.fieldschina.edm.dao;

import java.util.List;

import com.fieldschina.edm.entity.FavouriteAction;
import com.fieldschina.edm.entity.ShoppingCartAction;

/**
 * 购物车操作新数据的数据库操作接口
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-24 下午4:03:39
 */
public interface ShoppingCartActionDao {
	/**
	 * 插入一条新的数据
	 * 
	 */
	public boolean insertShoppingCartAction(ShoppingCartAction shoppingCartAction);
	/**
	 * 更新一条新的数据
	 * 
	 */
	public boolean updateShoppingCartAction(ShoppingCartAction shoppingCartAction);
	/**
	 * 查询所有的数据
	 * 
	 */
	public List<ShoppingCartAction> findAllShoppingCartAction();
	/**
	 * 查询部分符合时间条件的数据
	 * 
	 */
	public List<ShoppingCartAction> findSomeShoppingCartAction();
}
