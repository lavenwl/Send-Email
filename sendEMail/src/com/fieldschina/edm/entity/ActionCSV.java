package com.fieldschina.edm.entity;
/**
 * 生成actionCSV文件（用户行为表）时需要成单条数据类型
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-8 下午1:53:27
 */
public class ActionCSV {
	private String email;		//*
	private String itemID;		//*商品ID
	private String type;		//*商品分类
	private int brandID;		//*品牌ID
	private String priceLevel;	//价格区间
	private int tagID;			//自定义tag
	private int action;			//行为,1:view,2:purchased,3:collected,4:shoping_cart,5:login
	private String timesStamp;	//时间点 yyyy-mm-dd hh:mm:ss
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getItemID() {
		return itemID;
	}
	public void setItemID(String itemID) {
		this.itemID = itemID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getBrandID() {
		return brandID;
	}
	public void setBrandID(int brandID) {
		this.brandID = brandID;
	}
	public String getPriceLevel() {
		return priceLevel;
	}
	public void setPriceLevel(String priceLevel) {
		this.priceLevel = priceLevel;
	}
	public int getTagID() {
		return tagID;
	}
	public void setTagID(int tagID) {
		this.tagID = tagID;
	}
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	public String getTimesStamp() {
		return timesStamp;
	}
	public void setTimesStamp(String timesStamp) {
		this.timesStamp = timesStamp;
	}
	public String toString(){
		return "email:" + email + " itemID:" + itemID + " time:" + timesStamp;
	}
}
