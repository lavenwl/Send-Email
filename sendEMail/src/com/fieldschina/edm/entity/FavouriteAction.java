package com.fieldschina.edm.entity;
/**
 * 用户收藏行为数据实体类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-24 下午4:59:47
 */
public class FavouriteAction {
	private int customerId;		//用户ID
	private String email;		//用户Email
	private int itemId;			//用户产生收藏的成商品的ID
	private String startTime;	//用户收藏此商品的开始时间
	private String endTime;		//用户取消收藏该商品的时间
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
