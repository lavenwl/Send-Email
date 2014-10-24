package com.fieldschina.edm.service;

import java.util.List;

import com.fieldschina.edm.dao.FavouriteActionDao;
import com.fieldschina.edm.dao.impl.FavouriteActionDaoImpl;
import com.fieldschina.edm.entity.FavouriteAction;
import com.fieldschina.edm.entity.ShoppingCartAction;
import com.fieldschina.edm.entity.UserActionChange;
import com.fieldschina.edm.util.Util;

/**
 * 收藏列表数据监听逻辑类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-24 下午4:57:40
 */
public class FavouriteActionService {
	/**
	 * 初始化用户的收藏商品信息
	 * 
	 * @param userActionChange
	 * @return
	 */
	public boolean initDate(UserActionChange userActionChange){
		//得到用户的所有收藏信息
		List<Integer>list = userActionChange.getWishlistList();
		if(list != null){
			FavouriteAction favouriteAction = null;
			for(int i = 0; i < list.size(); i++){
				favouriteAction = new FavouriteAction();  
				favouriteAction.setCustomerId(userActionChange.getCustomerId());
				favouriteAction.setEmail(userActionChange.getEmail());
				favouriteAction.setItemId(list.get(i));
				favouriteAction.setStartTime(Util.getDayTime());
				favouriteAction.setEndTime("3000-01-01 12:01:01");
				insertFavouriteAction(favouriteAction);
			}
		}
		return true;
	}
/**
 * 插入新的一条用户收藏信息
 * 
 * @param favouriteAction
 * @return
 */
	public boolean insertFavouriteAction(FavouriteAction favouriteAction) {
		FavouriteActionDao favouriteActionDao = new FavouriteActionDaoImpl();
		return favouriteActionDao.insertFavouriteAction(favouriteAction);
	}
/**
 * 更新一条用户的收藏商品信息
 * 
 * @param favouriteAction
 * @return
 */
	public boolean updateFavouriteAction(FavouriteAction favouriteAction) {
		FavouriteActionDao favouriteActionDao = new FavouriteActionDaoImpl();
		return favouriteActionDao.updateFavouriteAction(favouriteAction);
	}
}
