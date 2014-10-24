package com.fieldschina.edm.entity;

import java.util.Date;
import java.util.List;

/**
 * 用户实例类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-6-19 上午9:45:09
 */
public class Recipient2 {
	
	private String email;				//用户的email
	private String warnProducts;		//提醒的产品名
	private List<ItemCSV> list;			//用户需要提醒的产品集合
	private String jsonProducts;		//用户需要提醒的产品字符串
	private String name;				//商品的名称
	private String pUrl;				//商品的连接
	private String iUrl;				//商品图片的连接
	private int pPrice;					//商品的价格
	private int pPrice2;				//商品特惠价格
	private String addDate;				//商品到货时间
	private String proList;				//本周热门搜索
	
	public String getProList() {
		return proList;
	}
	public void setProList(String proList) {
		this.proList = proList;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getpUrl() {
		return pUrl;
	}
	public void setpUrl(String pUrl) {
		this.pUrl = pUrl;
	}
	public String getiUrl() {
		return iUrl;
	}
	public void setiUrl(String iUrl) {
		this.iUrl = iUrl;
	}
	public int getpPrice() {
		return pPrice;
	}
	public void setpPrice(int pPrice) {
		this.pPrice = pPrice;
	}
	public int getpPrice2() {
		return pPrice2;
	}
	public void setpPrice2(int pPrice2) {
		this.pPrice2 = pPrice2;
	}
	public String getAddDate() {
		return addDate;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWarnProducts() {
		return warnProducts;
	}
	public void setWarnProducts(String warnProducts) {
		this.warnProducts = warnProducts;
	}
	public String getJsonProducts() {
		return jsonProducts;
	}
	public void setJsonProducts(String jsonProducts) {
		this.jsonProducts = jsonProducts;
	}
	public List<ItemCSV> getList() {
		return list;
	}
	public void setList(List<ItemCSV> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "Recipient2 [email=" + email + ", warnProducts=" + warnProducts
				+ ", list=" + list + ", name=" + name + ", pUrl=" + pUrl
				+ ", iUrl=" + iUrl + ", pPrice=" + pPrice + ", pPrice2="
				+ pPrice2 + ", addDate=" + addDate + "]";
	}
	
	
}

