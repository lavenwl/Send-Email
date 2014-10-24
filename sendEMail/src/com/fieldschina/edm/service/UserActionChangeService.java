package com.fieldschina.edm.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fieldschina.edm.dao.UserActionChangeDao;
import com.fieldschina.edm.dao.impl.UserActionChangeDaoImpl;
import com.fieldschina.edm.entity.FavouriteAction;
import com.fieldschina.edm.entity.ShoppingCartAction;
import com.fieldschina.edm.entity.UserActionChange;
import com.fieldschina.edm.util.Util;
/**
 * 检测用户收藏夹与购物车数据变动的逻辑类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-9-25 下午5:09:42
 */
public class UserActionChangeService extends Service{
	private Map<Integer, Integer> productMap = null;
	private ItemCSVService itemCSVService = null;
	private List<UserActionChange> list = null;
	private List<UserActionChange> nList = null;
	private Map<Integer, UserActionChange> map = null;
	private UserActionChangeDao userActionChangeDao = null;
	private UserActionChangeDao getUserActionChangeDao(){
		if(userActionChangeDao == null){
			userActionChangeDao = new UserActionChangeDaoImpl();
		}
		return userActionChangeDao;
	}
	/**
	 * 初始化web端的新数据
	 * 
	 * @return
	 */
	private List<UserActionChange> getNList(){
		if(nList == null){
			UserActionChangeDao userActionChangeDao = new UserActionChangeDaoImpl();
			nList = userActionChangeDao.findAllUserActionChangeFromWeb();
		}
		log.debug("fromweb:nList.size():" + nList.size());
		return nList;
	}
	/**
	 * 得到local端的老数据
	 * 
	 * @return
	 */
	private List<UserActionChange> getList(){
		if(list == null){
			UserActionChangeDao userActionChangeDao = new UserActionChangeDaoImpl();
			list = userActionChangeDao.findAllUserActionChangeFromLocal();
		}
		log.debug("fromlocal:list.size():" + list.size());
		return list;
	}
	/**
	 * 得到商品的基本信息，用于后面配对检测ID的具体商品信息
	 * 
	 * @return
	 */
	private Map<Integer, Integer> getProductMap(){
		if(productMap == null){
			ItemCSVService itemCSVService = this.getItemCSVService();
			productMap = itemCSVService.getAllProduct();
		}
		return productMap;
	}
	/**
	 * 初始化itemCSVService类，用于获取商品的基本信息
	 * 
	 * @return
	 */
	private ItemCSVService getItemCSVService(){
		if(itemCSVService == null){
			itemCSVService = new ItemCSVService();
		}
		return itemCSVService;
	}
	/**
	 * 计算中用于比较实用的本地老数据等成map集合
	 * 
	 * @return
	 */
	private Map<Integer, UserActionChange> getMap(){
		if(map == null){
			map = new HashMap<Integer, UserActionChange>();
			List<UserActionChange> list = getList();
			for(int i = 0; i < list.size(); i++){
				UserActionChange userActionChange = list.get(i);
				userActionChange.setWishlistMap(Util.formWishlistToMap(userActionChange.getWishlist()));
//				log.debug("getMap.customerid:" + userActionChange.getCustomerId());
				userActionChange.setCartMap(Util.formCartToMap(userActionChange.getCart()));
				userActionChange.setStoreRemindMap(Util.formCartToMap(userActionChange.getStoreRemind()) == null ? new HashMap<Integer, String>() : Util.formCartToMap(userActionChange.getStoreRemind()));
				map.put(userActionChange.getCustomerId(), userActionChange);
			}
		}
		
		return map;
	}
	
	/**
	 * 初始化数据（数据库表内数据的一次性添加）
	 */
	public void initData(){
		UserActionChangeDao userActionChangeDao = new UserActionChangeDaoImpl();
		FavouriteActionService favouriteActionService = new FavouriteActionService();
		ShoppingCartActionService shoppingCartActionService = new ShoppingCartActionService();
		List<UserActionChange> list = userActionChangeDao.findAllUserActionChangeFromWeb();
		log.debug("fromweb:list.size():" + list.size());
		for(int i = 0; i < list.size(); i++){
			UserActionChange userActionChange = list.get(i);
			userActionChange.setWishlistList(Util.formWishlistToList(userActionChange.getWishlist()));
			userActionChange.setCartList(Util.formCartToList(userActionChange.getCart()));
//			log.debug("转化后的cartlist.size()：" + (userActionChange.getCartList() == null ? 0 : userActionChange.getCartList().size()) + " 转化后的Wishlist.size():" + (userActionChange.getWishlistList() == null ? 0 : userActionChange.getWishlistList().size()));
			favouriteActionService.initDate(userActionChange);
			shoppingCartActionService.initDate(userActionChange);
			userActionChange.setCart(userActionChange.getCartList() == null ? "" : userActionChange.getCartList().toString());
			userActionChange.setWishlist(userActionChange.getWishlistList() == null? "" : userActionChange.getWishlistList().toString());
			userActionChangeDao.insertUserActionChange(userActionChange);
		}
	}
	/**
	 * 检测收藏列表购物车的数据变化
	 */
	public void detactionOfWishList(){
		UserActionChangeDao userActionChangeDao = new UserActionChangeDaoImpl();
		FavouriteActionService favouriteActionService = new FavouriteActionService();
		ShoppingCartActionService shoppingCartActionService = new ShoppingCartActionService();
		//当前数据
		List<UserActionChange> list = getNList();
		//昨日数据
		Map<Integer, UserActionChange> map = getMap();
		//迭代每个用户，检查其数据的变动情况
		List<Integer> wishlistList = null;
		List<Integer> cartList = null;
		Map<Integer, String> wishlistMap = null;
		Map<Integer, String> cartMap = new HashMap<Integer, String>();
		Map<Integer, String> storeRemindMap = new HashMap<Integer, String>();
		for(int i = 0; i < list.size(); i++){
			if(i % 100 == 0)
				log.debug("检测收藏列表购物车的数据变化每一百个数据完成！_________________________________整体进度：" + (Long.valueOf(i)/Long.valueOf(list.size()) * 100) + "%");
			UserActionChange userActionChange = list.get(i);
			userActionChange.setWishlistList(Util.formWishlistToList(userActionChange.getWishlist()));
			userActionChange.setCartList(Util.formCartToList(userActionChange.getCart()));
			wishlistList = userActionChange.getWishlistList();
			cartList = userActionChange.getCartList();
			//新增用户
			if(!map.containsKey(userActionChange.getCustomerId())){
//				log.debug("新增一条用户：customerId：" + userActionChange.getCustomerId());
				favouriteActionService.initDate(userActionChange);
				shoppingCartActionService.initDate(userActionChange);
				userActionChange.setCart(userActionChange.getCartList() == null ? "" : userActionChange.getCartList().toString());
				userActionChange.setWishlist(userActionChange.getWishlistList() == null? "" : userActionChange.getWishlistList().toString());
				userActionChangeDao.insertUserActionChange(userActionChange);
			}else{
				wishlistMap = ((UserActionChange)map.get(userActionChange.getCustomerId())).getWishlistMap();
				cartMap = ((UserActionChange)map.get(userActionChange.getCustomerId())).getCartMap();
				storeRemindMap = ((UserActionChange)map.get(userActionChange.getCustomerId())).getStoreRemindMap();
				//添加数据
				if(!(wishlistList == null || wishlistList.size() < 0)){
//					log.debug("进入Wishlist检查：customerId:" + userActionChange.getCustomerId());
					FavouriteAction favouriteAction = null;
					for(int j = 0; j < wishlistList.size(); j++){
						if(wishlistMap != null && wishlistList != null){
							if(wishlistMap.containsKey(wishlistList.get(j))){
//								log.debug("wishlist记录存在，保持不变：customerId：" + userActionChange.getCustomerId());
								//标记现有的记录，用于对删除记录做检查
								wishlistMap.put(wishlistList.get(j), "y");
							}else{
								log.debug("wishlist数据不存在，产生一条新的数据：customerId：" + userActionChange.getCustomerId());
								//产生一条新数据
								favouriteAction = new FavouriteAction();  
								favouriteAction.setCustomerId(userActionChange.getCustomerId());
								favouriteAction.setEmail(userActionChange.getEmail());
								favouriteAction.setItemId(wishlistList.get(j));
								favouriteAction.setStartTime(Util.getDayTime());
								favouriteAction.setEndTime("3000-01-01 12:01:01");
								favouriteActionService.insertFavouriteAction(favouriteAction);
								//新数据是否保存为提醒数据
								saveAsRemind(wishlistList.get(j), userActionChange, storeRemindMap);
							}
						}
						
					}
				}
				if(!(cartList == null || cartList.size() < 0)){
//					log.debug("进入cartlist检查：customerId:" + userActionChange.getCustomerId());
					ShoppingCartAction shoppingCartAction = null;
//					log.debug("customerId:" + userActionChange.getCustomerId() + " cartMap:" + cartMap.toString() + " cartList:" + cartList.toString());
					for(int j = 0; j < cartList.size(); j++){
						if(cartMap != null && cartList.get(j) != null){
							if(cartMap.containsKey(cartList.get(j))){
//								log.debug("cart记录存在，保持不变：customerId：" + userActionChange.getCustomerId());
								//标记现有的记录，用于对删除记录做检查
								cartMap.put(cartList.get(j), "y");
							}else{
								log.debug("cart数据不存在，产生一条新的数据：customerId：" + userActionChange.getCustomerId() + " itemid:" + cartList.get(j));
								//产生一条新数据
								shoppingCartAction = new ShoppingCartAction();  
								shoppingCartAction.setCustomerId(userActionChange.getCustomerId());
								shoppingCartAction.setEmail(userActionChange.getEmail());
								shoppingCartAction.setItemId(cartList.get(j));
								shoppingCartAction.setStartTime(Util.getDayTime());
								shoppingCartAction.setEndTime("3000-01-01 12:01:01");
								shoppingCartActionService.insertShoppingCartAction(shoppingCartAction);
								//新数据是否保存为提醒数据
								saveAsRemind(cartList.get(j), userActionChange, storeRemindMap);
							}
						}
					}
				}
				//删除数据
				if(!(wishlistList == null || wishlistList.size() < 0)){
//					log.debug("Wishlist删除数据开始，customerId：" + userActionChange.getCustomerId());
					if(wishlistMap != null){
						Iterator it = wishlistMap.keySet().iterator();
						FavouriteAction favouriteAction = null;
						while(it.hasNext()){
							int key = (Integer) it.next();
							if(!wishlistMap.get(key).equals("y")){
								log.debug("wishlist产生一条删除记录：customerId：" + userActionChange.getCustomerId());
								//产生一条删除数据
								favouriteAction = new FavouriteAction();  
								favouriteAction.setCustomerId(userActionChange.getCustomerId());
								favouriteAction.setEmail(userActionChange.getEmail());
								favouriteAction.setItemId(key);
								favouriteAction.setEndTime(Util.getDayTime());
								favouriteActionService.updateFavouriteAction(favouriteAction);
								//同步删除掉带有提醒数据中的对应产品
								deleteInRemind(key, userActionChange, storeRemindMap);
							}
						}
					}
					
				}
				if(!(cartList == null || cartList.size() < 0)){
//					log.debug("cart删除数据开始，customerId：" + userActionChange.getCustomerId());
					if(cartMap != null){
						Iterator it = cartMap.keySet().iterator();
						ShoppingCartAction shoppingCartAction = null;
						while(it.hasNext()){
							int key = (Integer) it.next();
							if(!cartMap.get(key).equals("y")){
								log.debug("cart产生一条删除记录：customerId：" + userActionChange.getCustomerId() + " itemid:" + key);
								//产生一条删除数据
								shoppingCartAction = new ShoppingCartAction();  
								shoppingCartAction.setCustomerId(userActionChange.getCustomerId());
								shoppingCartAction.setEmail(userActionChange.getEmail());
								shoppingCartAction.setItemId(key);
								shoppingCartAction.setEndTime(Util.getDayTime());
								shoppingCartActionService.updateShoppingCartAction(shoppingCartAction);
								//同步删除掉带有提醒数据中的对应产品
								deleteInRemind(key, userActionChange, storeRemindMap);
							}
						}
					}
				}
			}
			//更新最新的用户信息到数据库
			userActionChange.setCart(userActionChange.getCartList() == null ? "" : userActionChange.getCartList().toString());
			userActionChange.setWishlist(userActionChange.getWishlistList() == null ? "" : userActionChange.getWishlistList().toString());
			userActionChange.setStoreRemind(userActionChange.getStoreRemindList() == null ? "" : userActionChange.getStoreRemindList().toString());
			userActionChangeDao.updateUserActionChange(userActionChange);
		}
		//指控对象
		this.map = null;
		this.list = null;
		this.productMap = null;
	}
	/**
	 * 当产生一条新数据时，判断此条数据是否因为缺货而添加的，保存到到货提醒的字段内
	 * @param integer			产品ID
	 * @param userActionChange	数据保存对象
	 */
	private void saveAsRemind(Integer itemID, UserActionChange userActionChange, Map<Integer, String> remindMap) {
		//判断此产品是否缺货
		ItemCSVService itemCSVService = this.getItemCSVService();
		boolean b = isOutOfStock(itemID);
		if(b){
			if(!remindMap.containsKey(itemID)){
				remindMap.put(itemID, "");
				log.debug("添加了一条需要进行到货提醒的用户行为记录！customer_id：" + userActionChange.getCustomerId() + " itemID:" + itemID);
				userActionChange.setStoreRemindList(Util.formRemindMapToList(remindMap));
			}
		}
		
	}
	/**
	 * 判断产品ID当前是否缺货
	 * 
	 * @param itemID
	 * @return
	 */
	private boolean isOutOfStock(Integer itemID) {
		Map<Integer, Integer> productMap = getProductMap();
		boolean b = false;
		if(productMap.containsKey(itemID))
			if(productMap.get(itemID) == 1)//1:缺货，7：正常，
				b = true;
		return b;
	}
	/**
	 * 当删除一条数据时，判断提醒数据内部是否包含此产品，如果包含则同步删除，不再提醒
	 * @param key					产品ID
	 * @param userActionChange		数据保存对象
	 */
	private void deleteInRemind(int key, UserActionChange userActionChange, Map<Integer, String> remindMap) {
		if(remindMap.containsKey(key)){
			remindMap.remove(key);
			userActionChange.setStoreRemindList(Util.formRemindMapToList(remindMap));
			log.debug("删除了一条会到货提醒的用户行为记录！customer_id：" + userActionChange.getCustomerId() + " itemID:" + key);
		}
	}
	/**
	 * 当一条用户的到货提醒发送成功后删除useractionchange表中的用户到货提醒记录
	 * @param email
	 */
	public void deleteStoreRemind(String email){
		boolean b = getUserActionChangeDao().deleteStoreRemind(email);
	}
}
