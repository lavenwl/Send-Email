package com.fieldschina.edm.entity;
/**
 * 生成itemCSV文件(商品表)时需要的单条数据类型
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-8 下午1:52:25
 */
public class ItemCSV {
	private String title;		//*商品名称
	private String url;			//*商品链接
	private String imageUrl;	//*商品图片1，通常大图
	private String imageUrl2;	//商品图片2，通常小图
	private int price1;			//*价格1，通常市场价
	private int price2;			//*价格2，通常折扣价
	private int privce3;		//价格3，通常折扣价
	private String desc1;		//描述1
	private String desc2;		//描述2
	private String desc3;		//描述3
	private int itemID;			//*商品ID
	private String type;		//*商品分类
	private String brand;		//所属品牌
	private String priceLevel;	//价格区间
	private String tagID;		//*自定义TAG
	private String gender;		//男女属性
	private String onlineDate;	//上线时间
	private String offlineDate;	//下线时间
	private int stock;			//*库存
	private int status; 		//上下架状态，0下架，1上架
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getImageUrl2() {
		return imageUrl2;
	}
	public void setImageUrl2(String imageUrl2) {
		this.imageUrl2 = imageUrl2;
	}
	public int getPrice1() {
		return price1;
	}
	public void setPrice1(int price1) {
		this.price1 = price1;
	}
	public int getPrice2() {
		return price2;
	}
	public void setPrice2(int price2) {
		this.price2 = price2;
	}
	public int getPrivce3() {
		return privce3;
	}
	public void setPrivce3(int privce3) {
		this.privce3 = privce3;
	}
	public String getDesc1() {
		return desc1;
	}
	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}
	public String getDesc2() {
		return desc2;
	}
	public void setDesc2(String desc2) {
		this.desc2 = desc2;
	}
	public String getDesc3() {
		return desc3;
	}
	public void setDesc3(String desc3) {
		this.desc3 = desc3;
	}
	public int getItemID() {
		return itemID;
	}
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getPriceLevel() {
		return priceLevel;
	}
	public void setPriceLevel(String priceLevel) {
		this.priceLevel = priceLevel;
	}
	public String getTagID() {
		return tagID;
	}
	public void setTagID(String tagID) {
		this.tagID = tagID;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getOnlineDate() {
		return onlineDate;
	}
	public void setOnlineDate(String onlineDate) {
		this.onlineDate = onlineDate;
	}
	public String getOfflineDate() {
		return offlineDate;
	}
	public void setOfflineDate(String offlineDate) {
		this.offlineDate = offlineDate;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String toString(){
		return "id:" + itemID + " title:" + title + " url:" + url + " imageUrl" + imageUrl + " price1:" + price1 + " price2:" + price2 + " type:" + type + " desc1:" + desc1;
	}
}
