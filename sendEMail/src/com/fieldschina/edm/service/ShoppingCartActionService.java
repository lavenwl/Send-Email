package com.fieldschina.edm.service;

import java.util.List;

import com.fieldschina.edm.dao.ShoppingCartActionDao;
import com.fieldschina.edm.dao.impl.ShoppingCartActionDaoImpl;
import com.fieldschina.edm.entity.ShoppingCartAction;
import com.fieldschina.edm.entity.UserActionChange;
import com.fieldschina.edm.util.Util;

/**
 * 购物车数据监听逻辑类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-24 下午4:56:51
 */
public class ShoppingCartActionService extends Service{
	/**
	 * 初始化购物车数据改变监听数据
	 * 
	 * @param userActionChange
	 * @return
	 */
	public boolean initDate(UserActionChange userActionChange) {
		List<Integer>list = userActionChange.getCartList();
		if(list != null){
			ShoppingCartAction shoppingCartAction = null;
			for(int i = 0; i < list.size(); i++){
				shoppingCartAction = new ShoppingCartAction();  
				shoppingCartAction.setCustomerId(userActionChange.getCustomerId());
				shoppingCartAction.setEmail(userActionChange.getEmail());
				shoppingCartAction.setItemId(list.get(i));
				shoppingCartAction.setStartTime(Util.getDayTime());
				shoppingCartAction.setEndTime("3000-01-01 12:01:01");
				insertShoppingCartAction(shoppingCartAction);
			}
		}
		
		return true;
	}
	/**
	 * 插入一条新的数据
	 * 
	 * @param shoppingCartAction
	 * @return
	 */
	public boolean insertShoppingCartAction(ShoppingCartAction shoppingCartAction){
		ShoppingCartActionDao shoppingCartActionDao = new ShoppingCartActionDaoImpl();
		return shoppingCartActionDao.insertShoppingCartAction(shoppingCartAction);
	}
	/**
	 * 更新一条新的数据
	 * 
	 * @param shoppingCartAction
	 * @return
	 */
	public boolean updateShoppingCartAction(ShoppingCartAction shoppingCartAction) {
		ShoppingCartActionDao shoppingCartActionDao = new ShoppingCartActionDaoImpl();
		return shoppingCartActionDao.updateShoppingCartAction(shoppingCartAction);
	}

}
