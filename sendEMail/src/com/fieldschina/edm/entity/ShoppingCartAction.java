package com.fieldschina.edm.entity;
/**
 * 用户添加购物车行为数据实体类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-24 下午5:00:38
 */
public class ShoppingCartAction {

	private int customerId;		//用户的ID
	private String email;		//用户的Email
	private int itemId;			//用户数据变动的商品的Id
	private String startTime;	//用户添加该商品到购物车的时间
	private String endTime;		//用户取消够我车内的该商品的时间
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
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	


}
