package com.fieldschina.edm.entity;

import java.util.List;
import java.util.Map;

/**
 * 用户购物车及收藏列表信息变更比对实体类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-25 上午11:26:08
 */
public class UserActionChange {
	private int customerId;							//用户的ID
	private String email;							//用户的Email
	private String wishlist;						//用户的收藏列表内的集合
	private String cart;							//用户的购物车内的物品的集合
	private String storeRemind;						//记录用户的数据变动是否有到货提醒的商品
	private Map<Integer, String> wishlistMap;		//用于比较的临时的用户收藏集合
	private Map<Integer, String> cartMap;			//用于比较的临时的用户购物车的集合
	private Map<Integer, String> storeRemindMap;	//用于比较的临时的用户是否提醒字段的商品集合
	private List<Integer> wishlistList;				//中间变量使用的用户收藏的商品集合
	private List<Integer> cartList;					//中间变量使用的用户收藏车里面的商品的集合
	private List<Integer> storeRemindList;			//中间变量使用的用户是否提醒字段的商品的集合
	public String getStoreRemind() {
		return storeRemind;
	}
	public void setStoreRemind(String storeRemind) {
		this.storeRemind = storeRemind;
	}
	public Map<Integer, String> getStoreRemindMap() {
		return storeRemindMap;
	}
	public void setStoreRemindMap(Map<Integer, String> storeRemindMap) {
		this.storeRemindMap = storeRemindMap;
	}
	public List<Integer> getStoreRemindList() {
		return storeRemindList;
	}
	public void setStoreRemindList(List<Integer> storeRemindList) {
		this.storeRemindList = storeRemindList;
	}
	public List<Integer> getWishlistList() {
		return wishlistList;
	}
	public void setWishlistList(List<Integer> wishlistList) {
		this.wishlistList = wishlistList;
	}
	public List<Integer> getCartList() {
		return cartList;
	}
	public void setCartList(List<Integer> cartList) {
		this.cartList = cartList;
	}
	public Map<Integer, String> getWishlistMap() {
		return wishlistMap;
	}
	public void setWishlistMap(Map<Integer, String> wishlistMap) {
		this.wishlistMap = wishlistMap;
	}
	public Map<Integer, String> getCartMap() {
		return cartMap;
	}
	public void setCartMap(Map<Integer, String> cartMap) {
		this.cartMap = cartMap;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWishlist() {
		return wishlist;
	}
	public void setWishlist(String wishlist) {
		this.wishlist = wishlist;
	}
	public String getCart() {
		return cart;
	}
	public void setCart(String cart) {
		this.cart = cart;
	}
}
